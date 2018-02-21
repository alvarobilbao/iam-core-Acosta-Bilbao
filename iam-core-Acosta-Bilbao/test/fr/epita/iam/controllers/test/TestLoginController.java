package fr.epita.iam.controllers.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.epita.iam.controllers.LoginController;
import fr.epita.iam.datamodels.User;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.UserCreationException;
import fr.epita.iam.exceptions.UserDeletionException;
import fr.epita.iam.services.dao.UserDAO;

class TestLoginController {

	@Test
	void testAuthenticationSuccess() throws UserCreationException, IdentityDeletionException, UserDeletionException {
		//Given
		User testUser = new User();
		testUser.setPassword("testPass");
		testUser.setUsername("authTestUser");
		
		UserDAO userdao = new UserDAO();
		userdao.create(testUser);
		
		//when
		boolean loginSuccess = LoginController.authenticate(testUser.getUsername(), testUser.getPassword());
		
		//then
		assertTrue(loginSuccess);
		
		// remove the user so the test can be run again
		userdao.delete(testUser);
	}
	
	@Test
	void testAuthenticationFail() throws UserCreationException, IdentityDeletionException, UserDeletionException {
		//Given
		User testUser = new User();
		testUser.setPassword("testPass");
		testUser.setUsername("authTestUser");
		
		UserDAO userdao = new UserDAO();
		userdao.create(testUser);
		
		// remove the user so it is no longer a user
		userdao.delete(testUser);
		
		//when
		boolean loginSuccess = LoginController.authenticate(testUser.getUsername(), testUser.getPassword());
		
		//then
		assertTrue(!loginSuccess);
		
		
	}

}
