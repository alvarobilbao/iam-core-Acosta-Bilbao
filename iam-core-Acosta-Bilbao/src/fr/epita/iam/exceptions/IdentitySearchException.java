package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.Identity;;

public class IdentitySearchException extends IdentityDataException {

	public IdentitySearchException(Exception cause, Identity faultyIdentity) {
		super(cause, faultyIdentity);
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
		return "a problem occured while searching identities with that criteria : " + faultyIdentity;
	}
	
}
