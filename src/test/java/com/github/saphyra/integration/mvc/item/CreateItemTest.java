package com.github.saphyra.integration.mvc.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.github.saphyra.common.configuration.MvcConfiguration;
import com.github.saphyra.common.testcomponent.DatabaseCleanup;
import com.github.saphyra.common.testcomponent.MockMvcWrapper;
import com.github.saphyra.common.testcomponent.ResponseValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.ItemController;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemConverter;
import com.github.saphyra.randwo.item.repository.ItemEntity;
import com.github.saphyra.randwo.item.repository.ItemRepository;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.repository.KeyDao;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingEntity;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    CreateItemTest.class
})
public class CreateItemTest {
    private static final String NEW_LABEL_VALUE = "new_label_value";
    private static final UUID EXISTING_LABEL_ID = UUID.randomUUID();
    private static final String EXISTING_LABEL_VALUE = "existing_label_value";
    private static final String NEW_KEY_VALUE = "new_key_value";
    private static final UUID EXISTING_KEY_ID = UUID.randomUUID();
    private static final String EXISTING_KEY_VALUE = "existing_key_value";
    private static final String VALUE_1 = "value_1";
    private static final String VALUE_2 = "value_2";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private KeyDao keyDao;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemConverter itemConverter;

    @Autowired
    private ItemLabelMappingDao itemLabelMappingDao;

    @Autowired
    private ItemValueMappingDao itemValueMappingDao;

    private Map<String, String> newKeyValues;
    private Map<UUID, String> existingKeyValueIds;

    @Autowired
    private ItemValueMappingRepository itemValueMappingRepository;

    @Before
    public void setUp() {
        newKeyValues = new HashMap<>();
        newKeyValues.put(NEW_KEY_VALUE, VALUE_1);

        existingKeyValueIds = new HashMap<>();
        existingKeyValueIds.put(EXISTING_KEY_ID, VALUE_2);
    }

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullExistingKeyValues() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(null)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_EXISTING_KEY_VALUE_IDS);
    }

    @Test
    public void nullNewKeyValues() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newKeyValues(null)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_NEW_KEY_VALUES);
    }

    @Test
    public void noValues() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newKeyValues(new HashMap<>())
            .existingKeyValueIds(new HashMap<>())
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NO_ITEM_VALUES);
    }

    @Test
    public void nullInExistingKeyValues() throws Exception {
        //GIVEN
        existingKeyValueIds.put(UUID.randomUUID(), null);

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_IN_EXISTING_KEY_VALUES);
    }

    @Test
    public void nullInNewKeyValues() throws Exception {
        //GIVEN
        newKeyValues.put("a", null);

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_IN_NEW_KEY_VALUES);
    }

    @Test
    public void keyNotFound() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.KEY_NOT_FOUND);
    }

    @Test
    public void nullExistingLabelIds() throws Exception {
        //GIVEN
        givenExistingKey();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(null)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_EXISTING_LABEL_IDS);
    }

    @Test
    public void nullNewLabels() throws Exception {
        //GIVEN
        givenExistingKey();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(null)
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_NEW_LABELS);
    }

    @Test
    public void noLabels() throws Exception {
        //GIVEN
        givenExistingKey();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Collections.emptyList())
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NO_LABELS);
    }

    @Test
    public void nullInExistingLabelIds() throws Exception {
        //GIVEN
        givenExistingKey();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID, null))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_IN_EXISTING_LABEL_IDS);
    }

    @Test
    public void nullInNewLabels() throws Exception {
        //GIVEN
        givenExistingKey();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE, null))
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.NULL_IN_NEW_LABELS);
    }

    @Test
    public void existingLabelNotFound() throws Exception {
        //GIVEN
        givenExistingKey();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Collections.emptyList())
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.LABEL_NOT_FOUND);
    }

    @Test
    public void blankNewLabelValue() throws Exception {
        //GIVEN
        givenExistingKey();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(" "))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.EMPTY_LABEL_VALUE);
    }

    @Test
    public void newLabelValueAlreadyExists() throws Exception {
        //GIVEN
        givenExistingKey();
        givenExistingLabel();

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(EXISTING_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.LABEL_VALUE_ALREADY_EXISTS);
    }

    @Test
    public void blankNewKeyValue() throws Exception {
        //GIVEN
        givenExistingKey();
        givenExistingLabel();

        Map<String, String> kv = new HashMap<>();
        kv.put(" ", NEW_KEY_VALUE);

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(kv)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.EMPTY_KEY_VALUE);
    }

    @Test
    public void newKeyValueAlreadyExists() throws Exception {
        //GIVEN
        givenExistingKey();
        givenExistingLabel();

        Map<String, String> kv = new HashMap<>();
        kv.put(EXISTING_KEY_VALUE, VALUE_2);

        ItemRequest request = ItemRequest.builder()
            .newKeyValues(kv)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.KEY_VALUE_ALREADY_EXISTS);
    }

    @Test
    public void successfullyCreatedItem() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newKeyValues(newKeyValues)
            .existingKeyValueIds(existingKeyValueIds)
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID))
            .build();

        givenExistingLabel();
        givenExistingKey();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.CREATE_ITEM_MAPPING, request);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        Optional<Key> keyOptional = keyDao.findByKeyValue(NEW_KEY_VALUE);
        assertThat(keyOptional).isPresent();
        Key key = keyOptional.get();
        assertThat(key.getKeyValue()).isEqualTo(NEW_KEY_VALUE);

        List<ItemEntity> itemEntities = itemRepository.findAll();
        assertThat(itemEntities).hasSize(1);
        Item item = itemConverter.convertEntity(itemEntities.get(0));

        Optional<Label> labelOptional = labelDao.findByLabelValue(NEW_LABEL_VALUE);
        assertThat(labelOptional).isPresent();
        Label newLabel = labelOptional.get();

        assertThat(itemLabelMappingDao.findByItemIdAndLabelId(item.getItemId(), EXISTING_LABEL_ID)).isPresent();
        assertThat(itemLabelMappingDao.findByItemIdAndLabelId(item.getItemId(), newLabel.getLabelId())).isPresent();

        Optional<ItemValueMapping> valueMappingOptional = itemValueMappingDao.findByItemIdAndKeyId(item.getItemId(), EXISTING_KEY_ID);
        assertThat(valueMappingOptional).isPresent();
        ItemValueMapping itemValueMapping = valueMappingOptional.get();
        assertThat(itemValueMapping.getValue()).isEqualTo(VALUE_2);
        assertThat(itemValueMapping.getItemId()).isEqualTo(item.getItemId());
        assertThat(itemValueMapping.getKeyId()).isEqualTo(EXISTING_KEY_ID);

        assertThat(itemValueMappingDao.findByItemIdAndKeyId(item.getItemId(), EXISTING_KEY_ID)).isPresent();
        assertThat(itemLabelMappingDao.findByItemIdAndLabelId(item.getItemId(), newLabel.getLabelId())).isPresent();

        assertThat(itemValueMappingRepository.findAll().stream()
            .map(ItemValueMappingEntity::getValue)
            .collect(Collectors.toList())
        ).containsOnly(VALUE_1, VALUE_2);
    }

    private void givenExistingKey() {
        Key key = Key.builder()
            .keyId(EXISTING_KEY_ID)
            .keyValue(EXISTING_KEY_VALUE)
            .build();
        keyDao.save(key);
    }

    private void givenExistingLabel() {
        Label label = Label.builder()
            .labelId(EXISTING_LABEL_ID)
            .labelValue(EXISTING_LABEL_VALUE)
            .build();
        labelDao.save(label);
    }
}
