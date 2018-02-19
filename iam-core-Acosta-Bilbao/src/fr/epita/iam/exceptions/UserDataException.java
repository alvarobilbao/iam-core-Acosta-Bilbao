package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.User;

public class UserDataException extends Exception{
	protected final User faultyUser;
	private static final long serialVersionUID = 1L;



	public UserDataException(Exception cause, User faultyUser) {
		initCause(cause);
		this.faultyUser = faultyUser;
	}
}
