package com.accounting.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.accounting.views.project.FirmJPanel;
import com.accounting.views.publication.ExpensesJPanel;

public class MainView {

	private static JFrame frame;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel ( "com.alee.laf.WebLookAndFeel" );
		EventQueue.invokeLater(new Runnable() {
			public void run() {
	             	frame = new JFrame("Nested Layout Example");
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	                
	                JTabbedPane jtp = new JTabbedPane();
	                frame.getContentPane().add(jtp);
	                ExpensesJPanel jp2 = new ExpensesJPanel();
	                JPanel jp1 = new FirmJPanel(jp2, jtp);
	                

	                jtp.addTab("Влизане в системата", jp1);
	                //jtp.addTab("Разходи/Приходи", jp2);
	        		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	        		frame.setVisible(true);

	                frame.setLocationRelativeTo(null);
	                
	                try {
	                    // 1.6+
	                    frame.setLocationByPlatform(true);
	                    frame.setMinimumSize(frame.getSize());
	                } catch(Throwable ignoreAndContinue) {
	                }

	                frame.setVisible(true);
	            }
		});
	}
	
	public static void resizeFrame() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
        frame.setMinimumSize(frame.getSize());
        frame.invalidate();
        frame.validate();
	}
}
