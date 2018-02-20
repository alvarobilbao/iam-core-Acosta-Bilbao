package fr.epita.iam.services.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.datamodels.User;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.UserCreationException;
import fr.epita.iam.exceptions.UserDeletionException;
import fr.epita.iam.exceptions.UserSearchException;
import fr.epita.iam.services.dao.IdentityDAO;
import fr.epita.iam.services.dao.UserDAO;

class TestUserDAO {

	@Test
	void testCreateAndSearch() throws UserCreationException, IdentityCreationException, IdentitySearchException, UserSearchException, UserDeletionException {
		//Given
		final User user = new User();
		user.setPassword("test");
		// As the username is defined as unique in the database, change this value each time you run the test
		// to something which is not in the USERS table
		user.setUsername("userTest1");
		final Identity userIdentity = new Identity("123", "user@test.com", "userTest");
		user.setIdentity(userIdentity);
		
		final UserDAO userDao = new UserDAO();
		final IdentityDAO identityDao = new IdentityDAO();
		//When 
		userDao.create(user);
		
		//then
		final Identity resultIdentity = identityDao.searchById((userIdentity.getId()));
		assertTrue("identity for user was created " + userIdentity,userIdentity.equals(resultIdentity));
		
		final List<User> resultList = userDao.search(user);
		assertTrue("User found " + user, resultList.contains(user));
		
		//delete the user so this test can keep working

		userDao.delete(user);

	}

}
