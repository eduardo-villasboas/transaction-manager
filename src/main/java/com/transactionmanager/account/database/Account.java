package com.transactionmanager.account.database;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.transactionmanager.account.entity.AccountDto;
/**
 * Entidade mapeia uma conta no banco de dados.
 * 
 * @author eduardo
 *
 */
@Entity
@Table(name = "account")
class Account {

	@Id
	@Column(name = "account_id")
	@NotNull(message = "account_id cannot be null")
	private UUID accountId;

	@Column(name = "document_number")
	@NotNull(message = "document_number cannot be null")
	private Long documentNumber;

	public Account() {
	}

	public Account(AccountDto accountDto) {
		accountId = accountDto.getAccountId();
		documentNumber = accountDto.getDocumentNumber();
	}

	@Override
	public String toString() {
		return "Accounty [accountId=" + accountId + ", documentNumber=" + documentNumber + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId);
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
		Account other = (Account) obj;
		return Objects.equals(accountId, other.accountId);
	}

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public Long getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

}
