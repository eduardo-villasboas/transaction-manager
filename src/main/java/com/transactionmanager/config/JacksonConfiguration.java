package com.transactionmanager.config;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

@Configuration
class JacksonConfiguration {

	@Bean
	ObjectMapper objectMapper() {

		ObjectMapper objectMapper = new ObjectMapper();
		configureJavaTimeModuleAndTimeZone(objectMapper);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		return objectMapper;
	}

	private static void configureJavaTimeModuleAndTimeZone(ObjectMapper objectMapper) {
		final JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(ZonedDateTime.class,
				new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		
		objectMapper.registerModule(javaTimeModule);
	}

}
