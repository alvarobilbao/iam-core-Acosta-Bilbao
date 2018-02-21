package fr.epita.utils.services.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import fr.epita.utils.logger.Logger;

public class ConfigurationService {

	private Properties properties;

	private static ConfigurationService instance;
	private static final Logger LOGGER = new Logger(ConfigurationService.class);

	private ConfigurationService(String filePathToConfiguration) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(filePathToConfiguration)));
		} catch (final IOException e) {
			LOGGER.error("There was an IOException while loading the configuration File");
		}
	}

	public static ConfigurationService getInstance() {
		if (instance == null) {
			instance = new ConfigurationService(System.getProperty("conf"));
		}
		return instance;
	}

	public String getConfigurationValue(String propertyKey) {
		return properties.getProperty(propertyKey);
	}
}
