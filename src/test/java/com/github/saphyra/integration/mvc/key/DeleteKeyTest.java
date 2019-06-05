package com.github.saphyra.integration.mvc.key;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
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
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.KeyController;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    DeleteKeyTest.class
})
public class DeleteKeyTest {
    private static final UUID MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final UUID KEY_ID_2 = UUID.randomUUID();
    private static final UUID KEY_ID_3 = UUID.randomUUID();
    private static final String KEY_VALUE = "key_value";
    private static final String VALUE = "value";
    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private KeyDao keyDao;

    @Autowired
    private ItemValueMappingDao itemValueMappingDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullKeyIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(KeyController.DELETE_KEYS_MAPPING, null);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void nullInKeyIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(KeyController.DELETE_KEYS_MAPPING, Arrays.asList(KEY_ID_1, null));
        //THEN
        ErrorResponse errorResponse = responseValidator.verifyBadRequest(result, ErrorCode.VALUE_IS_NULL);
        responseValidator.verifyResponseParams(errorResponse.getParams(), PARAMETER_KEY_NULL_VALUE, ErrorCode.NULL_IN_KEY_IDS.getErrorCode());
    }

    @Test
    public void hasOnlyOneKey() throws Exception {
        //GIVEN
        Key key1 = Key.builder()
            .keyId(KEY_ID_1)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key1);

        Key key2 = Key.builder()
            .keyId(KEY_ID_2)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key2);

        Key key3 = Key.builder()
            .keyId(KEY_ID_3)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key3);

        ItemValueMapping mapping1 = ItemValueMapping.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(mapping1);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(KeyController.DELETE_KEYS_MAPPING, Arrays.asList(KEY_ID_1, KEY_ID_2));
        //THEN
        responseValidator.verifyErrorResponse(result, HttpStatus.UNPROCESSABLE_ENTITY, ErrorCode.ITEM_HAS_ONLY_ONE_KEY);
    }

    @Test
    public void successfulDeletion() throws Exception {
        //GIVEN
        //GIVEN
        Key key1 = Key.builder()
            .keyId(KEY_ID_1)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key1);

        Key key2 = Key.builder()
            .keyId(KEY_ID_2)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key2);

        Key key3 = Key.builder()
            .keyId(KEY_ID_3)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key3);

        ItemValueMapping mapping1 = ItemValueMapping.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(mapping1);

        ItemValueMapping mapping2 = ItemValueMapping.builder()
            .mappingId(MAPPING_ID_2)
            .itemId(ITEM_ID)
            .keyId(KEY_ID_3)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(mapping2);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(KeyController.DELETE_KEYS_MAPPING, Arrays.asList(KEY_ID_1, KEY_ID_2));
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(keyDao.findById(KEY_ID_1)).isEmpty();
        assertThat(keyDao.findById(KEY_ID_2)).isEmpty();
        assertThat(keyDao.findById(KEY_ID_3)).isPresent();

        assertThat(itemValueMappingDao.findById(MAPPING_ID_1)).isEmpty();
        assertThat(itemValueMappingDao.findById(MAPPING_ID_2)).isPresent();
    }
}
