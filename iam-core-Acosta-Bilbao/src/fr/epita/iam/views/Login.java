package fr.epita.iam.views;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import fr.epita.iam.controllers.LoginController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <h3>Description</h3>
 * <p>This JPanel class is used to validate Users or direct to a JPanel class 
 * to create a User and persist in the database. </p>
 *
 * <h3>Usage</h3>
 * <p>This class is used as follows: </p>
 *   <pre><code> JPanel instance = new Login();</code></pre>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public Login() {
		setBounds(100, 100, 650, 400);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(35, 76, 84, 14);
		this.add(lblUsername);
		
		usernameField = new JTextField();
		usernameField.setBounds(129, 73, 154, 20);
		this.add(usernameField);
		usernameField.setColumns(10);
		
		JButton signInButton = new JButton("Sign In");
		signInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				boolean isAuthenticated = LoginController.authenticate(username, password);
				if (isAuthenticated) {
					MainFrame main = MainFrame.getMainFrame();
					main.setViewTo(MainFrame.CREATE_IDENTITY_VIEW);
				}
			}
		});
		signInButton.setBounds(55, 218, 120, 23);
		this.add(signInButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MainFrame main = MainFrame.getMainFrame();
				main.setViewTo(MainFrame.SIGNUP_VIEW);
			}
		});
		registerButton.setBounds(225, 218, 120, 23);
		this.add(registerButton);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(35, 141, 84, 14);
		this.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(129, 138, 154, 20);
		this.add(passwordField);
	}
}
