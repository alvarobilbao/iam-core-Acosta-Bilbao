package fr.epita.iam.views;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * <h3>Description</h3>
 * <p>This class allows to manage the different views of the GUI, it's also responsible for 
 * resizing the windows as they change from window to window. </p>
 *
 * <h3>Usage</h3>
 * <p>This class is used as follows: </p>
 *   <pre><code> MainFrame instance =  MainFrame.getMainFrame();</code></pre>
 *
 * <h3>SonarLint warning Suppression</h3>
 * <p>It is not possible to reduce the depth for this JFrame class, hence, the warning is suppressed.
 * To suppress the warning the following code should be included before the MainFrame class.
 * @java.lang.SuppressWarnings("squid:MaximumInheritanceDepth").</p>
 * 
 *
 * @author Stéfano Acosta - Álvaro Bilbao
 *
 */
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
		setTitle("IamCore Java Project - Acosta - Bilbao");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 375);
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
			setBounds(100, 100, 400, 375);
			break;
		case CREATE_IDENTITY_VIEW:
			selectedView = new Create();
			setBounds(100, 100, 400, 375);
			break;
		case MANAGE_IDENTITY_VIEW:
			selectedView = new SearchDeleteUpdate();
			setBounds(100, 100, 700, 450);
			break;
		default: 
			selectedView = new Login();
			setBounds(100, 100, 400, 375);
			break;
		}
		setContentPane(selectedView);
		revalidate();	
	}

}
