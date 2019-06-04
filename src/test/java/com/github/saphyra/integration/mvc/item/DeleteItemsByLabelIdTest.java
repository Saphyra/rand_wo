package com.github.saphyra.integration.mvc.item;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
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
import com.github.saphyra.randwo.item.ItemController;
import com.github.saphyra.randwo.item.domain.DeleteItemRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemDeleteMethod;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    DeleteItemsByItemIdTest.class
})
public class DeleteItemsByLabelIdTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_VALUE_MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_VALUE_MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final String VALUE = "value";
    private static final UUID ITEM_LABEL_MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_LABEL_MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_LABEL_MAPPING_ID_3 = UUID.randomUUID();

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

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullLabelIds() throws Exception {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(null)
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_LABEL_IDS_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_LABEL_IDS);
    }

    @Test
    public void nullInLabelIds() throws Exception {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1, null))
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_LABEL_IDS_MAPPING, request);
        //THEN
        ErrorResponse errorResponse = responseValidator.verifyBadRequest(result, ErrorCode.VALUE_IS_NULL);
        responseValidator.verifyResponseParams(errorResponse.getParams(), PARAMETER_KEY_NULL_VALUE, ErrorCode.NULL_IN_LABEL_IDS.getErrorCode());
    }

    @Test
    public void emptyLabelIds() throws Exception {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Collections.emptyList())
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_LABEL_IDS_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NO_LABELS);
    }

    @Test
    public void nullDeleteMethod() throws Exception {
        //GIVEN
        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .itemDeleteMethod(null)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_LABEL_IDS_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_ITEM_DELETE_METHOD);
    }

    @Test
    public void deleteContains() throws Exception {
        //GIVEN
        Item item1 = Item.builder()
            .itemId(ITEM_ID_1)
            .build();
        itemDao.save(item1);

        Item item2 = Item.builder()
            .itemId(ITEM_ID_2)
            .build();
        itemDao.save(item2);

        ItemValueMapping itemValueMapping1 = ItemValueMapping.builder()
            .mappingId(ITEM_VALUE_MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(itemValueMapping1);

        ItemValueMapping itemValueMapping2 = ItemValueMapping.builder()
            .mappingId(ITEM_VALUE_MAPPING_ID_2)
            .itemId(ITEM_ID_2)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(itemValueMapping2);

        ItemLabelMapping itemLabelMapping1 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_1)
            .labelId(LABEL_ID_1)
            .itemId(ITEM_ID_1)
            .build();
        itemLabelMappingDao.save(itemLabelMapping1);

        ItemLabelMapping itemLabelMapping2 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_2)
            .labelId(LABEL_ID_2)
            .itemId(ITEM_ID_2)
            .build();
        itemLabelMappingDao.save(itemLabelMapping2);

        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_LABEL_IDS_MAPPING, request);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(itemDao.findById(ITEM_ID_1)).isEmpty();
        assertThat(itemDao.findById(ITEM_ID_2)).isPresent();

        assertThat(itemValueMappingDao.findById(ITEM_VALUE_MAPPING_ID_1)).isEmpty();
        assertThat(itemValueMappingDao.findById(ITEM_VALUE_MAPPING_ID_2)).isPresent();

        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_1)).isEmpty();
        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_2)).isPresent();
    }

    @Test
    public void deleteContainsAll() throws Exception {
        //GIVEN
        Item item1 = Item.builder()
            .itemId(ITEM_ID_1)
            .build();
        itemDao.save(item1);

        Item item2 = Item.builder()
            .itemId(ITEM_ID_2)
            .build();
        itemDao.save(item2);

        ItemValueMapping itemValueMapping1 = ItemValueMapping.builder()
            .mappingId(ITEM_VALUE_MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(itemValueMapping1);

        ItemValueMapping itemValueMapping2 = ItemValueMapping.builder()
            .mappingId(ITEM_VALUE_MAPPING_ID_2)
            .itemId(ITEM_ID_2)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(itemValueMapping2);

        ItemLabelMapping itemLabelMapping1 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_1)
            .labelId(LABEL_ID_1)
            .itemId(ITEM_ID_1)
            .build();
        itemLabelMappingDao.save(itemLabelMapping1);

        ItemLabelMapping itemLabelMapping2 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_2)
            .labelId(LABEL_ID_2)
            .itemId(ITEM_ID_1)
            .build();
        itemLabelMappingDao.save(itemLabelMapping2);

        ItemLabelMapping itemLabelMapping3 = ItemLabelMapping.builder()
            .mappingId(ITEM_LABEL_MAPPING_ID_3)
            .labelId(LABEL_ID_2)
            .itemId(ITEM_ID_2)
            .build();
        itemLabelMappingDao.save(itemLabelMapping3);

        DeleteItemRequest request = DeleteItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .itemDeleteMethod(ItemDeleteMethod.CONTAINS_ALL)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(ItemController.DELETE_BY_LABEL_IDS_MAPPING, request);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(itemDao.findById(ITEM_ID_1)).isEmpty();
        assertThat(itemDao.findById(ITEM_ID_2)).isPresent();

        assertThat(itemValueMappingDao.findById(ITEM_VALUE_MAPPING_ID_1)).isEmpty();
        assertThat(itemValueMappingDao.findById(ITEM_VALUE_MAPPING_ID_2)).isPresent();

        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_1)).isEmpty();
        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_2)).isEmpty();
        assertThat(itemLabelMappingDao.findById(ITEM_LABEL_MAPPING_ID_3)).isPresent();
    }
}
