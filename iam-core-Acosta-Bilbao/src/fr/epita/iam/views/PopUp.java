package fr.epita.iam.views;

import javax.swing.JOptionPane;

public class PopUp {
	

	private PopUp() {
		
	}
	
	/**
	 * @param infoMessage
	 */
	public static void popUpMessage(String infoMessage){
	        JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
