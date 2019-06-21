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
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.label.LabelController;
import com.github.saphyra.randwo.label.domain.Label;
import com.github.saphyra.randwo.label.domain.LabelView;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    GetLabelTest.class
})
public class GetLabelTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final String LABEL_VALUE = "label_value";
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ResponseValidator responseValidator;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

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
    public void notFound() throws Exception {
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.getRequest(LabelController.GET_LABEL_MAPPING, LABEL_ID_1);
        //THEN
        responseValidator.verifyNotFoundRequest(result, ErrorCode.LABEL_NOT_FOUND);
    }

    @Test
    public void found_notDeletable() throws Exception {
        //GIVEN
        Label key = Label.builder()
            .labelId(LABEL_ID_1)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(key);

        ItemLabelMapping itemLabelMapping = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .labelId(LABEL_ID_1)
            .itemId(ITEM_ID)
            .build();
        itemLabelMappingDao.save(itemLabelMapping);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.getRequest(LabelController.GET_LABEL_MAPPING, LABEL_ID_1);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        LabelView resultLabel = objectMapperDelegator.readValue(result.getContentAsString(), LabelView.class);
        assertThat(resultLabel.getLabelId()).isEqualTo(LABEL_ID_1);
        assertThat(resultLabel.getLabelValue()).isEqualTo(LABEL_VALUE);
        assertThat(resultLabel.getItems()).isEqualTo(1);
        assertThat(resultLabel.isDeletable()).isFalse();
    }

    @Test
    public void found_deletable() throws Exception {
        //GIVEN
        Label key = Label.builder()
            .labelId(LABEL_ID_1)
            .labelValue(LABEL_VALUE)
            .build();
        labelDao.save(key);

        ItemLabelMapping itemLabelMapping1 = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .labelId(LABEL_ID_1)
            .itemId(ITEM_ID)
            .build();
        itemLabelMappingDao.save(itemLabelMapping1);

        ItemLabelMapping itemLabelMapping2 = ItemLabelMapping.builder()
            .mappingId(UUID.randomUUID())
            .labelId(LABEL_ID_2)
            .itemId(ITEM_ID)
            .build();
        itemLabelMappingDao.save(itemLabelMapping2);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.getRequest(LabelController.GET_LABEL_MAPPING, LABEL_ID_1);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        LabelView resultLabel = objectMapperDelegator.readValue(result.getContentAsString(), LabelView.class);
        assertThat(resultLabel.getLabelId()).isEqualTo(LABEL_ID_1);
        assertThat(resultLabel.getLabelValue()).isEqualTo(LABEL_VALUE);
        assertThat(resultLabel.getItems()).isEqualTo(1);
        assertThat(resultLabel.isDeletable()).isTrue();
    }
}
