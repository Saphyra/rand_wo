package com.github.saphyra.integration.mvc.label;

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
import com.github.saphyra.randwo.label.LabelController;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    DeleteLabelTest.class
})
public class DeleteLabelTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();
    private static final UUID LABEL_ID_3 = UUID.randomUUID();
    private static final String LABEL_VALUE = "label_value";
    private static final UUID MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID ITEM_ID = UUID.randomUUID();
    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ItemLabelMappingDao itemLabelMappingDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void nullLabelIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(LabelController.DELETE_LABELS_MAPPING, null);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void nullInLabelIds() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(LabelController.DELETE_LABELS_MAPPING, Arrays.asList(LABEL_ID_1, null));
        //THEN
        ErrorResponse errorResponse = responseValidator.verifyBadRequest(result, ErrorCode.VALUE_IS_NULL);
        responseValidator.verifyResponseParams(errorResponse.getParams(), PARAMETER_KEY_NULL_VALUE, ErrorCode.NULL_IN_LABEL_IDS.getErrorCode());
    }

    @Test
    public void hasOnlyOneLabel() throws Exception {
        //GIVEN
        Label label1 = Label.builder()
            .labelId(LABEL_ID_1)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(label1);

        Label label2 = Label.builder()
            .labelId(LABEL_ID_2)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(label2);

        Label label3 = Label.builder()
            .labelId(LABEL_ID_3)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(label3);

        ItemLabelMapping mapping1 = ItemLabelMapping.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID)
            .labelId(LABEL_ID_1)
            .build();
        itemLabelMappingDao.save(mapping1);

        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(LabelController.DELETE_LABELS_MAPPING, Arrays.asList(LABEL_ID_1, LABEL_ID_2));
        //THEN
        responseValidator.verifyErrorResponse(result, HttpStatus.UNPROCESSABLE_ENTITY, ErrorCode.ITEM_HAS_ONLY_ONE_LABEL);
    }

    @Test
    public void successfulDeletion() throws Exception {
        //GIVEN
        Label label1 = Label.builder()
            .labelId(LABEL_ID_1)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(label1);

        Label label2 = Label.builder()
            .labelId(LABEL_ID_2)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(label2);

        Label label3 = Label.builder()
            .labelId(LABEL_ID_3)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(label3);

        ItemLabelMapping mapping1 = ItemLabelMapping.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID)
            .labelId(LABEL_ID_1)
            .build();
        itemLabelMappingDao.save(mapping1);

        ItemLabelMapping mapping2 = ItemLabelMapping.builder()
            .mappingId(MAPPING_ID_2)
            .itemId(ITEM_ID)
            .labelId(LABEL_ID_3)
            .build();
        itemLabelMappingDao.save(mapping2);

        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.deleteRequest(LabelController.DELETE_LABELS_MAPPING, Arrays.asList(LABEL_ID_1, LABEL_ID_2));
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(labelDao.findById(LABEL_ID_1)).isEmpty();
        assertThat(labelDao.findById(LABEL_ID_2)).isEmpty();
        assertThat(labelDao.findById(LABEL_ID_3)).isPresent();

        assertThat(itemLabelMappingDao.findById(MAPPING_ID_1)).isEmpty();
        assertThat(itemLabelMappingDao.findById(MAPPING_ID_2)).isPresent();
    }
}
