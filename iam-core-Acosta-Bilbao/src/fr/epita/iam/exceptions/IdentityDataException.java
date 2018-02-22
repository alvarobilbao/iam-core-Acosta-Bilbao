package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.Identity;

/**
 * <h3>Description</h3>
 * <p>This class is the parent of all the specific dedicated Exception classes for Identity.
 * </p>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class IdentityDataException extends Exception{

	protected final Identity faultyIdentity;
	private static final long serialVersionUID = 1L;

	/**
	 * @param cause
	 * @param faultyIdentity
	 */
	public IdentityDataException(Exception cause, Identity faultyIdentity) {
		initCause(cause);
		this.faultyIdentity = faultyIdentity;
	}
	
}
