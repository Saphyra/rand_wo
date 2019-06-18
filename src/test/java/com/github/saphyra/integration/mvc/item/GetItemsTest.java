package com.github.saphyra.integration.mvc.item;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.item.ItemController;
import com.github.saphyra.randwo.item.domain.GetItemsRequest;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.repository.ItemDao;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    GetItemsTest.class
})
public class GetItemsTest {
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_ID_3 = UUID.randomUUID();
    private static final UUID ITEM_ID_4 = UUID.randomUUID();
    private static final String SEARCH_BY_LABEL = "label_value";
    private static final String SEARCH_BY_KEY = "key_value";
    private static final String SEARCH_BY_VALUE = "value";
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();
    private static final String NON_MATCHING_LABEL_VALUE = "non_matching_lv";
    private static final String MATCHING_LABEL_VALUE = "matching_label_value";
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final UUID KEY_ID_2 = UUID.randomUUID();
    private static final String NON_MATCHING_KEY_VALUE = "non_matching_kv";
    private static final String MATCHING_VALUE = "matching_value";
    private static final String NON_MATCHING_VALUE = "non_matching_v";
    private static final String MATCHING_KEY_VALUE = "matching_key_value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemLabelMappingDao itemLabelMappingDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ItemValueMappingDao itemValueMappingDao;

    @Autowired
    private KeyDao keyDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullQueryParams() throws Exception {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .build();

        Item item = Item.builder().itemId(ITEM_ID_1).build();
        itemDao.save(item);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_ITEMS_MAPPING, request);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        List<ItemView> itemViews = objectMapperDelegator.readArrayValue(result.getContentAsString(), ItemView[].class);
        assertThat(itemViews.stream().filter(itemView -> itemView.getItemId().equals(ITEM_ID_1)).findAny()).isPresent();
    }

    @Test
    public void filtered() throws Exception {
        //GIVEN
        GetItemsRequest request = GetItemsRequest.builder()
            .searchByLabel(SEARCH_BY_LABEL)
            .searchByKey(SEARCH_BY_KEY)
            .searchByValue(SEARCH_BY_VALUE)
            .build();

        Item itemWithNonMatchingLabel = Item.builder().itemId(ITEM_ID_1).build();
        itemDao.save(itemWithNonMatchingLabel);
        ItemLabelMapping labelMappingForNonMatchingLabel = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID_1)
            .build();
        itemLabelMappingDao.save(labelMappingForNonMatchingLabel);
        ItemValueMapping valueMappingForNonMatchingLabel = ItemValueMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_1)
            .keyId(KEY_ID_1)
            .value(MATCHING_VALUE)
            .build();
        itemValueMappingDao.save(valueMappingForNonMatchingLabel);

        Item itemWithNonMatchingKey = Item.builder().itemId(ITEM_ID_2).build();
        itemDao.save(itemWithNonMatchingKey);
        ItemLabelMapping labelMappingForNonMatchingKey = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID_2)
            .build();
        itemLabelMappingDao.save(labelMappingForNonMatchingKey);
        ItemValueMapping valueMappingForNonMatchingKey = ItemValueMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_2)
            .keyId(KEY_ID_1)
            .value(MATCHING_VALUE)
            .build();
        itemValueMappingDao.save(valueMappingForNonMatchingKey);

        Item itemWithNonMatchingValue = Item.builder().itemId(ITEM_ID_3).build();
        itemDao.save(itemWithNonMatchingValue);
        ItemLabelMapping labelMappingForNonMatchingValue = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_3)
            .labelId(LABEL_ID_2)
            .build();
        itemLabelMappingDao.save(labelMappingForNonMatchingValue);
        ItemValueMapping valueMappingForNonMatchingValue = ItemValueMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_3)
            .keyId(KEY_ID_2)
            .value(NON_MATCHING_VALUE)
            .build();
        itemValueMappingDao.save(valueMappingForNonMatchingValue);

        Item matchingItem = Item.builder().itemId(ITEM_ID_4).build();
        itemDao.save(matchingItem);
        ItemLabelMapping labelMappingForMatchingItem = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_4)
            .labelId(LABEL_ID_2)
            .build();
        itemLabelMappingDao.save(labelMappingForMatchingItem);
        ItemValueMapping valueMappingForMatchingItem = ItemValueMapping.builder()
            .mappingId(UUID.randomUUID())
            .itemId(ITEM_ID_4)
            .keyId(KEY_ID_2)
            .value(MATCHING_VALUE)
            .build();
        itemValueMappingDao.save(valueMappingForMatchingItem);

        Label labelWithNonMatchingValue = Label.builder()
            .labelId(LABEL_ID_1)
            .labelValue(NON_MATCHING_LABEL_VALUE)
            .build();
        labelDao.save(labelWithNonMatchingValue);

        Label labelWithMatchingValue = Label.builder()
            .labelId(LABEL_ID_2)
            .labelValue(MATCHING_LABEL_VALUE)
            .build();
        labelDao.save(labelWithMatchingValue);

        Key keyWithNonMatchingValue = Key.builder()
            .keyId(KEY_ID_1)
            .keyValue(NON_MATCHING_KEY_VALUE)
            .build();
        keyDao.save(keyWithNonMatchingValue);

        Key keyWithMatchingValue = Key.builder()
            .keyId(KEY_ID_2)
            .keyValue(MATCHING_KEY_VALUE)
            .build();
        keyDao.save(keyWithMatchingValue);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(ItemController.GET_ITEMS_MAPPING, request);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        List<ItemView> itemViews = objectMapperDelegator.readArrayValue(result.getContentAsString(), ItemView[].class);
        assertThat(itemViews).hasSize(1);
        assertThat(itemViews.stream().filter(itemView -> itemView.getItemId().equals(ITEM_ID_4)).findAny()).isPresent();
    }
}
