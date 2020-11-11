package com.transactionmanager.account.controller;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
class AccountRequest {

	@NotNull(message = "Error. document_number cannot be null")
	@ApiModelProperty(example = "2809021124")
	private Long documentNumber;

	@Override
	public String toString() {
		return "AccountRequest [documentNumber=" + documentNumber + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(documentNumber);
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
		AccountRequest other = (AccountRequest) obj;
		return Objects.equals(documentNumber, other.documentNumber);
	}

	public Long getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

}
