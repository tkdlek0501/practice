package com.myapi.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;


import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// '/swagger-ui/' url 로 진입하기
@Profile({"local"}) // TODO: swaggger local 설정
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private static final String API_NAME = "Project API";
    private static final String API_VERSION = "1.0";
    private static final String API_DESCRIPTION = "HJ 프로젝트 명세서";
	
	@Bean
    public Docket api() {
        
		// TODO: authorization (token) 공통으로 적용되도록 하기
		
        return new Docket(DocumentationType.SWAGGER_2)
    		.apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant("/api/**"))
            .build();
    }
	
	public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }
}
