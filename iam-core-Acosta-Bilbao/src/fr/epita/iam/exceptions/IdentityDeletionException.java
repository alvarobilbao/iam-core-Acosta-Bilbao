/**
 * 
 */
package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.Identity;

/**
 * @author usuario
 *
 */
public class IdentityDeletionException extends IdentityDataException {
	public IdentityDeletionException(Exception cause, Identity faultyIdentity) {
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
		return "a problem occured while deleting the identity : " + faultyIdentity;
	}
}
