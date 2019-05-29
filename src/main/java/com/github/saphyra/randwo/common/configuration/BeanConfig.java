package com.github.saphyra.randwo.common.configuration;

import com.github.saphyra.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public IdGenerator idGenerator(){
        return new IdGenerator();
    }
}
