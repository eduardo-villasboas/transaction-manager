package com.transactionmanager.commons.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class ResourceTest {

	@Test
	void testIfResourceCreateCorrectUrl() {
		final UUID idOfResource = UUID.randomUUID();

		assertThat(new Resource("/url-of-resources/", idOfResource).getResource()).as("Invalid resource")
				.isEqualTo(String.format("/url-of-resources/%s", idOfResource));
	}

}
