package fr.epita.iam.views;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static final int LOGIN_VIEW = 1;
	public static final int SIGNUP_VIEW = 2;
	public static final int CREATE_IDENTITY_VIEW = 3;
	public static final int MANAGE_IDENTITY_VIEW = 4;	
	
	private static MainFrame instance;
	/**
	 * Create the frame.
	 */
	private MainFrame() {
		//Title set
		setTitle("IamCore Java Project - Acosta - Bilbao");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new Login();
		setContentPane(contentPane);
	}
	
	public static MainFrame getMainFrame() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	
	public void setViewTo(int view) {
		JPanel selectedView = null;
		switch(view) {
		case SIGNUP_VIEW:
			selectedView = new SignUp();
			break;
		case CREATE_IDENTITY_VIEW:
			selectedView = new Create();
			break;
		case MANAGE_IDENTITY_VIEW:
			selectedView = new SearchDeleteUpdate();
			break;
		default: 
			selectedView = new Login();
			break;
		}
		setContentPane(selectedView);
		revalidate();	
	}

}
