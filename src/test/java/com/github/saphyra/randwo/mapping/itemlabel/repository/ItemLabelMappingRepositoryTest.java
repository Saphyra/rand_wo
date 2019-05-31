package com.github.saphyra.randwo.mapping.itemlabel.repository;

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

import com.github.saphyra.common.configuration.reporitory.ItemLabelMappingRepositoryTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(
    initializers = ConfigFileApplicationContextInitializer.class,
    classes = ItemLabelMappingRepositoryTestConfig.class
)
@TestPropertySource(properties = "liquibase.changelog.location=classpath:database/change_sets/item_label_mapping.xml")
public class ItemLabelMappingRepositoryTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();
    private static final UUID MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID MAPPING_ID_3 = UUID.randomUUID();
    private static final UUID MAPPING_ID_4 = UUID.randomUUID();
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_2 = UUID.randomUUID();

    @Autowired
    private ItemLabelMappingRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByItemId() {
        //GIVEN
        ItemLabelMappingEntity entity1 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID_2)
            .build();
        underTest.save(entity1);

        ItemLabelMappingEntity entity2 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_2)
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID_1)
            .build();
        underTest.save(entity2);

        ItemLabelMappingEntity entity3 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_3)
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID_2)
            .build();
        underTest.save(entity3);
        //WHEN
        underTest.deleteByItemId(ITEM_ID_2);
        //THEN
        assertThat(underTest.findAll()).containsOnly(entity1);
    }

    @Test
    public void findByItemIdAndLabelId_notFound() {
        //GIVEN
        ItemLabelMappingEntity entity1 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID_2)
            .build();
        underTest.save(entity1);

        ItemLabelMappingEntity entity2 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_2)
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID_1)
            .build();
        underTest.save(entity2);

        ItemLabelMappingEntity entity3 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_3)
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID_2)
            .build();
        underTest.save(entity3);
        //WHEN
        Optional<ItemLabelMappingEntity> result = underTest.findByItemIdAndLabelId(ITEM_ID_1, LABEL_ID_1);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void findByItemIdAndLabelId_found() {
        //GIVEN
        ItemLabelMappingEntity entity1 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID_2)
            .build();
        underTest.save(entity1);

        ItemLabelMappingEntity entity2 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_2)
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID_1)
            .build();
        underTest.save(entity2);

        ItemLabelMappingEntity entity3 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_3)
            .itemId(ITEM_ID_2)
            .labelId(LABEL_ID_2)
            .build();
        underTest.save(entity3);

        ItemLabelMappingEntity entity4 = ItemLabelMappingEntity.builder()
            .mappingId(MAPPING_ID_4)
            .itemId(ITEM_ID_1)
            .labelId(LABEL_ID_1)
            .build();
        underTest.save(entity4);
        //WHEN
        Optional<ItemLabelMappingEntity> result = underTest.findByItemIdAndLabelId(ITEM_ID_1, LABEL_ID_1);
        //THEN
        assertThat(result).contains(entity4);
    }
}