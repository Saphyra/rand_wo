package com.github.saphyra.integration.mvc.item;

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
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.item.ItemController;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.domain.RandomItemRequest;
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
    GetRandomItemTest.class
})
public class GetRandomItemTest {
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final String VALUE = "value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

    @Autowired
    private ResponseValidator responseValidator;

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
    public void nullRequestBody() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, null);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void nullLabelIds() throws Exception {
        //GIVEN
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(null)
            .keyIds(Arrays.asList(KEY_ID_1))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_LABEL_IDS);
    }

    @Test
    public void nullKeyIds() throws Exception {
        //GIVEN
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .keyIds(null)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_KEY_IDS);
    }

    @Test
    public void nullInLabelIds() throws Exception {
        //GIVEN
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1, null))
            .keyIds(Arrays.asList(KEY_ID_1))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_IN_LABEL_IDS);
    }

    @Test
    public void nullInKeyIds() throws Exception {
        //GIVEN
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .keyIds(Arrays.asList(KEY_ID_1, null))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_IN_KEY_IDS);
    }

    @Test
    public void returnRandomItem() throws Exception {
        //GIVEN
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .keyIds(Arrays.asList(KEY_ID_1))
            .build();

        Item item = Item.builder().itemId(ITEM_ID_1).build();
        itemDao.save(item);

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
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, request);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        ItemView itemView = objectMapperDelegator.readValue(result.getContentAsString(), ItemView.class);
        assertThat(itemView.getItemId()).isEqualTo(ITEM_ID_1);
        assertThat(itemView.getLabelIds()).contains(LABEL_ID_1);
        assertThat(itemView.getColumns().get(KEY_ID_1)).isEqualTo(VALUE);
    }

    @Test
    public void randomItemCannotBeSelected_doesNotContainKey() throws Exception {
        //GIVEN
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .keyIds(Arrays.asList(KEY_ID_1))
            .build();

        Item selectableItem = Item.builder().itemId(ITEM_ID_1).build();
        itemDao.save(selectableItem);

        ItemLabelMapping labelMapping = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID_1)
            .build();
        itemLabelMappingDao.save(labelMapping);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyNotFoundRequest(result, ErrorCode.RANDOM_ITEM_CANNOT_BE_SELECTED);
    }

    @Test
    public void randomItemCannotBeSelected_doesNotContainLabel() throws Exception {
        //GIVEN
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(Arrays.asList(LABEL_ID_1))
            .keyIds(Arrays.asList(KEY_ID_1))
            .build();

        Item item = Item.builder().itemId(ITEM_ID_1).build();
        itemDao.save(item);

        ItemValueMapping valueMapping = ItemValueMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_1)
            .keyId(KEY_ID_1)
            .value(VALUE)
            .build();
        itemValueMappingDao.save(valueMapping);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_RANDOM_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyNotFoundRequest(result, ErrorCode.RANDOM_ITEM_CANNOT_BE_SELECTED);
    }
}
