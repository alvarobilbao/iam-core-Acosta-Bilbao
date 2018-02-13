package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.Identity;

public class IdentityDataException extends Exception{

	protected final Identity faultyIdentity;
	private static final long serialVersionUID = 1L;



	public IdentityDataException(Exception cause, Identity faultyIdentity) {
		initCause(cause);
		this.faultyIdentity = faultyIdentity;
	}
	
}
