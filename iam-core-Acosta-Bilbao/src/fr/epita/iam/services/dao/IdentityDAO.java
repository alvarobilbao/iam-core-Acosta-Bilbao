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
import fr.epita.iam.exceptions.IdentityUpdateException;
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
			// the PreparedStatement.RETURN_GENERATED_KEYS says that generated keys should be retrievable after execution
			final PreparedStatement pstmt = connection.
					prepareStatement("INSERT INTO IDENTITIES(UID, EMAIL, DISPLAY_NAME) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, identity.getUid());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getDisplayName());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			//As we are only handling 1 insertion, if the insertion is made, it will return the auto generated id, so we will set it to the identity
			while (rs.next()) {
				// this sets the dbID 
				identity.setId(rs.getInt(1));
			}
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
					.prepareStatement("SELECT UID, EMAIL, DISPLAY_NAME, ID FROM IDENTITIES " 
			+ "WHERE (? IS NULL OR UID = ?) "+ "AND (? IS NULL OR EMAIL LIKE ?) " 
			+ "AND (? IS NULL OR DISPLAY_NAME LIKE ?)");

			pstmt.setString(1, criteria.getUid());
			pstmt.setString(2, criteria.getUid());
			pstmt.setString(3, criteria.getEmail());
			pstmt.setString(4, criteria.getEmail() + "%");
			pstmt.setString(5, criteria.getDisplayName());
			pstmt.setString(6, criteria.getDisplayName() + "%");
			final ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				final Identity currentIdentity = new Identity();
				// How to select the right index?
				currentIdentity.setDisplayName(rs.getString("DISPLAY_NAME"));
				currentIdentity.setEmail(rs.getString("EMAIL"));
				currentIdentity.setUid(rs.getString("UID"));
				currentIdentity.setId(rs.getInt("ID"));
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
	
	public void update(Identity identity) throws IdentityUpdateException {
		LOGGER.info("Creating the identity: " + identity);
		Connection connection = null;
		try {
			connection = getConnection();
			// the PreparedStatement.RETURN_GENERATED_KEYS says that generated keys should be retrievable after execution
			final PreparedStatement pstmt = connection.
					prepareStatement("INSERT INTO IDENTITIES(UID, EMAIL, DISPLAY_NAME) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, identity.getUid());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getDisplayName());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			//As we are only handling 1 insertion, if the insertion is made, it will return the auto generated id, so we will set it to the identity
			while (rs.next()) {
				// this sets the dbID 
				identity.setId(rs.getInt(1));
			}
			pstmt.close();
		} catch (final Exception e) {
			// TODO: handle exception
			LOGGER.error("error while creating the identity " + identity + "got that error " + e.getMessage());
			throw new IdentityUpdateException(e, identity);

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

	public Identity searchById(int id) throws IdentitySearchException {
		final List<Identity> results = new ArrayList<>();
		Connection connection = null;
		final Identity foundIdentity = new Identity();
		
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("SELECT ID, UID, EMAIL, DISPLAY_NAME FROM IDENTITIES " 
			+ "WHERE (? IS NULL OR ID = ?) ");

			pstmt.setInt(1, id);
			pstmt.setInt(2, id);
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				foundIdentity.setDisplayName(rs.getString("DISPLAY_NAME"));
				foundIdentity.setEmail(rs.getString("EMAIL"));
				foundIdentity.setUid(rs.getString("UID"));
				foundIdentity.setId(rs.getInt("ID"));
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing search", e);
			Identity faultyIdentity = new Identity();
			faultyIdentity.setId(id);
			throw new IdentitySearchException(e, faultyIdentity);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					// can do nothing here, except logging maybe?
				}
			}
		}

		return foundIdentity;

	}

}
