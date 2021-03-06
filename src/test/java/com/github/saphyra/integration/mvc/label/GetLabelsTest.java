package com.github.saphyra.integration.mvc.label;

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
import com.github.saphyra.randwo.label.LabelController;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.domain.LabelView;
import com.github.saphyra.randwo.label.repository.LabelDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    GetLabelsTest.class
})
public class GetLabelsTest {
    private static final UUID LABEL_ID = UUID.randomUUID();
    private static final String LABEL_VALUE = "label_value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private LabelDao labelDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void getAllLabels() throws Exception {
        //GIVEN
        Label key = Label.builder()
            .labelId(LABEL_ID)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(key);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.getRequest(LabelController.GET_LABELS_MAPPING);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        List<LabelView> resultLabels = objectMapperDelegator.readArrayValue(result.getContentAsString(), LabelView[].class);
        assertThat(resultLabels).hasSize(1);
        LabelView resultLabel = resultLabels.get(0);
        assertThat(resultLabel.getLabelId()).isEqualTo(LABEL_ID);
        assertThat(resultLabel.getLabelValue()).isEqualTo(LABEL_VALUE);
        assertThat(resultLabel.getItems()).isEqualTo(0);
        assertThat(resultLabel.isDeletable()).isTrue();
    }
}
