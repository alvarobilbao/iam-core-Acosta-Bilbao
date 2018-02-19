package fr.epita.iam.datamodels;

public class User {

	private String username;
	private String password;
	private int Id;
	private Identity identity;
	
	public User() {
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Identity getIdentity() {
		return identity;
	}
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	// TODO check that all cases are handled, add conditions for 
	//the remaining cases
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) return true;
		
		if(!(obj instanceof User) || obj == null) return false;
		
		User other = (User) obj;
		
		if(other.Id != this.Id) return false;
		
		if(other.username != null && other.password != null && other.identity != null) {
				return  other.username.equals(this.username) && 
						other.password.equals(this.password) && 
						other.identity.equals(this.identity);
		} else if ((other.username == null && this.username == null) && 
					other.password != null && other.identity != null) {
			return other.password.equals(this.password) && 
					other.identity.equals(this.identity);
		} else if ((other.password == null && this.password == null) && 
				other.username != null && other.identity != null) {
			return other.username.equals(this.username) && 
					other.identity.equals(this.identity);
		} else if ((other.identity == null && this.identity == null) && 
				other.username != null && other.password != null) {
			return other.username.equals(this.username) && 
					other.password.equals(this.password);
		} else if ((other.username == null && this.username == null) && 
				(other.password == null && this.password == null) &&
				other.identity != null) {
			return other.identity.equals(this.identity);
		} else if ((other.username == null && this.username == null) && 
				(other.identity == null && this.identity == null) &&
				other.password != null) {
			return other.password.equals(this.password);
		} else if ((other.identity == null && this.identity == null) && 
				(other.password == null && this.password == null) &&
				other.username != null) {
			return other.username.equals(this.username);
		} else if ((other.username == null && this.username == null) && 
				(other.password == null && this.password == null) &&
				(other.identity == null && this.identity == null) ) {
			return true;
		}
		
		return false;
		
	}
	
}
