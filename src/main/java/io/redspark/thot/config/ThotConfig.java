package io.redspark.thot.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThotConfig {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

}
