--liquibase formatted sql
--changeset eduardo-villasboas:2020-11-07_01 author:eduardo-villasboas
CREATE TABLE "transaction" (
	transaction_id UUID NOT NULL,
	account_id UUID NOT NULL,
	operation_type smallint NOT NULL,
	ammount decimal(15,2) NOT NULL,
	event_date timestamp NOT NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id), 
	CONSTRAINT transaction_fk_account_account_id FOREIGN KEY(account_id) REFERENCES account(account_id)
);
CREATE UNIQUE INDEX transaction_transaction_id_index on "transaction"(transaction_id);
--rollback DROP index transaction_transaction_id_index;
--rollback DROP TABLE `transaction`;
