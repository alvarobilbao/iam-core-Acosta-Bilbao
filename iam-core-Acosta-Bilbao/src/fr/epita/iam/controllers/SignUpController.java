package fr.epita.iam.controllers;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.datamodels.User;
import fr.epita.iam.services.dao.UserDAO;
import fr.epita.iam.views.MainFrame;
import fr.epita.iam.views.PopUp;

/**
 * <h3>Description</h3>
 * <p>Manages the control of the SignUp UI for user with identity creation and
 *  for password confirmation at user creation.
 * Uses an empty constructor.
 * </p>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class SignUpController {

	private SignUpController () {
		
	}
	
	/**
	 * @param username
	 * @param password
	 * @param uid
	 * @param displayName
	 * @param passConf
	 * @param email
	 */
	public static void onCreateUserBtnClick(String username, String password, String uid, String displayName,
			String passConf, String email) {
		
		if(!password.equals(passConf)) {
			PopUp.popUpMessage("The password and the confirm password fields have different values");
		}
		else {
			try {
				UserDAO userdao = new UserDAO();
				User user = new User();
				Identity userIdentity = new Identity();
				userIdentity.setDisplayName(displayName);
				userIdentity.setEmail(email);
				userIdentity.setUid(uid);
				user.setIdentity(userIdentity);
				user.setPassword(password);
				user.setUsername(username);
				
				userdao.create(user);
				
				MainFrame.getMainFrame().setViewTo(MainFrame.LOGIN_VIEW);
				// Show create success popup
				PopUp.popUpMessage("User created Succesfully");
			} catch (Exception e) {
				PopUp.popUpMessage("There was an error creating the User");
			}
		}
		
	}
	
}
