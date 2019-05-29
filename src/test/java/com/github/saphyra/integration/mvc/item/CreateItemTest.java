package com.github.saphyra.integration.mvc.item;

import static com.github.saphyra.randwo.common.ErrorCode.PARAMETER_KEY_NULL_VALUE;
import static com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator.NULL_EXISTING_LABELS;
import static com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator.NULL_NEW_LABELS;
import static com.github.saphyra.randwo.item.service.validator.itemrequest.ItemRequestValidator.NULL_VALUES;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.item.ItemController;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemRequest;
import com.github.saphyra.randwo.item.repository.ItemConverter;
import com.github.saphyra.randwo.item.repository.ItemEntity;
import com.github.saphyra.randwo.item.repository.ItemRepository;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.repository.ItemLabelMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    CreateItemTest.class
})
@Ignore
//TODO fix it
public class CreateItemTest {
    private static final String NEW_LABEL_VALUE = "new_label_value";
    private static final String VALUES_KEY = "values_key";
    private static final String VALUES_VALUE = "values_value";
    private static final UUID EXISTING_LABEL_ID = UUID.randomUUID();
    private static final String EXISTING_LABEL_VALUE = "existing_label_value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemConverter itemConverter;

    @Autowired
    private ItemLabelMappingDao mappingDao;

    private Map<String, String> filledValues;

    @Before
    public void setUp() {
        filledValues = new HashMap<>();
        filledValues.put(VALUES_KEY, VALUES_VALUE);
    }

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullValues() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        ErrorResponse response = verifyErrorResponse(result, ErrorCode.VALUE_IS_NULL);
        verifyResponseParams(response.getParams(), NULL_VALUES);
    }

    @Test
    public void nullExistingLabelIds() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(null)
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        ErrorResponse response = verifyErrorResponse(result, ErrorCode.VALUE_IS_NULL);
        verifyResponseParams(response.getParams(), NULL_EXISTING_LABELS);
    }

    @Test
    public void nullNewLabels() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(null)
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        ErrorResponse response = verifyErrorResponse(result, ErrorCode.VALUE_IS_NULL);
        verifyResponseParams(response.getParams(), NULL_NEW_LABELS);
    }

    @Test
    public void noLabels() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        verifyErrorResponse(result, ErrorCode.NO_LABELS);
    }

    @Test
    public void existingLabelNotFound() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Collections.emptyList())
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID))
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        verifyErrorResponse(result, ErrorCode.LABEL_NOT_FOUND);
    }

    @Test
    public void emptyValues() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        verifyErrorResponse(result, ErrorCode.NO_ITEM_VALUES);
    }

    @Test
    public void emptyValue() throws Exception {
        //GIVEN
        filledValues.put(VALUES_KEY, null);

        ItemRequest request = ItemRequest.builder()
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();

        givenExistingLabel();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        verifyErrorResponse(result, ErrorCode.NULL_ITEM_VALUE);
    }

    @Test
    public void emptyLabelValue() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Arrays.asList(" "))
            .existingLabelIds(Collections.emptyList())
            .build();

        givenExistingLabel();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        verifyErrorResponse(result, ErrorCode.EMPTY_LABEL_VALUE);
    }

    @Test
    public void mappingAlreadyExists() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Arrays.asList(EXISTING_LABEL_VALUE))
            .existingLabelIds(Collections.emptyList())
            .build();

        givenExistingLabel();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        verifyErrorResponse(result, ErrorCode.LABEL_VALUE_ALREADY_EXISTS);
    }

    @Test
    public void successfullyCreatedItem() throws Exception {
        //GIVEN
        ItemRequest request = ItemRequest.builder()
            .newLabels(Arrays.asList(NEW_LABEL_VALUE))
            .existingLabelIds(Arrays.asList(EXISTING_LABEL_ID))
            .build();

        givenExistingLabel();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.putRequest(ItemController.SAVE_ITEM_MAPPING, request);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        List<ItemEntity> itemEntities = itemRepository.findAll();
        assertThat(itemEntities).hasSize(1);
        Item item = itemConverter.convertEntity(itemEntities.get(0));
        assertThat(item.getValues().get(VALUES_KEY)).isEqualTo(VALUES_VALUE);

        Optional<Label> labelOptional = labelDao.findByLabelValue(NEW_LABEL_VALUE);
        assertThat(labelOptional).isPresent();
        Label newLabel = labelOptional.get();

        assertThat(mappingDao.findByItemIdAndLabelId(item.getItemId(), EXISTING_LABEL_ID)).isPresent();
        assertThat(mappingDao.findByItemIdAndLabelId(item.getItemId(), newLabel.getLabelId())).isPresent();
    }

    private ErrorResponse verifyErrorResponse(MockHttpServletResponse result, ErrorCode errorCode) throws UnsupportedEncodingException {
        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse response = objectMapperDelegator.readValue(result.getContentAsString(), ErrorResponse.class);
        assertThat(response.getErrorCode()).isEqualTo(errorCode.getErrorCode());
        return response;
    }

    private void verifyResponseParams(Map<String, String> params, String expectedValue) {
        assertThat(params).containsKey(PARAMETER_KEY_NULL_VALUE);
        assertThat(params.get(PARAMETER_KEY_NULL_VALUE)).isEqualTo(expectedValue);
    }

    private void givenExistingLabel() {
        Label label = Label.builder()
            .labelId(EXISTING_LABEL_ID)
            .labelValue(EXISTING_LABEL_VALUE)
            .build();
        labelDao.save(label);
    }
}
