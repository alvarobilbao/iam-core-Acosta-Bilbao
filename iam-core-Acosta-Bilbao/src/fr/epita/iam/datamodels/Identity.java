package fr.epita.iam.datamodels;

/**
 * <h3>Description</h3>
 * <p>This class allows to ...</p>
 *
 * <h3>Usage</h3>
 * <p>This class is used as follows: </p>
 *   <pre><code>${type_name} instance = new ${type_name}();</code></pre>
 *
 * 
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 *
 */
public class Identity {

	private String uid;
	private String email;
	private String displayName;
	private int id;
	
	
	/**
	 * <h5>Description</h5>
	 * Non-empty Constructor for the Class Identity, 3 Strings parameters must be given 
	 * or be declared as null, at least one parameter should be different than null otherwise
	 * the suggested Constructor for Identity is the empty Identity() Constructor.
	 * 
	 * @param uid Should be a String containing numbers only.
	 * @param email String variable, no restrictions.
	 * @param displayName String variable, accepts spaces, numbers and symbols.
	 */
	public Identity(String uid, String email, String displayName) {
		this.uid = uid;
		this.email = email;
		this.displayName = displayName;
	}
	
	/**
	 * <h5>Description</h5>
	 * Default empty constructor for Class Identity.
	 */
	public Identity() {
		
	}

	/**
	 * @return The String uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid , the String uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	/**
	 * @return The String email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email , the String email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return The String displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName , the String displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * @return The int id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id ,  the int id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [displayName=" + displayName + ", uid=" + uid + ", email=" + email + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (displayName == null ? 0 : displayName.hashCode());
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (uid == null ? 0 : uid.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Identity other = (Identity) obj;
		if (displayName == null) {
			if (other.displayName != null) {
				return false;
			}
		} else if (!displayName.equals(other.displayName)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (uid == null) {
			if (other.uid != null) {
				return false;
			}
		} else if (!uid.equals(other.uid)) {
			return false;
		}
		return true;
	}

	
}
