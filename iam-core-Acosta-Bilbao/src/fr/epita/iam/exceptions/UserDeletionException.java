package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.User;

public class UserDeletionException extends UserDataException{

	public UserDeletionException(Exception cause, User faultyUser) {
		super(cause, faultyUser);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String getMessage() {
		return "a problem occured while deleting the identity : " + faultyUser;
	}
	
}
