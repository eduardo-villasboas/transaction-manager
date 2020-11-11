package com.transactionmanager.transaction.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.transactionmanager.transaction.usecase.OperationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
class TransactionRequest {

	@NotNull(message = "Error. account_id cannot be null")
	private UUID accountId;

	@NotNull(message = "Error. operation_type cannot be null")
	@ApiModelProperty(value = "Id da operação.1 -> COMPRA_A_VISTA, 2 -> COMPRA_PARCELADA,3 -> SAQUE, 4 -> PAGAMENTO", allowableValues = "1,2,3,4")
	private OperationType operationType;

	@NotNull(message = "Error. ammount cannot be null")
	@ApiModelProperty(example = "1324.45")
	private BigDecimal ammount;

	@Override
	public String toString() {
		return "TransactionRequest [accountId=" + accountId + ", operationType=" + operationType + ", ammount="
				+ ammount + "]";
	}

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}

}
