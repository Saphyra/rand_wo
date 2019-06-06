package com.github.saphyra.integration.mvc.storedobject;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.saphyra.common.configuration.MvcConfiguration;
import com.github.saphyra.common.testcomponent.DatabaseCleanup;
import com.github.saphyra.common.testcomponent.MockMvcWrapper;
import com.github.saphyra.common.testcomponent.ResponseValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.storedobject.StoredObjectController;
import com.github.saphyra.randwo.storedobject.domain.StoreObjectRequest;
import com.github.saphyra.randwo.storedobject.domain.StoredObject;
import com.github.saphyra.randwo.storedobject.repository.StoredObjectDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    StoredObjectApiTest.class
})
public class StoredObjectApiTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String NEW_VALUE = "new_value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private StoredObjectDao storedObjectDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void getObject_notFound() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.getRequest(StoredObjectController.GET_OBJECT_MAPPING, KEY);
        //THEN
        responseValidator.verifyNotFoundRequest(result, ErrorCode.OBJECT_NOT_FOUND);
    }

    @Test
    public void getObject_found() throws Exception {
        //GIVEN
        givenStoredObject();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.getRequest(StoredObjectController.GET_OBJECT_MAPPING, KEY);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        StoredObject resultObject = objectMapperDelegator.readValue(result.getContentAsString(), StoredObject.class);
        assertThat(resultObject.getKey()).isEqualTo(KEY);
        assertThat(resultObject.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void saveObject_emptyRequestBody() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(StoredObjectController.SAVE_OBJECT_MAPPING, null);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void saveObject_nullKey() throws Exception {
        //GIVEN
        StoreObjectRequest storeObjectRequest = StoreObjectRequest.builder()
            .key(null)
            .value(VALUE)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(StoredObjectController.SAVE_OBJECT_MAPPING, storeObjectRequest);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_OBJECT_KEY);
    }

    @Test
    public void saveObject_blankKey() throws Exception {
        //GIVEN
        StoreObjectRequest storeObjectRequest = StoreObjectRequest.builder()
            .key(" ")
            .value(VALUE)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(StoredObjectController.SAVE_OBJECT_MAPPING, storeObjectRequest);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.EMPTY_OBJECT_KEY);
    }

    @Test
    public void saveObject_tooLongKey() throws Exception {
        //GIVEN
        byte[] array = new byte[300];
        new Random().nextBytes(array);
        String tooLongKey = new String(array, Charset.forName("UTF-8"));
        StoreObjectRequest storeObjectRequest = StoreObjectRequest.builder()
            .key(tooLongKey)
            .value(VALUE)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(StoredObjectController.SAVE_OBJECT_MAPPING, storeObjectRequest);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.OBJECT_KEY_TOO_LONG);
    }

    @Test
    public void saveObject_successful() throws Exception {
        //GIVEN
        StoreObjectRequest storeObjectRequest = StoreObjectRequest.builder()
            .key(KEY)
            .value(VALUE)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(StoredObjectController.SAVE_OBJECT_MAPPING, storeObjectRequest);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        Optional<StoredObject> storedObjectOptional = storedObjectDao.findById(KEY);
        assertThat(storedObjectOptional).isPresent();
        assertThat(storedObjectOptional.get().getKey()).isEqualTo(KEY);
        assertThat(storedObjectOptional.get().getValue()).isEqualTo(VALUE);
    }

    @Test
    public void saveObject_successfulOverwrite() throws Exception {
        //GIVEN
        StoreObjectRequest storeObjectRequest = StoreObjectRequest.builder()
            .key(KEY)
            .value(NEW_VALUE)
            .build();

        givenStoredObject();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(StoredObjectController.SAVE_OBJECT_MAPPING, storeObjectRequest);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        Optional<StoredObject> storedObjectOptional = storedObjectDao.findById(KEY);
        assertThat(storedObjectOptional).isPresent();
        assertThat(storedObjectOptional.get().getKey()).isEqualTo(KEY);
        assertThat(storedObjectOptional.get().getValue()).isEqualTo(NEW_VALUE);
    }

    private void givenStoredObject() {
        StoredObject storedObject = StoredObject.builder()
            .key(KEY)
            .value(VALUE)
            .build();
        storedObjectDao.save(storedObject);
    }
}
