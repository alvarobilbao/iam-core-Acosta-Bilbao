package fr.epita.iam.services.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.epita.utils.services.configuration.ConfigurationService;
import fr.epita.utils.logger.Logger;

public class DBConnection {

	private static final Logger LOGGER = new Logger(DBConnection.class);
	
	private static final String DB_HOST = "db.host";
	private static final String DB_PWD = "db.pwd";
	private static final String DB_USER = "db.user";
	
	//Making the constructor private so this class is not instantiable, because its hole purposse is to 
	//get a database connection
	private DBConnection() {
		
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		LOGGER.trace("Getting a DB connection");
		final ConfigurationService confService = ConfigurationService.getInstance();

		final String url = confService.getConfigurationValue(DB_HOST);
		final String password = confService.getConfigurationValue(DB_PWD);
		final String username = confService.getConfigurationValue(DB_USER);

		Class.forName("org.apache.derby.jdbc.ClientDriver");

		final Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
	
}
