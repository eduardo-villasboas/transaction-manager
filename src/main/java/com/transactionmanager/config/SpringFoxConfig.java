package com.transactionmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Classe de configuração da documentação automática do swagger. 
 * 
 * @author eduardo
 *
 */
@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket api() {
		final Docket docket = new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false);
		final ApiSelectorBuilder apiSelectorBuidler = docket.select();
		final ApiSelectorBuilder apis = apiSelectorBuidler
				.apis(RequestHandlerSelectors.basePackage("com.transactionmanager"));
		final ApiSelectorBuilder paths = apis.paths(PathSelectors.any());
		return paths.build();
	}

}