package fr.epita.iam.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.services.dao.IdentityDAO;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Create extends JFrame {

	private JPanel contentPane;
	private JTextField txtUidCreate;
	private JTextField txtDisplayNameCreate;
	private JTextField txtEmailCreate;
	private JLabel lblUid;
	private JLabel lblDisplayName;
	private JLabel lblEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Create frame = new Create();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Create() {
		setResizable(false);
		setTitle("Identity Creation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 285, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		@SuppressWarnings("unused")
		final Identity id1 = new Identity();
		@SuppressWarnings("unused")
		final IdentityDAO dao = new IdentityDAO();
		
		txtUidCreate = new JTextField();
		txtUidCreate.setBounds(66, 36, 141, 20);
		txtUidCreate.setColumns(10);
		
		txtDisplayNameCreate = new JTextField();
		txtDisplayNameCreate.setBounds(66, 94, 141, 20);
		txtDisplayNameCreate.setColumns(10);
		
		txtEmailCreate = new JTextField();
		txtEmailCreate.setBounds(66, 150, 141, 20);
		txtEmailCreate.setColumns(10);
		
		lblUid = new JLabel("UID");
		lblUid.setBounds(66, 11, 86, 14);
		
		lblDisplayName = new JLabel("Display Name");
		lblDisplayName.setBounds(66, 69, 86, 14);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(66, 125, 86, 14);
		contentPane.setLayout(null);
		contentPane.add(txtUidCreate);
		contentPane.add(lblUid);
		contentPane.add(txtDisplayNameCreate);
		contentPane.add(lblDisplayName);
		contentPane.add(txtEmailCreate);
		contentPane.add(lblEmail);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String messageString = "";
				if (txtUidCreate.getText().isEmpty()) {
					id1.setUid(null);
				} else {
					id1.setUid(txtUidCreate.getText());
					messageString=messageString+id1.getUid();
				}
				if (txtDisplayNameCreate.getText().isEmpty()) {
					id1.setDisplayName(null);
					messageString=messageString+"  ";
				} else {
					id1.setDisplayName(txtDisplayNameCreate.getText());
					messageString=messageString+"   "+id1.getDisplayName();
				}
				if (txtEmailCreate.getText().isEmpty()) {
					id1.setEmail(null);
				} else {
					id1.setEmail(txtEmailCreate.getText());
					messageString=messageString+"   "+id1.getEmail();
				}
				try {
					dao.create(id1);	
				} catch (IdentityCreationException e1) {
					// TODO Auto-generated catch block
					
					e1.printStackTrace();
				}			
				txtUidCreate.setText(null);
				txtDisplayNameCreate.setText(null);
				txtEmailCreate.setText(null);
				
				PopUp.popUpMessage("Identity created:  "+ messageString);
			}
		});
		btnCreate.setBounds(78, 203, 115, 23);
		contentPane.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(78, 237, 115, 23);
		contentPane.add(btnCancel);
	}
}
