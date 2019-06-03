package com.github.saphyra.randwo.mapping.itemvalue.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.saphyra.common.configuration.reporitory.ItemValueMappingRepositoryTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(
    initializers = ConfigFileApplicationContextInitializer.class,
    classes = ItemValueMappingRepositoryTestConfig.class
)
@TestPropertySource(properties = "liquibase.changelog.location=classpath:database/change_sets/item_value_mapping.xml")
public class ItemValueMappingRepositoryTest {
    private static final UUID MAPPING_ID_1 = UUID.randomUUID();
    private static final UUID MAPPING_ID_2 = UUID.randomUUID();
    private static final UUID MAPPING_ID_3 = UUID.randomUUID();
    private static final UUID ITEM_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_2 = UUID.randomUUID();
    private static final UUID KEY_ID_1 = UUID.randomUUID();

    @Autowired
    private ItemValueMappingRepository underTest;

    @Test
    public void deleteByItemId() {
        //GIVEN
        ItemValueMappingEntity entity1 = ItemValueMappingEntity.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .build();
        underTest.save(entity1);

        ItemValueMappingEntity entity2 = ItemValueMappingEntity.builder()
            .mappingId(MAPPING_ID_2)
            .itemId(ITEM_ID_1)
            .build();
        underTest.save(entity2);

        ItemValueMappingEntity entity3 = ItemValueMappingEntity.builder()
            .mappingId(MAPPING_ID_3)
            .itemId(ITEM_ID_2)
            .build();
        underTest.save(entity3);
        //WHEN
        underTest.deleteByItemId(ITEM_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsOnly(entity3);
    }

    @Test
    public void findByItemIdAndKeyId() {
        //GIVEN
        ItemValueMappingEntity entity1 = ItemValueMappingEntity.builder()
            .mappingId(MAPPING_ID_1)
            .itemId(ITEM_ID_1)
            .keyId(KEY_ID_1)
            .build();
        underTest.save(entity1);

        ItemValueMappingEntity entity2 = ItemValueMappingEntity.builder()
            .mappingId(MAPPING_ID_2)
            .itemId(ITEM_ID_1)
            .build();
        underTest.save(entity2);
        //WHEN
        Optional<ItemValueMappingEntity> result = underTest.findByItemIdAndKeyId(ITEM_ID_1, KEY_ID_1);
        //THEN
        assertThat(result).contains(entity1);
    }
}