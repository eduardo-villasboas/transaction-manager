package com.transactionmanager;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.transactionmanager.account.error.handler.ExcecptionsHandler;
import com.transactionmanager.config.WebConfig;

/**
 * Classe base abstrata que facilita a criação de um novo teste de integração mockMvc
 * 
 * @author eduardo
 *
 */
@SpringBootTest
@Transactional
public abstract class AbstractIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private ExcecptionsHandler exceptionsHandler;

	@Autowired
	private WebConfig webconfig;

	/**
	 * Configura o mockmvc. Caso a classe filha 
	 */
	@BeforeEach
	protected void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(getController()).setControllerAdvice(exceptionsHandler)
				.setMessageConverters(convertToArray(webconfig.getConverters())).build();
	}

	/**
	 * Retorna um object facade que encapsula o objeto mockMvc e fornece uma interface mais
	 * simples e amigável para o cliente.
	 * 
	 * @return um facade para o mockMvc
	 */
	protected MockMvcFacade getMockMvcFacade() {
		return new MockMvcFacade(mockMvc);
	}

	/**
	 * Metodo abstrado que deve retornar uma instância de um bean do tipo controller
	 * do spring.
	 * 
	 * @return Um controller para o qual serão realizadas as chamadas http.
	 */
	protected abstract Object getController();

	/**
	 * Convert uma lista de {@link HttpMessageConverter} para um array do mesmo tipo de forma safe type.
	 * 
	 * @param converters lista de {@link HttpMessageConverter} para ser convertida para um array.
	 * @return um array de {@link HttpMessageConverter}
	 */
	private static HttpMessageConverter<?>[] convertToArray(List<HttpMessageConverter<?>> converters) {
		final HttpMessageConverter<?>[] convertersAsArray = new HttpMessageConverter[converters.size()];
		return converters.toArray(convertersAsArray);
	}

}
