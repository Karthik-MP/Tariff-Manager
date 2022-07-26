package com.virtusa.tariffmanagerrest.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContexts(){
		return Arrays.asList(SecurityContext.builder().securityReferences(this.securityReferences()).build());
	}
	
	private List<SecurityReference> securityReferences(){
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}
	 @Bean 
	 public Docket api() {
 
	 return new Docket(DocumentationType.SWAGGER_2) 
			.apiInfo(this.getInfo())
			.securityContexts(this.securityContexts()) 
			.securitySchemes(Arrays.asList(this.apiKeys())) 
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	  }

	private ApiInfo getInfo() {
		return new ApiInfo("TariffManager Application",
				"By Team 14", 
				"1.0", 
				"Terms of Service",
				 new Contact("Rahul","abc.com","abc@mail.com"), 
				"Licence of APIS",
				"API licencence URL",
				new ArrayList<>());
	}
	 
}
