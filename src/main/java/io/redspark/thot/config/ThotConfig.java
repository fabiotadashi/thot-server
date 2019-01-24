package io.redspark.thot.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class ThotConfig {

    private static final String APPLICATION_JSON = "application/json";
    public static final String JWT = "JWT";
    public static final String HEADER = "header";

    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public Docket api(){
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new ApiKey(JWT, HttpHeaders.AUTHORIZATION, HEADER));

        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Collections.singleton(APPLICATION_JSON))
                .consumes(Collections.singleton(APPLICATION_JSON))
                .securitySchemes(schemeList)
                .ignoredParameterTypes(Authentication.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
