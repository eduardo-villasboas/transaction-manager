package com.transactionmanager.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuração para possibilitar o funcionamento da estratégia de nomeação
 * snake case com os testes de integração. Pois a configuração via yaml não
 * funciona para testes de integração.
 * 
 * @author eduardo
 *
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	private List<HttpMessageConverter<?>> converters;

	
	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * Configura uma lista de {@link HttpMessageConverter } com uma estratégia de
	 * nomeação SNAKE_CASE fazendo com que os nomes dos campos retornados para o
	 * chamador dá API sejam convertidos da seguinte forma: { idOfEntity: 1234 } ->
	 * { id_of_entity: 1234 }
	 * 
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);

		converters.add(converter);
		this.converters = List.copyOf(converters);
	}
	


	/**
	 * Retorna uma lista de {@link HttpMessageConverter } previamente configurada.
	 * 
	 * @return uma lista de {@link HttpMessageConverter }
	 */
	public List<HttpMessageConverter<?>> getConverters() {
		return converters;
	}
	
}
