package com.transactionmanager.account.entity;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe que representa uma conta
 * 
 * @author eduardo
 *
 */
@ApiModel
public final class AccountDto {

	@ApiModelProperty(required = false)
	private UUID accountId;

	@NotNull(message = "Error. document_number cannot be null")
	@ApiModelProperty(example = "2809021124")
	private Long documentNumber;

	@Override
	public String toString() {
		return "AccountDto [accountId=" + accountId + ", documentNumber=" + documentNumber + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, documentNumber);
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
		AccountDto other = (AccountDto) obj;
		return Objects.equals(accountId, other.accountId) && Objects.equals(documentNumber, other.documentNumber);
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
