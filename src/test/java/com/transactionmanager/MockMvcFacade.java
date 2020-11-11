package com.transactionmanager;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Classe de fachada que expoem uma interface mais simples para realização dos
 * testes mockMvc
 * 
 * @author eduardo
 *
 */
public final class MockMvcFacade {

	private final MockMvc mockMvc;

	public MockMvcFacade(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	/**
	 * 
	 * @param url para realização da requisição
	 * @return uma {@link ResultActions} com a qual se pode fazer asserts a fim de
	 *         verificar se o endpoint testado está funcionado da maneira experada
	 *         pelo teste.
	 * @throws Exception
	 */
	public ResultActions get(String url) throws Exception {
		return mockMvc.perform(configureHeaders(MockMvcRequestBuilders.get(url))).andDo(print());
	}

	/**
	 * 
	 * @param mockHttpServletRequestBuilder um builder de http mock para ser
	 *                                      configurado com os headers
	 * 
	 * @return {@link MockHttpServletRequestBuilder} um builder de http mock
	 *         configurado com os headers.
	 * 
	 */
	private MockHttpServletRequestBuilder configureHeaders(
			final MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
		return mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	}

	/**
	 * Realiza uma request post para criação de um novo recurso e extrai o resultado
	 * da request que sempre deve ser uma string com a url do recurso criado. Caso o
	 * parametro assertions for passado o resultado é verificado antes de ser
	 * executado
	 * 
	 * @param postUrl        url para criação do novo recurso
	 * @param requestBody    request body para criação do novo recurso.
	 * @param resultMatchers parametro pode ser passado opcionalmente para fazer
	 *                       verificações a respeito do resultado do post.
	 * @return uma url para o recurso recem criado.
	 * @throws Exception
	 */
	public String post(String postUrl, JSONObject requestBody, ResultMatcher... resultMatchers) throws Exception {

		final ResultActions resultActions = postWithResponse(postUrl, requestBody);
		
		for (ResultMatcher r : resultMatchers) {
			resultActions.andExpect(r);
		}

		return extractResponse(resultActions.andReturn());
	}

	/**
	 * 
	 * @param postUrl     url para criação do novo recurso
	 * @param requestBody request body para criação do novo recurso
	 * @return uma {@link ResultActions} com a qual se pode fazer asserts a fim de
	 *         verificar se o endpoint testado está funcionado da maneira experada
	 *         pelo teste.
	 */
	public ResultActions postWithResponse(String postUrl, JSONObject requestBody) throws Exception {

		final MockHttpServletRequestBuilder configuredWithHeaders = configureHeaders(
				MockMvcRequestBuilders.post(postUrl));
		appedRequestBody(configuredWithHeaders, requestBody);
		return mockMvc.perform(configuredWithHeaders).andDo(print());

	}

	/**
	 * 
	 * @param configuredWithHeaders http mock request builder previamente
	 *                              configurado com os headers
	 * @param requestBody           o body para criação do novo recurso.
	 * @return uma instancia do mesmo http mock request builder configurada com o
	 *         body para criação do novo recurso.
	 */
	private MockHttpServletRequestBuilder appedRequestBody(MockHttpServletRequestBuilder configuredWithHeaders,
			JSONObject requestBody) {
		return configuredWithHeaders.content(requestBody.toString());
	}

	/**
	 * Extrai o resultado do MvcResult em forma de uma string que representa a url de busca.
	 * @param mockMvcResult resultado da request de post
	 * @return a resposta como uma string
	 * @throws UnsupportedEncodingException
	 */
	private String extractResponse(final MvcResult mockMvcResult) throws UnsupportedEncodingException {
		final JSONObject jsonObject = new JSONObject(mockMvcResult.getResponse().getContentAsString());
		return jsonObject.getString("resource");
	}

}
