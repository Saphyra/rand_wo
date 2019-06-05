package com.github.saphyra.integration.mvc.key;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

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
import com.github.saphyra.randwo.key.KeyController;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    UpdateKeyTest.class
})
public class UpdateKeyTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String EXISTING_KEY_VALUE = "existing_key_value";
    private static final String NEW_KEY_VALUE = "new_key_value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private KeyDao keyDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullNewKeyValue() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.UPDATE_KEY_MAPPING, null, KEY_ID);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void emptyNewKeyValue() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.UPDATE_KEY_MAPPING, " ", KEY_ID);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.EMPTY_KEY_VALUE);
    }

    @Test
    public void existingKeyValue() throws Exception {
        //GIVEN
        givenExistingKey();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.UPDATE_KEY_MAPPING, EXISTING_KEY_VALUE, KEY_ID);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.KEY_VALUE_ALREADY_EXISTS);
    }

    @Test
    public void keyNotFound() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.UPDATE_KEY_MAPPING, NEW_KEY_VALUE, KEY_ID);
        //THEN
        responseValidator.verifyNotFoundRequest(result, ErrorCode.KEY_NOT_FOUND);
    }

    @Test
    public void successfulUpdate() throws Exception {
        //GIVEN
        givenExistingKey();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.UPDATE_KEY_MAPPING, NEW_KEY_VALUE, KEY_ID);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(keyDao.findById(KEY_ID).get().getKeyValue()).isEqualTo(NEW_KEY_VALUE);
    }

    private void givenExistingKey() {
        Key key = Key.builder()
            .keyId(KEY_ID)
            .keyValue(EXISTING_KEY_VALUE)
            .build();
        keyDao.save(key);
    }
}
