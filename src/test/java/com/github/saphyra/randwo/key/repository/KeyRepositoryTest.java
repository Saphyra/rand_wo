package com.github.saphyra.randwo.key.repository;

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

import com.github.saphyra.common.configuration.reporitory.KeyRepositoryTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(
    initializers = ConfigFileApplicationContextInitializer.class,
    classes = KeyRepositoryTestConfig.class
)
@TestPropertySource(properties = "liquibase.changelog.location=classpath:database/change_sets/key.xml")
public class KeyRepositoryTest {
    private static final String KEY_VALUE_1 = "key_value_1";
    private static final String KEY_VALUE_2 = "key_value_2";
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final UUID KEY_ID_2 = UUID.randomUUID();

    @Autowired
    private KeyRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void findByKeyValue_notFound() {
        //WHEN
        Optional<KeyEntity> result = underTest.findByKeyValue(KEY_VALUE_1);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void findByKeyValue_found() {
        //WHEN
        KeyEntity entity1 = KeyEntity.builder()
            .keyId(KEY_ID_1)
            .keyValue(KEY_VALUE_1)
            .build();
        underTest.save(entity1);

        KeyEntity entity2 = KeyEntity.builder()
            .keyId(KEY_ID_2)
            .keyValue(KEY_VALUE_2)
            .build();
        underTest.save(entity2);

        Optional<KeyEntity> result = underTest.findByKeyValue(KEY_VALUE_1);
        //THEN
        assertThat(result).contains(entity1);
    }
}