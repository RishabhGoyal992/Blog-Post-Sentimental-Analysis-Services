package com.rishabh.blog.config;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";

	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private List<springfox.documentation.spi.service.contexts.SecurityContext> securityContexts() {
		return Arrays.asList(springfox.documentation.spi.service.contexts.SecurityContext.builder().securityReferences(sf()).build());
	}

	private List<SecurityReference> sf() {

//		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

		return Arrays.asList(new SecurityReference("JWT", (springfox.documentation.service.AuthorizationScope[]) new AuthorizationScope[] { scope }));
	} 

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo getInfo() {
		return new ApiInfo("Blogging Application", "Post, Categorize and Comment!", "1.0", "Terms of Service", new Contact("Rishabh Goyal", "", "rishabhgoyal992@gmail.com"), "License", "License URL", java.util.Collections.emptyList());
	}
	
}
