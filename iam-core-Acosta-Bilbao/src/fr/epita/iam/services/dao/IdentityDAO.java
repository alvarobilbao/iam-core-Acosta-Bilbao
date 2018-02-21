package fr.epita.iam.services.dao;

import java.sql.Connection;
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
import fr.epita.iam.services.database.DBConnection;
import fr.epita.utils.logger.Logger;

public class IdentityDAO {

	private static final String CLOSING_THE_PREPARED_STATEMENT_ERROR = "There was an sql error while closing the prepared statement";
	private static final String CLOSING_THE_DB_CONNECTION_ERROR = "There was an sql error while closing the DB connection";
	private static final String SQL_CLOSING_RESULTSET_ERROR = "There was an sql error while closing the result set";
	private static final Logger LOGGER = new Logger(IdentityDAO.class);
	
	public void create(Identity identity) throws IdentityCreationException {

		LOGGER.info("Creating the identity: " + identity);
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DBConnection.getConnection();
			// the PreparedStatement.RETURN_GENERATED_KEYS says that generated keys should be retrievable after execution
			pstmt = connection.
					prepareStatement("INSERT INTO IDENTITIES(UID, EMAIL, DISPLAY_NAME) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, identity.getUid());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getDisplayName());
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			//As we are only handling 1 insertion, if the insertion is made, it will return the auto generated id, so we will set it to the identity
			while (rs.next()) {
				// this sets the dbID 
				identity.setId(rs.getInt(1));
			}
			
		} catch (final Exception e) {
			LOGGER.error("error while creating the identity " + identity + "got that error " + e.getMessage());
			throw new IdentityCreationException(e, identity);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					LOGGER.error(CLOSING_THE_DB_CONNECTION_ERROR);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (final SQLException e) {
					LOGGER.error(CLOSING_THE_PREPARED_STATEMENT_ERROR);
				}
			}
			if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						LOGGER.error(SQL_CLOSING_RESULTSET_ERROR);
					}
			}
		}
	}
	
	public void delete(Identity identity) throws IdentityDeletionException {
		LOGGER.info("Deleting the identity: " + identity);
		Connection connection = null;
		PreparedStatement pstmt = null;
		//Delete with ID only
		try {
			connection = DBConnection.getConnection();
			pstmt = connection
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
					LOGGER.error(CLOSING_THE_DB_CONNECTION_ERROR);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (final SQLException e) {
					LOGGER.error(CLOSING_THE_PREPARED_STATEMENT_ERROR);
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
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DBConnection.getConnection();
			pstmt = connection
					.prepareStatement("SELECT UID, EMAIL, DISPLAY_NAME, ID FROM IDENTITIES " 
			+ "WHERE (? IS NULL OR UID = ?) "+ "AND (? IS NULL OR EMAIL LIKE ?) " 
			+ "AND (? IS NULL OR DISPLAY_NAME LIKE ?)");

			pstmt.setString(1, criteria.getUid());
			pstmt.setString(2, criteria.getUid());
			pstmt.setString(3, criteria.getEmail());
			pstmt.setString(4, criteria.getEmail() + "%");
			pstmt.setString(5, criteria.getDisplayName());
			pstmt.setString(6, criteria.getDisplayName() + "%");
			rs = pstmt.executeQuery();
			
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
					LOGGER.error(CLOSING_THE_DB_CONNECTION_ERROR);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (final SQLException e) {
					LOGGER.error(CLOSING_THE_PREPARED_STATEMENT_ERROR);
				}
			}
			if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						LOGGER.error(SQL_CLOSING_RESULTSET_ERROR);
					}
			}
		}

		return results;
	}
	
	public void update(Identity identity) throws IdentityUpdateException {
		LOGGER.info("Update the identity: " + identity);
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DBConnection.getConnection();
			// the PreparedStatement.RETURN_GENERATED_KEYS says that generated keys should be retrievable after execution
			pstmt = connection.
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
			LOGGER.error("error while creating the identity " + identity + "got that error " + e.getMessage());
			throw new IdentityUpdateException(e, identity);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					LOGGER.error(CLOSING_THE_DB_CONNECTION_ERROR);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (final SQLException e) {
					LOGGER.error(CLOSING_THE_PREPARED_STATEMENT_ERROR);
				}
			}
		}
	}

	public Identity searchById(int id) throws IdentitySearchException {
		Connection connection = null;
		final Identity foundIdentity = new Identity();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DBConnection.getConnection();
			pstmt = connection
					.prepareStatement("SELECT ID, UID, EMAIL, DISPLAY_NAME FROM IDENTITIES " 
			+ "WHERE (? IS NULL OR ID = ?) ");

			pstmt.setInt(1, id);
			pstmt.setInt(2, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				foundIdentity.setDisplayName(rs.getString("DISPLAY_NAME"));
				foundIdentity.setEmail(rs.getString("EMAIL"));
				foundIdentity.setUid(rs.getString("UID"));
				foundIdentity.setId(rs.getInt("ID"));
			}
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
					LOGGER.error(CLOSING_THE_DB_CONNECTION_ERROR);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (final SQLException e) {
					LOGGER.error(CLOSING_THE_PREPARED_STATEMENT_ERROR);
				}
			}
			if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						LOGGER.error(SQL_CLOSING_RESULTSET_ERROR);
					}
			}
		}

		return foundIdentity;

	}

}
