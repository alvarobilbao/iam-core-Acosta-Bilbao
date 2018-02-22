package fr.epita.iam.views;

import javax.swing.JOptionPane;

/**
 * <h3>Description</h3>
 * <p>This class is used to generate customized PopUps messages as needed.</p>
 *
 * <h3>Usage</h3>
 * <p>This class is used as follows: </p>
 *   <pre><code> PopUp.popUpMessage("Some text or instead a String variable");</code></pre>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class PopUp {
	

	private PopUp() {
		
	}
	
	/**
	 * @param infoMessage infoMessage can be some text or instead a String variable.
	 */
	public static void popUpMessage(String infoMessage){
	        JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
