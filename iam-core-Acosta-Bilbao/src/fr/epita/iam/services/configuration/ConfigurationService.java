package fr.epita.iam.services.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationService {
	private Properties properties;

	private static ConfigurationService instance;

	private ConfigurationService(String filePathToConfiguration) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(filePathToConfiguration)));
		} catch (final IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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
