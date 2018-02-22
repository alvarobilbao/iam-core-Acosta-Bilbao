package fr.epita.iam.controllers;

import java.util.List;

import fr.epita.iam.datamodels.User;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.UserSearchException;
import fr.epita.iam.services.dao.UserDAO;
import fr.epita.iam.views.PopUp;
import fr.epita.utils.logger.Logger;

/**
 * <h3>Description</h3>
 * <p>Manages the control of the Login UI and User validation.
 * </p>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class LoginController {
	private static final Logger LOGGER = new Logger(LoginController.class);
	private LoginController () {
		
	}

	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean authenticate(String username, String password) {
		boolean isAuthenticated = false;
		UserDAO userdao = new UserDAO();
		User criteria = new User();
		criteria.setPassword(password);
		criteria.setUsername(username);
		
		try {
			List<User> matchedUsers = userdao.search(criteria);
			if (matchedUsers.size() == 1) {
				isAuthenticated = true;
			}
		} catch (UserSearchException e) {
			LOGGER.error("There was an error while searching for the user");
			PopUp.popUpMessage("There was an error on the authentication process, please try again");
		} catch (IdentitySearchException e) {
			LOGGER.error("There was an error while searching for the user's Identity");
			PopUp.popUpMessage("There was an error on the authentication process, please try again");
		}	
		return isAuthenticated;
		
	}

}
