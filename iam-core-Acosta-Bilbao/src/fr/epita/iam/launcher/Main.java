package fr.epita.iam.launcher;

import java.awt.EventQueue;

import fr.epita.iam.views.MainFrame;

public class Main {

	public static void main(String[] args) {
		// TODO create the console menu or instantiate GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = MainFrame.getMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

}
