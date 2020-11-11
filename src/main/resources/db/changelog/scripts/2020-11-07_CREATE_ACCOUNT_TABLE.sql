--liquibase formatted sql
--changeset eduardo-villasboas:2020-11-07_01 author:eduardo-villasboas
CREATE TABLE account (
	account_id UUID NOT NULL,
	document_number BIGINT NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (account_id)
);
CREATE UNIQUE INDEX account_account_id_index ON account(account_id);
--rollback DROP index account_account_id_index;
--rollback DROP TABLE account;
