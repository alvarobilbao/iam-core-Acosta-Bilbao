package fr.epita.iam.services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.datamodels.User;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.UserCreationException;
import fr.epita.iam.exceptions.UserDeletionException;
import fr.epita.iam.exceptions.UserSearchException;
import fr.epita.iam.services.database.DBConnection;
import fr.epita.utils.logger.Logger;

/**
 * <h3>Description</h3>
 * <p>This UserDAO class is used to manage the users persisted in the database 
 * (more concretely: Create, Search, Delete and Update operations).
 * UserDAO interacts with the DataBase and has four different public methods:</p>
 * <p>{@link #create(User) void create(Identity identity)}
 * </p>
 * <p>{@link #search(User) List &ltUser&gt search(User criteria)}
 * </p>
 * <p>{@link #delete(Identity) void delete(Identity identity)}
 * </p>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class UserDAO {

private static final Logger LOGGER = new Logger(IdentityDAO.class);
private static final String CLOSING_THE_PREPARED_STATEMENT_ERROR = "There was an sql error while closing the prepared statement";
private static final String CLOSING_THE_DB_CONNECTION_ERROR = "There was an sql error while closing the DB connection";
private static final String SQL_CLOSING_RESULTSET_ERROR = "There was an sql error while closing the result set";
	
	
	/**
	 * <h3>Description</h3>
	 * <p>Creates a user and persist it in a database
	 * partially set or completely set but not null. 
	 * </p>
	 * @param user
	 * @throws UserCreationException
	 * @throws IdentityDeletionException
	 */
	public void create(User user) throws  UserCreationException, IdentityDeletionException {
		LOGGER.info("Creating the User: " + user);
		Connection connection = null;
		IdentityDAO identityDao = new IdentityDAO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			identityDao.create(user.getIdentity());
			connection = DBConnection.getConnection();
			// the PreparedStatement.RETURN_GENERATED_KEYS says that generated keys should be retrievable after execution
			pstmt = connection.
					prepareStatement("INSERT INTO USERS(USERNAME, PASSWORD, IDENTITY_ID) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getIdentity().getId());
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			//As we are only handling 1 insertion, if the insertion is made, it will return the auto generated id, so we will set it to the identity
			while (rs.next()) {
				// this sets the dbID 
				user.setId(rs.getInt(1));
			}
			pstmt.close();
		} catch (final IdentityCreationException ice) {
			LOGGER.error("error while creating the user identity " + user + "got that error " + ice.getMessage());
			throw new UserCreationException(ice, user);
		}
		catch (final Exception e) {
			LOGGER.error("error while creating the user " + user + "got that error " + e.getMessage());
			LOGGER.info("deleting the identity from the unsuccesfully created user");
			identityDao.delete(user.getIdentity());
			throw new UserCreationException(e, user);

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
	
	/**
	 * <h3>Description</h3>
	 * <p>Read an user from the database
	 * </p>
	 * 
	 * @return
	 * @throws IdentitySearchException 
	 * @throws SQLException
	 */
	public List<User> search(User criteria) throws UserSearchException, IdentitySearchException {
		
		final List<User> results = new ArrayList<>();
		Connection connection = null;
		final IdentityDAO identityDao = new IdentityDAO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DBConnection.getConnection();
			pstmt = connection
					.prepareStatement("SELECT USERNAME, PASSWORD, USER_ID, IDENTITY_ID FROM USERS " 
			+ "WHERE USERNAME = ? AND PASSWORD = ?");

			pstmt.setString(1, criteria.getUsername());
			pstmt.setString(2, criteria.getPassword());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				final User currentUser = new User();
				// How to select the right index?
				currentUser.setUsername(rs.getString("USERNAME"));
				currentUser.setPassword(rs.getString("PASSWORD"));
				currentUser.setId(rs.getInt("USER_ID"));
				final Identity userIdentity = identityDao.searchById(rs.getInt("IDENTITY_ID"));
				currentUser.setIdentity(userIdentity);
				results.add(currentUser);
			}
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing search", e);
			throw new UserSearchException(e, criteria);
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

	/**
	 * <h3>Description</h3>
	 * <p>Deletes a user from a database by using the Id from the user in the DB.
	 * In order to be able to use the Id field user.getId(), a search should be
	 * done first to retrieve the Id index from the databases.
	 * </p>
	 * @param user User Class type
	 * @throws UserDeletionException
	 */
	public void delete(User user) throws UserDeletionException {
		LOGGER.info("Deleting the identity: " + user);
		Connection connection = null;
		PreparedStatement pstmt = null;
		//Delete with ID only
		try {
			connection = DBConnection.getConnection();
			pstmt = connection
					.prepareStatement("DELETE FROM USERS where USER_ID = ?");
            pstmt.setInt(1, user.getId());
            pstmt.execute();
        } catch (final Exception e) {
        	LOGGER.error("error while deleting the identity: " + user + "got the error: " + e.getMessage());
			throw new UserDeletionException(e, user);
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

	
}
