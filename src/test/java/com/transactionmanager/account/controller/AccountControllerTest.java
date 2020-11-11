package com.transactionmanager.account.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.transactionmanager.AbstractIntegrationTest;
import com.transactionmanager.MockMvcFacade;

class AccountControllerTest extends AbstractIntegrationTest {

	private static final Long DOCUMENT_NUMBER = 4433123466L;
	@Autowired
	private AccountController accountController;

	@Test
	@Sql({ "/test/script/accounts/insert-account-information-to-get-test.sql" })
	void testIfGetReturnsAccountWithSpecifiedAccountId() throws Exception {
		
		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		
		final String findByIdUrl = "/accounts/30ecb17a-f7aa-4a1f-a0af-75bb453909ba";
		mockMvcFacade.get(findByIdUrl)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account_id", is("30ecb17a-f7aa-4a1f-a0af-75bb453909ba")))
				.andExpect(jsonPath("$.document_number", is(325568907)));

	}

	@Test
	void testWhenGetNotFoundEntityThenReturn404() throws Exception {

		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String findByIdUrl = "/accounts/d9c42614-0959-49a1-b71c-2e325282af20";
		mockMvcFacade.get(findByIdUrl).andExpect(status().isNotFound()).andExpect(
				jsonPath("$.error_message", is("Error. Account not found for id d9c42614-0959-49a1-b71c-2e325282af20")));

	}

	@Test
	void testWhenPostThenCreateANewAccount() throws Exception {
		
		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/accounts";
		final JSONObject newAccountRequest = new JSONObject();
		newAccountRequest.put("document_number", DOCUMENT_NUMBER);
		
		final String findByIdUrl = mockMvcFacade.post(postUrl, newAccountRequest, status().isCreated(),
				jsonPath("$", startsWith("/accounts/")));
		
		findAndCheckNewAccount(mockMvcFacade, findByIdUrl);
	}

	private void findAndCheckNewAccount(final MockMvcFacade mockMvcFacade, final String findByIdUrl) throws Exception {
		mockMvcFacade.get(findByIdUrl)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.document_number", is(DOCUMENT_NUMBER)));
	}
	
	@Test 
	void testWhenPostAndNotInformAnValidDocumentNumberThenReturns400() throws Exception {

		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/accounts";
		final JSONObject newAccountRequest = new JSONObject();
		
		mockMvcFacade.postWithResponse(postUrl, newAccountRequest)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error_message", is("Error when validate data.")))
				.andExpect(jsonPath("$.error_details[0]", is("Error. document_number cannot be null")));
		
	}

	@Override
	protected Object getController() {
		return accountController;
	}

}
