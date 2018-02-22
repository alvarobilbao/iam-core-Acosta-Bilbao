package fr.epita.utils.services.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import fr.epita.utils.logger.Logger;

/**
 * <h3>Description</h3>
 * <p>Used to get the configuration file for avoid hard-coding it inside the project.</p>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class ConfigurationService {

	private Properties properties;

	private static ConfigurationService instance;
	private static final Logger LOGGER = new Logger(ConfigurationService.class);

	/**
	 * @param filePathToConfiguration
	 */
	private ConfigurationService(String filePathToConfiguration) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(filePathToConfiguration)));
		} catch (final IOException e) {
			LOGGER.error("There was an IOException while loading the configuration File");
		}
	}

	/**
	 * @return instance
	 */
	public static ConfigurationService getInstance() {
		if (instance == null) {
			instance = new ConfigurationService(System.getProperty("conf"));
		}
		return instance;
	}

	/**
	 * @param propertyKey
	 * @return String configValue
	 */
	public String getConfigurationValue(String propertyKey) {
		return properties.getProperty(propertyKey);
	}
}
