package fr.epita.iam.exceptions;

import fr.epita.iam.datamodels.Identity;

public class IdentityCreationException extends IdentityDataException{
	private static final long serialVersionUID = 1L;


	public IdentityCreationException(Exception e, Identity identity) {
		super(e, identity);
	}

	@Override
	public String getMessage() { 
		return "A problem occurred while creating that Identity : " + faultyIdentity;
	}
}
