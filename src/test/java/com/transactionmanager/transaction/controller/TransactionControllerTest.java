package com.transactionmanager.transaction.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.transactionmanager.AbstractIntegrationTest;
import com.transactionmanager.MockMvcFacade;
import com.transactionmanager.time.Clock;
import com.transactionmanager.transaction.usecase.OperationType;

class TransactionControllerTest extends AbstractIntegrationTest {

	private static final UUID ACCOUNT_ID = UUID.fromString("f4c56de4-afec-4498-9c8a-0f1a35ad484c");

	private static final UUID NOT_FOUND_ACCOUNT_ID = UUID.fromString("9176b00f-399c-4105-80b3-5a23364022b4");

	@Autowired
	private TransactionController transactionController;
	
	@MockBean
	private Clock clock;

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private static final ZonedDateTime CURRENT_TIME = ZonedDateTime.now(ZoneOffset.UTC);

	@BeforeEach
	protected void setup() {
		super.setup();
		when(clock.getUtcZonedDateTime()).thenReturn(CURRENT_TIME);
	}
	
	@Test
	@Sql({ "/test/script/transactions/insert-account-information-to-post-test.sql" })
	void testWhenPostThenCreateANewTransactionCompra() throws Exception {
		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/transactions";
		final JSONObject newAccountRequest = new JSONObject();
		newAccountRequest.put("account_id", ACCOUNT_ID);
		newAccountRequest.put("operation_type", OperationType.COMPRA_A_VISTA.getOperationTypeId());
		newAccountRequest.put("ammount", "11323.45");

		final String findByIdUrl = mockMvcFacade.post(postUrl, newAccountRequest, status().isCreated(),
				jsonPath("$", startsWith("/transactions/")));

		findAndCheckNewTransaction(mockMvcFacade, findByIdUrl, 
				jsonPath("$.account_id", is(ACCOUNT_ID.toString())),
				jsonPath("$.operation_type", is((int) OperationType.COMPRA_A_VISTA.getOperationTypeId())),
				jsonPath("$.event_date", is(DATE_FORMAT.format(CURRENT_TIME))),
				jsonPath("$.ammount", is(-11323.45)));
	}

	@Test
	@Sql({ "/test/script/transactions/insert-account-information-to-post-test.sql" })
	void testWhenPostThenCreateANewTransactionOfCompraParcelada() throws Exception {
		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/transactions";
		final JSONObject newAccountRequest = new JSONObject();
		newAccountRequest.put("account_id", ACCOUNT_ID);
		newAccountRequest.put("operation_type", OperationType.COMPRA_PARCELADA.getOperationTypeId());
		newAccountRequest.put("ammount", "2500.00");

		final String findByIdUrl = mockMvcFacade.post(postUrl, newAccountRequest, status().isCreated(),
				jsonPath("$", startsWith("/transactions/")));

		findAndCheckNewTransaction(mockMvcFacade, findByIdUrl,
				jsonPath("$.account_id", is(ACCOUNT_ID.toString())),
				jsonPath("$.operation_type", is((int) OperationType.COMPRA_PARCELADA.getOperationTypeId())),
				jsonPath("$.event_date",is(DATE_FORMAT.format(CURRENT_TIME))),
				jsonPath("$.ammount", is(-2500.00)));

	}

	@Test
	@Sql({ "/test/script/transactions/insert-account-information-to-post-test.sql" })
	void testWhenPostThenCreateANewTransactionOfSaque() throws Exception {
		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/transactions";
		final JSONObject newAccountRequest = new JSONObject();
		newAccountRequest.put("account_id", ACCOUNT_ID);
		newAccountRequest.put("operation_type", OperationType.SAQUE.getOperationTypeId());
		newAccountRequest.put("ammount", "11000.00");

		final String findByIdUrl = mockMvcFacade.post(postUrl, newAccountRequest, status().isCreated(),
				jsonPath("$", startsWith("/transactions/")));

		findAndCheckNewTransaction(mockMvcFacade, findByIdUrl, 
				jsonPath("$.account_id", is(ACCOUNT_ID.toString())),
				jsonPath("$.operation_type", is((int) OperationType.SAQUE.getOperationTypeId())),
				jsonPath("$.event_date", is(DATE_FORMAT.format(CURRENT_TIME))),
				jsonPath("$.ammount", is(-11000.00)));

	}

	@Test
	@Sql({ "/test/script/transactions/insert-account-information-to-post-test.sql" })
	void testWhenPostThenCreateANewTransactionOfPagamento() throws Exception {
		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/transactions";
		final JSONObject newAccountRequest = new JSONObject();
		newAccountRequest.put("account_id", ACCOUNT_ID);
		newAccountRequest.put("operation_type", OperationType.PAGAMENTO.getOperationTypeId());
		newAccountRequest.put("ammount", "15467.31");

		final String findByIdUrl = mockMvcFacade.post(postUrl, newAccountRequest, status().isCreated(),
				jsonPath("$", startsWith("/transactions/")));

		findAndCheckNewTransaction(mockMvcFacade, findByIdUrl, 
				jsonPath("$.account_id", is(ACCOUNT_ID.toString())),
				jsonPath("$.operation_type", is((int) OperationType.PAGAMENTO.getOperationTypeId())),
				jsonPath("$.event_date", is(DATE_FORMAT.format(CURRENT_TIME))),
				jsonPath("$.ammount", is(15467.31)));

	}

	private void findAndCheckNewTransaction(final MockMvcFacade mockMvcFacade, final String findByIdUrl,
			ResultMatcher... expectations) throws Exception {
		final ResultActions resultActions = mockMvcFacade.get(findByIdUrl).andExpect(status().isOk());
		for (ResultMatcher resultMatcher : expectations) {
			resultActions.andExpect(resultMatcher);
		}

	}

	@Test
	void testWhenPostThenReturn400BecauseAccountDoesNotExists() throws Exception {
		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/transactions";
		final JSONObject newAccountRequest = new JSONObject();
		newAccountRequest.put("account_id", NOT_FOUND_ACCOUNT_ID);
		newAccountRequest.put("operation_type", OperationType.PAGAMENTO.getOperationTypeId());
		newAccountRequest.put("ammount", "600.00");

		mockMvcFacade.postWithResponse(postUrl, newAccountRequest).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error_message", is(String
						.format("Error acccount with id %s not found. Transaction wasn't created.", NOT_FOUND_ACCOUNT_ID))));
	}
	
	@Test 
	void testWhenPostAndNotInformValidTransactionDataThenReturn400() throws Exception {

		final MockMvcFacade mockMvcFacade = getMockMvcFacade();
		final String postUrl = "/transactions";
		final JSONObject newAccountRequest = new JSONObject();
		
		mockMvcFacade.postWithResponse(postUrl, newAccountRequest)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error_message", is("Error when validate data.")))
				.andExpect(jsonPath("$.error_details[0]", is("Error. account_id cannot be null")))
				.andExpect(jsonPath("$.error_details[2]", is("Error. operation_type cannot be null")))
				.andExpect(jsonPath("$.error_details[1]", is("Error. ammount cannot be null")));
		
	}


	@Override
	protected Object getController() {
		return transactionController;
	}

}
