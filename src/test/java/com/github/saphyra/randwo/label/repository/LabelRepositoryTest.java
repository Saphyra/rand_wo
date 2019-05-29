package com.github.saphyra.randwo.label.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.saphyra.common.configuration.reporitory.LabelRepositoryTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(
    initializers = ConfigFileApplicationContextInitializer.class,
    classes = LabelRepositoryTestConfig.class
)
@TestPropertySource(properties = "liquibase.changelog.location=classpath:database/change_sets/label.xml")
public class LabelRepositoryTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();
    private static final String LABEL_VALUE_1 = "label_value_1";
    private static final String LABEL_VALUE_2 = "label_value_2";

    @Autowired
    private LabelRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void findByLabelValue_empty() {
        //GIVEN
        LabelEntity label = LabelEntity.builder()
            .labelId(LABEL_ID_1)
            .labelValue(LABEL_VALUE_1)
            .build();
        underTest.save(label);
        //WHEN
        Optional<LabelEntity> result = underTest.findByLabelValue(LABEL_VALUE_2);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void findByLabelValue_found() {
        //GIVEN
        LabelEntity label1 = LabelEntity.builder()
            .labelId(LABEL_ID_1)
            .labelValue(LABEL_VALUE_1)
            .build();
        underTest.save(label1);

        LabelEntity label2 = LabelEntity.builder()
            .labelId(LABEL_ID_2)
            .labelValue(LABEL_VALUE_2)
            .build();
        underTest.save(label2);
        //WHEN
        Optional<LabelEntity> result = underTest.findByLabelValue(LABEL_VALUE_1);
        //THEN
        assertThat(result).contains(label1);
    }
}