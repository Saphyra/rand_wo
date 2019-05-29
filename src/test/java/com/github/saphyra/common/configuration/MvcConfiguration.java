package com.github.saphyra.common.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.saphyra.randwo.Application;

@TestConfiguration
@ComponentScan(
    basePackageClasses = Application.class,
    basePackages = "com.github.saphyra.common.testcomponent"
)
@Import({
    DataSourceConfiguration.class
})
@EnableJpaRepositories(basePackageClasses = Application.class)
public class MvcConfiguration {
}
