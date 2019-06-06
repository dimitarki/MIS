package com.accounting.views.project;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonsFirmPanel extends JPanel {

	private JButton createFirm;
	private JButton searchFirm;
	public ButtonsFirmPanel() {
		super();
		createFirm = new JButton("Създаване на фирма");
		searchFirm = new JButton("Влизане с фирма");
		this.add( createFirm);
		this.add( searchFirm);
	}
	public JButton getCreateFirm() {
		return createFirm;
	}
	public JButton getSearchFirm() {
		return searchFirm;
	}
	
}
