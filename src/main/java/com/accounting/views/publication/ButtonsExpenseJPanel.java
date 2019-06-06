package com.accounting.views.publication;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonsExpenseJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton createExpense;
	private JButton searchExpense;

	public ButtonsExpenseJPanel() {
		super();
		createExpense = new JButton("Създаване на разход/приход");
		searchExpense = new JButton("Търси разходи/приходи");
		this.add(createExpense);
		this.add(searchExpense);
	}

	public JButton getCreateExpense() {
		return createExpense;
	}

	public JButton getSearchExpense() {
		return searchExpense;
	}
}
