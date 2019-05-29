package com.github.saphyra.common.configuration.reporitory;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.saphyra.common.configuration.DataSourceConfiguration;
import com.github.saphyra.randwo.common.configuration.DatabaseConfig;
import com.github.saphyra.randwo.key.repository.KeyRepository;

@TestConfiguration
@Import({
    DatabaseConfig.class,
    DataSourceConfiguration.class
})
@EnableJpaRepositories(basePackageClasses = KeyRepository.class)
public class KeyRepositoryTestConfig {
}
