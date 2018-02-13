package fr.epita.iam.services.configuration.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import fr.epita.iam.services.configuration.ConfigurationService;

class TestConfigurationService {

	@Test
	void test() {
		// Given
		final File file = new File(System.getProperty("conf"));
		assertTrue("file exist", file.exists());
		
		// When
		final ConfigurationService configService = ConfigurationService.getInstance();
		final String dbHost = configService.getConfigurationValue("db.host");
		
		// Then
		assertEquals("jdbc:derby://localhost:1527/iam;create=true", dbHost);
	}

}
