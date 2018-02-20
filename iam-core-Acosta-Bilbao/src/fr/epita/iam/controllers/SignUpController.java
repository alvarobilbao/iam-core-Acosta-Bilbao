package fr.epita.iam.controllers;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.datamodels.User;
import fr.epita.iam.services.dao.UserDAO;
import fr.epita.iam.views.MainFrame;
import fr.epita.iam.views.PopUp;

public class SignUpController {

	private SignUpController () {
		
	}
	
	public static void onCreateUserBtnClick(String username, String password, String uid, String displayName,
			String passConf, String email) {
		
		if(!password.equals(passConf)) {
			//TODO show error popup
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
				//TODO handle create User exception
				
			}
		}
		
	}
	
}
