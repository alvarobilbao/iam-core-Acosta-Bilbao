package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.User;

public class UserCreationException extends UserDataException{
	private static final long serialVersionUID = 1L;

	public UserCreationException(Exception e, User user) {
		super(e, user);
	}

	@Override
	public String getMessage() { 
		return "A problem occurred while creating the Identity : " + faultyUser;
	}

}
