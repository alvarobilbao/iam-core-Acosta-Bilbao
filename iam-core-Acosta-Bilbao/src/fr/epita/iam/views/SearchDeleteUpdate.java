package fr.epita.iam.views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.IdentityUpdateException;
import fr.epita.iam.services.dao.IdentityDAO;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import javax.swing.JTextPane;
import java.awt.SystemColor;

public class SearchDeleteUpdate extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUidSearch;
	private JTextField txtDisplayNameSearch;
	private JTextField txtEmailSearch;
	private JTextField txtUidUpdate;
	private JTextField txtDisplayNameUpdate;
	private JTextField txtEmailUpdate;
	
	

	/**
	 * Create the frame.
	 * @throws IdentitySearchException 
	 */
	public SearchDeleteUpdate(){
		setBounds(100, 100, 676, 366);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//create the model and add elements
     	final Identity id1 = new Identity();
     	final IdentityDAO dao = new IdentityDAO();
     	DefaultListModel<String> idList = new DefaultListModel<>();	
		final List<Identity> listModel = new ArrayList<>();
		
		
		JLabel lblUidSearch = new JLabel("UID");
		lblUidSearch.setBounds(15, 102, 48, 14);
		
		txtUidSearch = new JTextField();
		txtUidSearch.setBounds(15, 127, 115, 20);
		txtUidSearch.setMaximumSize(new Dimension(200000, 200000));
		txtUidSearch.setColumns(10);
		
		JLabel lblDisplayNameSearch = new JLabel("Display Name");
		lblDisplayNameSearch.setBounds(15, 158, 98, 14);
		
		txtDisplayNameSearch = new JTextField();
		txtDisplayNameSearch.setBounds(15, 183, 115, 20);
		txtDisplayNameSearch.setMaximumSize(new Dimension(200000, 200000));
		txtDisplayNameSearch.setColumns(10);
		
		txtEmailSearch = new JTextField();
		txtEmailSearch.setBounds(15, 238, 115, 20);
		txtEmailSearch.setMaximumSize(new Dimension(200000, 200000));
		txtEmailSearch.setColumns(10);
		
		JLabel lblEmailSearch = new JLabel("E-mail");
		lblEmailSearch.setBounds(15, 214, 106, 14);
			
		JButton btnSearchId = new JButton("Search");
		btnSearchId.setBounds(15, 291, 125, 23);
		
		btnSearchId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				idList.clear();
				txtUidUpdate.setEnabled(true);
                txtDisplayNameUpdate.setEnabled(true);
                txtEmailUpdate.setEnabled(true);
                
				if (txtUidSearch.getText().isEmpty()) {
					id1.setUid(null);
				} else {
					id1.setUid(txtUidSearch.getText());
				}
				if (txtDisplayNameSearch.getText().isEmpty()) {
					id1.setDisplayName(null);
				} else {
					id1.setDisplayName(txtDisplayNameSearch.getText());
				}
				if (txtEmailSearch.getText().isEmpty()) {
					id1.setEmail(null);
				} else {
					id1.setEmail(txtEmailSearch.getText());
				}
				
				try {
					listModel.addAll(dao.searchAll(id1));
				} catch (IdentitySearchException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Identity idAux;
				for (Iterator<Identity> iterator = listModel.iterator(); iterator.hasNext();) {
					idAux = (iterator.next());
					idList.addElement(idAux.getUid()+"       "+idAux.getDisplayName()+"       "+idAux.getEmail());
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(158, 43, 349, 206);
		
		JLabel lblUidUpdate = new JLabel("UID");
		lblUidUpdate.setBounds(534, 102, 48, 14);
		
		txtUidUpdate = new JTextField();
		txtUidUpdate.setEnabled(false);
		txtUidUpdate.setBounds(535, 127, 114, 20);
		txtUidUpdate.setMaximumSize(new Dimension(200000, 200000));
		txtUidUpdate.setColumns(10);
		
		JLabel lblDisplayNameUpdate = new JLabel("Display Name");
		lblDisplayNameUpdate.setBounds(534, 158, 98, 14);
		
		txtDisplayNameUpdate = new JTextField();
		txtDisplayNameUpdate.setEnabled(false);
		txtDisplayNameUpdate.setBounds(534, 183, 115, 20);
		txtDisplayNameUpdate.setMaximumSize(new Dimension(200000, 200000));
		txtDisplayNameUpdate.setColumns(10);
		
		JLabel lblEmailUpdate = new JLabel("E-mail");
		lblEmailUpdate.setBounds(534, 214, 115, 14);
		
		txtEmailUpdate = new JTextField();
		txtEmailUpdate.setEnabled(false);
		txtEmailUpdate.setBounds(534, 238, 115, 20);
		txtEmailUpdate.setMaximumSize(new Dimension(200000, 200000));
		txtEmailUpdate.setColumns(10);
		
		JList<String> lstSearch = new JList<>(idList);
		lstSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstSearch.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	if (!lstSearch.isSelectionEmpty()) {
                        txtUidUpdate.setText(listModel.get(lstSearch.getSelectedIndex()).getUid());
                        txtDisplayNameUpdate.setText(listModel.get(lstSearch.getSelectedIndex()).getDisplayName());
                        txtEmailUpdate.setText(listModel.get(lstSearch.getSelectedIndex()).getEmail());
    				}
                }
            }
        });
 
		scrollPane.setViewportView(lstSearch);
		this.setLayout(null);
		this.add(txtEmailSearch);
		this.add(txtUidSearch);
		this.add(btnSearchId);
		this.add(lblDisplayNameSearch);
		this.add(txtDisplayNameSearch);
		this.add(lblUidSearch);
		this.add(lblEmailSearch);
		this.add(scrollPane);

		this.add(lblEmailUpdate);
		this.add(txtEmailUpdate);
		this.add(lblUidUpdate);
		this.add(txtDisplayNameUpdate);
		this.add(lblDisplayNameUpdate);
		this.add(txtUidUpdate);
		
		JTextPane txtpnForSearchingAn = new JTextPane();
		txtpnForSearchingAn.setEditable(false);
		txtpnForSearchingAn.setBackground(SystemColor.menu);
		txtpnForSearchingAn.setText("For searching an identity, please fill the following fields:");
		txtpnForSearchingAn.setBounds(15, 43, 115, 48);
		this.add(txtpnForSearchingAn);
		
		JTextPane txtpnForUpdatingSearch = new JTextPane();
		txtpnForUpdatingSearch.setEditable(false);
		txtpnForUpdatingSearch.setText("To update, search an identity, then click on it on the list.");
		txtpnForUpdatingSearch.setBackground(SystemColor.menu);
		txtpnForUpdatingSearch.setBounds(534, 43, 115, 48);
		this.add(txtpnForUpdatingSearch);
		
		JButton btnDelete = new JButton("Delete");
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dao.delete(dao.searchById(listModel.get(lstSearch.getSelectedIndex()).getId()));
					idList.removeElement(idList.getElementAt(lstSearch.getSelectedIndex()));
				} catch (IdentityDeletionException | IdentitySearchException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (idList.isEmpty()) {
                    txtUidUpdate.setEnabled(false);
                    txtDisplayNameUpdate.setEnabled(false);
                    txtEmailUpdate.setEnabled(false);
				}
			}
		});
		btnDelete.setBounds(197, 291, 125, 23);
		this.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id1.setId(listModel.get(lstSearch.getSelectedIndex()).getId());
				
				if (idList.isEmpty()) {
                    txtUidUpdate.setEnabled(false);
                    txtDisplayNameUpdate.setEnabled(false);
                    txtEmailUpdate.setEnabled(false);

                    txtUidUpdate.setText(null);
                    txtDisplayNameUpdate.setText(null);
                    txtEmailUpdate.setText(null);
				}
				
				if (txtUidUpdate.getText().isEmpty()) {
					id1.setUid(null);
				} else {
					id1.setUid(txtUidUpdate.getText());
				}
				if (txtDisplayNameUpdate.getText().isEmpty()) {
					id1.setDisplayName(null);
				} else {
					id1.setDisplayName(txtDisplayNameUpdate.getText());
				}
				if (txtEmailUpdate.getText().isEmpty()) {
					id1.setEmail(null);
				} else {
					id1.setEmail(txtEmailUpdate.getText());
				}
				try {
					dao.update(id1);	
				} catch (IdentityUpdateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnSearchId.doClick();
			}
		});
		
		btnUpdate.setBounds(534, 291, 125, 23);
		this.add(btnUpdate);
		
		JTextPane txtpnToDeleteSearch = new JTextPane();
		txtpnToDeleteSearch.setText("To delete, search an identity, then click on it on the list, press Delete:");
		txtpnToDeleteSearch.setBackground(SystemColor.menu);
		txtpnToDeleteSearch.setBounds(158, 260, 349, 23);
		this.add(txtpnToDeleteSearch);
		
		JButton btnBackToCreate = new JButton("Back to create");
		btnBackToCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.getMainFrame().setViewTo(MainFrame.CREATE_IDENTITY_VIEW);
			}
		});
		btnBackToCreate.setBounds(367, 291, 125, 23);
		add(btnBackToCreate);
	}
}
