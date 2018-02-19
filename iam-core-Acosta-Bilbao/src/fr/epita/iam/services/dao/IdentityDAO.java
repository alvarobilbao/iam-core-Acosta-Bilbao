package fr.epita.iam.services.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.IdentityUpdateException;
import fr.epita.utils.services.configuration.ConfigurationService;
import fr.epita.iam.services.database.DBConnection;
import fr.epita.utils.logger.Logger;

public class IdentityDAO {

	private static final Logger LOGGER = new Logger(IdentityDAO.class);
	
	public void create(Identity identity) throws IdentityCreationException {

		LOGGER.info("Creating the identity: " + identity);
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
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
		//Delete with ID only
		try {
			connection = DBConnection.getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("DELETE FROM IDENTITIES where ID = ?");
            pstmt.setInt(1, identity.getId());
            pstmt.execute();
            pstmt.close();
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
			connection = DBConnection.getConnection();
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
		LOGGER.info("Update the identity: " + identity);
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
			// the PreparedStatement.RETURN_GENERATED_KEYS says that generated keys should be retrievable after execution
			final PreparedStatement pstmt = connection.
					prepareStatement("UPDATE IDENTITIES "
							+ "SET UID = ?, EMAIL = ?, DISPLAY_NAME = ? "
							+ "WHERE ID = ?");
			pstmt.setString(1, identity.getUid());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getDisplayName());
			pstmt.setInt(4, identity.getId());
			pstmt.execute();
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

	public Identity searchById(int id) throws IdentitySearchException {
		final List<Identity> results = new ArrayList<>();
		Connection connection = null;
		final Identity foundIdentity = new Identity();
		
		try {
			connection = DBConnection.getConnection();
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
