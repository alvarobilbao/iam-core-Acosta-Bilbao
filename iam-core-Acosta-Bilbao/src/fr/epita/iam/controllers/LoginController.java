package fr.epita.iam.controllers;

import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodels.User;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.UserSearchException;
import fr.epita.iam.services.dao.UserDAO;

public class LoginController {
	
	private LoginController () {
		
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IdentitySearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			return isAuthenticated;
		}
	}

}
