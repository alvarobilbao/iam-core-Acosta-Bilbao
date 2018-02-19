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
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.UserCreationException;
import fr.epita.iam.exceptions.UserSearchException;
import fr.epita.iam.services.database.DBConnection;
import fr.epita.utils.logger.Logger;

public class UserDAO {

private static final Logger LOGGER = new Logger(IdentityDAO.class);
	
	public void create(User user) throws  UserCreationException, IdentityCreationException {

		LOGGER.info("Creating the User: " + user);
		Connection connection = null;
		IdentityDAO identityDao = new IdentityDAO();
		identityDao.create(user.getIdentity());
		try {
			connection = DBConnection.getConnection();
			// the PreparedStatement.RETURN_GENERATED_KEYS says that generated keys should be retrievable after execution
			final PreparedStatement pstmt = connection.
					prepareStatement("INSERT INTO USERS(USERNAME, PASSWORD, IDENTITY_ID) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getIdentity().getId());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			//As we are only handling 1 insertion, if the insertion is made, it will return the auto generated id, so we will set it to the identity
			while (rs.next()) {
				// this sets the dbID 
				user.setId(rs.getInt(1));
			}
			pstmt.close();
		} catch (final Exception e) {
			// TODO: handle exception
			LOGGER.error("error while creating the user " + user + "got that error " + e.getMessage());
			throw new UserCreationException(e, user);

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
	 * @throws IdentitySearchException 
	 * @throws SQLException
	 */
	public List<User> searchAll(User criteria) throws UserSearchException, IdentitySearchException {
		
		final List<User> results = new ArrayList<>();
		Connection connection = null;
		final IdentityDAO identityDao = new IdentityDAO();
		try {
			connection = DBConnection.getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("SELECT USERNAME, PASSWORD, USER_ID, IDENTITY_ID FROM USERS " 
			+ "WHERE (? IS NULL OR USERNAME LIKE ?) "+ "AND (? IS NULL OR PASSWORD LIKE ?)");

			pstmt.setString(1, criteria.getUsername());
			pstmt.setString(2, criteria.getUsername() + "%");
			pstmt.setString(3, criteria.getPassword());
			pstmt.setString(4, criteria.getPassword() + "%");
			final ResultSet rs = pstmt.executeQuery();
			
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
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while performing search", e);
			throw new UserSearchException(e, criteria);
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

	
}
