package fr.epita.iam.services.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.epita.utils.services.configuration.ConfigurationService;
import fr.epita.iam.datamodels.Identity;
import fr.epita.utils.logger.Logger;

/**
 * <h3>Description</h3>
 * <p>This class is used to generate a connection with the Database, with the method:</p>
 * <p>{@link #getConnection() Connection getConnection()}
 * </p>
 *
 *
 * @author St�fano Acosta - �lvaro Bilbao
 */
public class DBConnection {

	private static final Logger LOGGER = new Logger(DBConnection.class);
	
	private static final String DB_HOST = "db.host";
	private static final String DB_PWD = "db.pwd";
	private static final String DB_USER = "db.user";
	
	//Making the constructor private so this class is not instantiable, because its hole purposse is to 
	//get a database connection
	private DBConnection() {
		
	}
	
	/**
	 * @return A connection to the Url
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		LOGGER.trace("Getting a DB connection");
		final ConfigurationService confService = ConfigurationService.getInstance();

		final String url = confService.getConfigurationValue(DB_HOST);
		final String password = confService.getConfigurationValue(DB_PWD);
		final String username = confService.getConfigurationValue(DB_USER);

		Class.forName("org.apache.derby.jdbc.ClientDriver");

		return DriverManager.getConnection(url, username, password);
	}
	
}
