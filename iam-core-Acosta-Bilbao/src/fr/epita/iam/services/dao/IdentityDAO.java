package fr.epita.iam.services.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.services.configuration.ConfigurationService;
import fr.epita.logger.Logger;

public class IdentityDAO {

	private static final Logger LOGGER = new Logger(IdentityDAO.class);
	
	private static final String DB_HOST = "db.host";
	private static final String DB_PWD = "db.pwd";
	private static final String DB_USER = "db.user";
	
	public void create(Identity identity) throws IdentityCreationException {

		LOGGER.info("Creating the identity: " + identity);
		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("INSERT INTO IDENTITIES(UID, EMAIL, DISPLAY_NAME) VALUES (?, ?, ?)");
			pstmt.setString(1, identity.getUid());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getDisplayName());
			pstmt.execute();
			pstmt.close();
		} catch (final Exception e) {
			// TODO: handle exception
			LOGGER.error("error while creating the identity " + identity + "got that error " + e.getMessage());
			throw new IdentityCreationException(e, identity);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					// can do nothing here, except logging maybe?
				}
			}
		}
	}
	
	public void delete(Identity identity) throws IdentityDeletionException {
		LOGGER.info("Deleting the identity: " + identity);
		Connection connection = null;
		// TODO: Implement delete for the GUI behavior
		//Delete with UID only
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("DELETE FROM IDENTITIES where UID = ?");
            pstmt.setString(1, identity.getUid());
        } catch (final Exception e) {
        	LOGGER.error("error while deleting the identity: " + identity + "got the error: " + e.getMessage());
			throw new IdentityDeletionException(e, identity);
        } finally {
        	if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					// can do nothing here, except logging maybe?
				}
			}
        }
	}
	
	/**
	 * Read all the identities from the database
	 * @return
	 * @throws SQLException
	 */
	public List<Identity> searchAll(Identity criteria) throws IdentitySearchException {
		
		final List<Identity> results = new ArrayList<>();
		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("SELECT UID, EMAIL, DISPLAY_NAME FROM IDENTITIES " 
			+ "WHERE (? IS NULL OR UID = ?) "+ "AND (? IS NULL OR EMAIL LIKE ?) " 
			+ "AND (? IS NULL OR DISPLAY_NAME LIKE ?)");

			pstmt.setString(1, criteria.getDisplayName());
			pstmt.setString(2, criteria.getDisplayName() + "%");
			pstmt.setString(3, criteria.getEmail());
			pstmt.setString(4, criteria.getEmail() + "%");
			pstmt.setString(5, criteria.getUid());
			pstmt.setString(6, criteria.getUid());
			final ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				final Identity currentIdentity = new Identity();
				// How to select the right index?
				currentIdentity.setDisplayName(rs.getString("DISPLAY_NAME"));
				currentIdentity.setEmail(rs.getString("EMAIL"));
				currentIdentity.setUid(rs.getString("UID"));
				results.add(currentIdentity);
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing search", e);
			throw new IdentitySearchException(e, criteria);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					// can do nothing here, except logging maybe?
				}
			}
		}

		return results;
	}
	
	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		// TODO make this variable through configuration

		final ConfigurationService confService = ConfigurationService.getInstance();

		final String url = confService.getConfigurationValue(DB_HOST);
		final String password = confService.getConfigurationValue(DB_PWD);
		final String username = confService.getConfigurationValue(DB_USER);

		Class.forName("org.apache.derby.jdbc.ClientDriver");

		final Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}

	
}
