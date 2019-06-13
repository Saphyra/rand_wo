package com.github.saphyra.randwo.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.Random;

@Configuration
public class BeanConfig {
    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
