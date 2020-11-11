package com.transactionmanager.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

/**
 * Configuração do liquibase
 * 
 * @author eduardo
 *
 */
@Configuration
class LiquibaseConfiguration {

	@Bean
	SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:db/changelog/changelog.xml");
		liquibase.setDataSource(dataSource);
		return liquibase;
	}
	
}
