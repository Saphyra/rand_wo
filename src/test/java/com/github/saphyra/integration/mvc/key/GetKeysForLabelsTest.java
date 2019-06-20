package com.github.saphyra.integration.mvc.key;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
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
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.key.KeyController;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    GetKeysForLabelsTest.class
})
public class GetKeysForLabelsTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final String KEY_VALUE = "key_value";
    private static final String VALUE = "value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private KeyDao keyDao;

    @Autowired
    private ItemLabelMappingDao itemLabelMappingDao;

    @Autowired
    private ItemValueMappingDao itemValueMappingDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void emptyRequestBody() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.GET_KEYS_FOR_LABELS_MAPPING, null);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void nullInLabelIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.GET_KEYS_FOR_LABELS_MAPPING, Arrays.asList(LABEL_ID_1, null));

        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_IN_LABEL_IDS);
    }

    @Test
    public void getKeysForLabels() throws Exception {
        //GIVEN
        ItemLabelMapping itemLabelMapping = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID_1)
            .build();
        itemLabelMappingDao.save(itemLabelMapping);

        ItemValueMapping itemValueMapping = ItemValueMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_1)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(itemValueMapping);

        Key key = Key.builder()
            .keyId(KEY_ID_1)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key);

        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(KeyController.GET_KEYS_FOR_LABELS_MAPPING, Arrays.asList(LABEL_ID_1));
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        List<Key> resultKeys = objectMapperDelegator.readArrayValue(result.getContentAsString(), Key[].class);
        assertThat(resultKeys).hasSize(1);
        Key resultKey = resultKeys.get(0);
        assertThat(resultKey.getKeyId()).isEqualTo(KEY_ID_1);
        assertThat(resultKey.getKeyValue()).isEqualTo(KEY_VALUE);
    }
}
