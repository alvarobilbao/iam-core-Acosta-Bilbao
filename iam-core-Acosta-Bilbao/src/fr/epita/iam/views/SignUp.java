package fr.epita.iam.views;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import fr.epita.iam.controllers.SignUpController;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <h3>Description</h3>
 * <p>This JPanel class is used to create Users with an identity and persist it in the database. </p>
 *
 * <h3>Usage</h3>
 * <p>This class is used as follows: </p>
 *   <pre><code> JPanel instance = new SignUp();</code></pre>
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 */
public class SignUp extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField dispNameField;
	private JTextField uidField;
	private JTextField emailField;

	/**
	 * Create the frame.
	 */
	public SignUp() {
		setBounds(100, 100, 650, 400);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(28, 20, 135, 14);
		this.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(28, 50, 135, 14);
		this.add(lblPassword);
		
		JLabel lblUid = new JLabel("UID");
		lblUid.setBounds(28, 140, 135, 14);
		this.add(lblUid);
		
		JLabel lblDisplayName = new JLabel("Display name");
		lblDisplayName.setBounds(28, 110, 135, 14);
		this.add(lblDisplayName);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(28, 170, 135, 14);
		this.add(lblEmail);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(28, 80, 135, 14);
		this.add(lblConfirmPassword);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				String passConf = new String(confirmPasswordField.getPassword());
				String uid = uidField.getText();
				String displayName = dispNameField.getText();
				String email = emailField.getText();
				SignUpController.onCreateUserBtnClick(username, password, uid, displayName, passConf, email);
			}
		});
		btnSignUp.setBounds(28, 224, 144, 23);
		this.add(btnSignUp);
		
		JButton btnLogin = new JButton("Back to Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainFrame mainframe = MainFrame.getMainFrame();
				mainframe.setViewTo(MainFrame.LOGIN_VIEW);
			}
		});
		btnLogin.setBounds(200, 224, 144, 23);
		this.add(btnLogin);
		
		usernameField = new JTextField();
		usernameField.setBounds(197, 20, 154, 20);
		this.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(197, 50, 154, 20);
		this.add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setColumns(10);
		confirmPasswordField.setBounds(197, 80, 154, 20);
		this.add(confirmPasswordField);
		
		dispNameField = new JTextField();
		dispNameField.setColumns(10);
		dispNameField.setBounds(197, 110, 154, 20);
		this.add(dispNameField);
		
		uidField = new JTextField();
		uidField.setColumns(10);
		uidField.setBounds(197, 140, 154, 20);
		this.add(uidField);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(197, 170, 154, 20);
		this.add(emailField);
	}
}
