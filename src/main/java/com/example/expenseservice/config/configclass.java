package com.example.expenseservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configclass {

    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }

}
