package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.Identity;

public class IdentityUpdateException extends IdentityDataException{
	private static final long serialVersionUID = 1L;

	public IdentityUpdateException(Exception e, Identity identity) {
		super(e, identity);
	}

	@Override
	public String getMessage() { 
		return "A problem occurred while updating the Identity : " + faultyIdentity;
	}

}
