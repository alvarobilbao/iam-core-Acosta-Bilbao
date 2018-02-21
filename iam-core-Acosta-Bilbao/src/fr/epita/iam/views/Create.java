package fr.epita.iam.views;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.services.dao.IdentityDAO;
import fr.epita.utils.logger.Logger;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Create extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUidCreate;
	private JTextField txtDisplayNameCreate;
	private JTextField txtEmailCreate;
	private JLabel lblUid;
	private JLabel lblDisplayName;
	private JLabel lblEmail;
	
	private static final Logger LOGGER = new Logger(Create.class);

	/**
	 * Create the frame.
	 */
	public Create() {
		setBounds(100, 100, 285, 330);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		@SuppressWarnings("unused")
		final Identity id1 = new Identity();
		@SuppressWarnings("unused")
		final IdentityDAO dao = new IdentityDAO();
		
		txtUidCreate = new JTextField();
		txtUidCreate.setBounds(66, 66, 141, 20);
		txtUidCreate.setColumns(10);
		
		txtDisplayNameCreate = new JTextField();
		txtDisplayNameCreate.setBounds(66, 124, 141, 20);
		txtDisplayNameCreate.setColumns(10);
		
		txtEmailCreate = new JTextField();
		txtEmailCreate.setBounds(66, 180, 141, 20);
		txtEmailCreate.setColumns(10);
		
		lblUid = new JLabel("UID");
		lblUid.setBounds(66, 41, 86, 14);
		
		lblDisplayName = new JLabel("Display Name");
		lblDisplayName.setBounds(66, 99, 86, 14);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(66, 155, 86, 14);
		this.setLayout(null);
		this.add(txtUidCreate);
		this.add(lblUid);
		this.add(txtDisplayNameCreate);
		this.add(lblDisplayName);
		this.add(txtEmailCreate);
		this.add(lblEmail);
		
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
					LOGGER.error("There was an error while creating the Identity");
					PopUp.popUpMessage("There was an error while trying to create the Identity, Please try again");

				}			
				txtUidCreate.setText(null);
				txtDisplayNameCreate.setText(null);
				txtEmailCreate.setText(null);
				
				PopUp.popUpMessage("Identity created:  "+ messageString);
			}
		});
		btnCreate.setBounds(65, 233, 144, 23);
		this.add(btnCreate);
		
		JButton btnManage = new JButton("Manage Identities");
		btnManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getMainFrame().setViewTo(MainFrame.MANAGE_IDENTITY_VIEW);
			}
		});
		btnManage.setBounds(65, 267, 144, 23);
		this.add(btnManage);
		
		JLabel lblCreateNewIdentity = new JLabel("CREATE NEW IDENTITY");
		lblCreateNewIdentity.setBounds(10, 11, 197, 14);
		add(lblCreateNewIdentity);
	}
}
