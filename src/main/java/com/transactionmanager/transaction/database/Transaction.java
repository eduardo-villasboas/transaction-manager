package com.transactionmanager.transaction.database;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entidade mapeia uma transação no banco de dados.
 * 
 * @author eduardo
 *
 */
@Entity
@Table(name = "transaction")
class Transaction {

	@Id
	@Column(name = "transaction_id")
	@NotNull(message = "transaction_id cannot be null")
	private UUID transactionId;

	@Column(name = "account_id")
	@NotNull(message = "account_id cannot be null")
	private UUID accountId;
	
	@Column(name = "operation_type")
	@NotNull(message = "operation_type cannot be null")
	private short operationType;
	
	@Column(name = "ammount")
	@NotNull(message = "ammount cannot be null")
	private BigDecimal ammount;

	@Column(name = "event_date")
	@NotNull(message = "eventDate cannot be null")
	private ZonedDateTime eventDate;

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountId=" + accountId + ", operationType="
				+ operationType + ", ammount=" + ammount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(transactionId);
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
		Transaction other = (Transaction) obj;
		return Objects.equals(transactionId, other.transactionId);
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

	public short getOperationType() {
		return operationType;
	}

	public void setOperationType(short operationType) {
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
