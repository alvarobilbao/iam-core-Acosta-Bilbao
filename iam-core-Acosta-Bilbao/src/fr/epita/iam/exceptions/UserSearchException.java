package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.User;

public class UserSearchException extends UserDataException {
	public UserSearchException(Exception cause, User faultyUser) {
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
		return "a problem occured while searching users with that criteria : " + faultyUser;
	}

}
