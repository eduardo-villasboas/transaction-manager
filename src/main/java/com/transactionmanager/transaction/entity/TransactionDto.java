package com.transactionmanager.transaction.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.transactionmanager.transaction.usecase.OperationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe que representa uma transação
 * 
 * @author eduardo
 *
 */
@ApiModel
public final class TransactionDto {

	private UUID transactionId;

	@NotNull(message = "Error. account_id cannot be null")
	private UUID accountId;

	@NotNull(message = "Error. operation_type cannot be null")
	@ApiModelProperty(value = "Id da operação.1 -> COMPRA_A_VISTA, 2 -> COMPRA_PARCELADA,3 -> SAQUE, 4 -> PAGAMENTO", 
						allowableValues = "1,2,3,4")
	private OperationType operationType;

	@NotNull(message = "Error. ammount cannot be null")
	@ApiModelProperty(example = "1324.45")
	private BigDecimal ammount;

	private ZonedDateTime eventDate;

	@Override
	public String toString() {
		return "TransactionDto [transactionId=" + transactionId + ", accountId=" + accountId + ", operationType="
				+ operationType + ", ammount=" + ammount + ", eventDate=" + eventDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, ammount, eventDate, operationType, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TransactionDto other = (TransactionDto) obj;
		return Objects.equals(accountId, other.accountId) && Objects.equals(ammount, other.ammount)
				&& Objects.equals(eventDate, other.eventDate) && operationType == other.operationType
				&& Objects.equals(transactionId, other.transactionId);
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
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

	public ZonedDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(ZonedDateTime eventDate) {
		this.eventDate = eventDate;
	}

}
