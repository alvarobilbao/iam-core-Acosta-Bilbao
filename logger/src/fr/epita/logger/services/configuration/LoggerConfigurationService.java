package fr.epita.logger.services.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoggerConfigurationService {

	private Properties properties;

	private static LoggerConfigurationService instance;

	private LoggerConfigurationService(String filePathToConfiguration) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(filePathToConfiguration)));
		} catch (final IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static LoggerConfigurationService getInstance() {
		if (instance == null) {
			instance = new LoggerConfigurationService(System.getProperty("conf"));
		}
		return instance;
	}

	public String getConfigurationValue(String propertyKey) {
		return properties.getProperty(propertyKey);
	}
}
