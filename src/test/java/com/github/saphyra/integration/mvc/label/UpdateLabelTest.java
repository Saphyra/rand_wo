package com.github.saphyra.integration.mvc.label;

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
import com.github.saphyra.randwo.label.LabelController;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    UpdateLabelTest.class
})
public class UpdateLabelTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String EXISTING_LABEL_VALUE = "existing_label_value";
    private static final String NEW_LABEL_VALUE = "new_label_value";

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
    public void nullNewLabelValue() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(LabelController.UPDATE_LABEL_MAPPING, null, LABEL_ID);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.REQUEST_BODY_MISSING);
    }

    @Test
    public void emptyNewLabelValue() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(LabelController.UPDATE_LABEL_MAPPING, " ", LABEL_ID);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.EMPTY_LABEL_VALUE);
    }

    @Test
    public void existingLabelValue() throws Exception {
        //GIVEN
        givenExistingLabel();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(LabelController.UPDATE_LABEL_MAPPING, EXISTING_LABEL_VALUE, LABEL_ID);
        //THEN
        responseValidator.verifyBadRequest(result, ErrorCode.LABEL_VALUE_ALREADY_EXISTS);
    }

    @Test
    public void labelNotFound() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(LabelController.UPDATE_LABEL_MAPPING, NEW_LABEL_VALUE, LABEL_ID);
        //THEN
        responseValidator.verifyNotFoundRequest(result, ErrorCode.LABEL_NOT_FOUND);
    }

    @Test
    public void successfulUpdate() throws Exception {
        //GIVEN
        givenExistingLabel();
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.postRequest(LabelController.UPDATE_LABEL_MAPPING, NEW_LABEL_VALUE, LABEL_ID);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(labelDao.findById(LABEL_ID).get().getLabelValue()).isEqualTo(NEW_LABEL_VALUE);
    }

    private void givenExistingLabel() {
        Label label = Label.builder()
            .labelId(LABEL_ID)
            .labelValue(EXISTING_LABEL_VALUE)
            .build();
        labelDao.save(label);
    }
}
