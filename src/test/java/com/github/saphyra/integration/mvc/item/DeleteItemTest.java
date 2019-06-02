package com.github.saphyra.integration.mvc.item;

import com.github.saphyra.common.configuration.MvcConfiguration;
import com.github.saphyra.common.testcomponent.DatabaseCleanup;
import com.github.saphyra.common.testcomponent.MockMvcWrapper;
import com.github.saphyra.common.testcomponent.ResponseValidator;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.ItemController;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    DeleteItemTest.class
})
public class DeleteItemTest {
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_ID_3 = UUID.randomUUID();
    private static final UUID ITEM_LABEL_MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_LABEL_MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_LABEL_MAPPING_ID_3 = UUID.randomUUID();
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final UUID ITEM_VALUE_MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_VALUE_MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_VALUE_MAPPING_ID_3 = UUID.randomUUID();
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final String VALUE = "value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemLabelMappingDao itemLabelMappingDao;

    @Autowired
    private ItemValueMappingDao itemValueMappingDao;

    @Before
    public void setUp() {
        givenExistingItems();
        givenExistingItemLabelMapping();
        givenExistingItemValueMapping();
    }

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullItemIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_IDS_MAPPING, null);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void emptyItemIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_IDS_MAPPING, Collections.emptyList());
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.EMPTY_ITEM_IDS);
    }

    @Test
    public void nullInItemIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_IDS_MAPPING, Arrays.asList(ITEM_ID_1, null));
        //THEN
        ErrorResponse errorResponse = responseValidator.verifyBadRequest(result, ErrorCode.VALUE_IS_NULL);
        verifyResponseParams(errorResponse.getParams(), ErrorCode.NULL_ITEM_ID.getErrorCode());
    }

    @Test
    public void successfulDeletion() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_IDS_MAPPING, Arrays.asList(ITEM_ID_1, ITEM_ID_2));
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(itemDao.findById(ITEM_ID_1)).isEmpty();
        assertThat(itemDao.findById(ITEM_ID_2)).isEmpty();
        assertThat(itemDao.findById(ITEM_ID_3)).isPresent();

        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_1)).isEmpty();
        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_2)).isEmpty();
        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_3)).isPresent();

        assertThat(itemValueMappingDao.findById(ITEM_VALUE_MAPPING_ID_1)).isEmpty();
        assertThat(itemValueMappingDao.findById(ITEM_VALUE_MAPPING_ID_2)).isEmpty();
        assertThat(itemValueMappingDao.findById(ITEM_VALUE_MAPPING_ID_3)).isPresent();
    }

    private void verifyResponseParams(Map<String, String> params, String expectedValue) {
        assertThat(params).containsKey(PARAMETER_KEY_NULL_VALUE);
        assertThat(params.get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(expectedValue);
    }

    private void givenExistingItems() {
        Item item1 = Item.builder()
            .itemId(ITEM_ID_1)
            .build();
        itemDao.save(item1);

        Item item2 = Item.builder()
            .itemId(ITEM_ID_2)
            .build();
        itemDao.save(item2);

        Item item3 = Item.builder()
            .itemId(ITEM_ID_3)
            .build();
        itemDao.save(item3);
    }

    private void givenExistingItemLabelMapping() {
        ItemLabelMapping mapping1 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID)
            .build();
        itemLabelMappingDao.save(mapping1);

        ItemLabelMapping mapping2 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_2)
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID)
            .build();
        itemLabelMappingDao.save(mapping2);

        ItemLabelMapping mapping3 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_3)
            .itemId(ITEM_ID_3)
            .labelId(LABEL_ID)
            .build();
        itemLabelMappingDao.save(mapping3);
    }

    private void givenExistingItemValueMapping() {
        ItemValueMapping mapping1 = ItemValueMapping.builder()
            .mappingId(ITEM_VALUE_MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(mapping1);

        ItemValueMapping mapping2 = ItemValueMapping.builder()
            .mappingId(ITEM_VALUE_MAPPING_ID_2)
            .itemId(ITEM_ID_2)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(mapping2);

        ItemValueMapping mapping3 = ItemValueMapping.builder()
            .mappingId(ITEM_VALUE_MAPPING_ID_3)
            .itemId(ITEM_ID_3)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(mapping3);
    }
}
