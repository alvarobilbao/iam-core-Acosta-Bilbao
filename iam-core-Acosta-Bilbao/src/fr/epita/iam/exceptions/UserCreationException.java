package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.User;

/**
 * <h3>Description</h3>
 * <p>This class is the parent of all the specific dedicated Exception classes for User.
 * </p>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class UserCreationException extends UserDataException{
	private static final long serialVersionUID = 1L;

	/**
	 * @param e
	 * @param user
	 */
	public UserCreationException(Exception e, User user) {
		super(e, user);
	}

	@Override
	public String getMessage() { 
		return "A problem occurred while creating the Identity : " + faultyUser;
	}

}
