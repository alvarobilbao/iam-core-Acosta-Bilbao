package fr.epita.iam.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

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
		case LOGIN_VIEW:
			selectedView = new Login();
			break;
		case SIGNUP_VIEW:
			selectedView = new SignUp();
			break;
		case CREATE_IDENTITY_VIEW:
			selectedView = new Create();
			break;
		case MANAGE_IDENTITY_VIEW:
			selectedView = new SearchDeleteUpdate();
			break;
		}
		if(selectedView != null) {
			setContentPane(selectedView);
			revalidate();	
		}
		
	}

}
