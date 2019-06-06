package com.accounting.views.publication;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.accounting.controller.ChartController;
import com.accounting.controller.ExpenseException;
import com.accounting.controller.ExpensesController;
import com.accounting.model.firm.Expense;
import com.accounting.views.MainView;
import com.accounting.views.TableCommonGenerator;

public class ExpensesJPanel extends JPanel {
	private JScrollPane tablePane;
	private CreateExpenseJPanel createPublicationJPanel;
	private ButtonsExpenseJPanel buttonPanel;
	private SearchExpenseJPanel searchPanel;
	private ExpensesController expenseController;
	private JPanel graphicShow;
	public ExpensesJPanel() {
		super(new BorderLayout(10, 10));
		this.setBorder(new TitledBorder("Разходи/Приходи"));
		expenseController = ExpensesController.getInstance();
		tablePane = TableCommonGenerator.getConfiguredForExpensesPanel();
		tablePane.setVisible(false);
		graphicShow = new JPanel();
		graphicShow.setVisible(false);
		buttonPanel = new ButtonsExpenseJPanel();
		createPublicationJPanel = new CreateExpenseJPanel();
		createPublicationJPanel.setVisible(false);
		searchPanel = new SearchExpenseJPanel();
		searchPanel.setVisible(false);
		this.add(buttonPanel, BorderLayout.NORTH);
		createOpenPublicationCreationActionListener();
		createOpenSearchPublicationActionListener();
		createSavePublicationActionListener();
		createSearchPublicationActionListener();
		createDeletePublicationActionListener();
		createChartIncome();
		createChartEarn();
		createChartOutcome();
	}
	
	private void createChartIncome() {
		searchPanel.getShowGraphChart().addActionListener((ActionEvent e) -> {
		    	try {
		    		remove(graphicShow);
		    		graphicShow = new JPanel();
		            JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(ChartController.getInstance().createChartURLExpenses("Приход",searchPanel.getFirm().getSelectedItem().toString() ,searchPanel.getDateFrom().getDate(), searchPanel.getDateTo().getDate())))));
		            graphicShow.add(label);
		            add(graphicShow, BorderLayout.EAST);
		            graphicShow.setVisible(true);
			    	invalidate();
			    	validate();
			    	MainView.resizeFrame();
		    	} catch (Exception ex) {
		    		JOptionPane.showMessageDialog(null, "Грешка при графиката. Проверете връзката с интернет !");
		    	}
		    	
		});
	}
	
	private void createChartEarn() {
		searchPanel.getEarnGraphChart().addActionListener((ActionEvent e) -> {
		    	try {
		    		remove(graphicShow);
		    		graphicShow = new JPanel();
		            JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(ChartController.getInstance().createChartURLExpensesEnd(searchPanel.getFirm().getSelectedItem().toString() ,searchPanel.getDateFrom().getDate(), searchPanel.getDateTo().getDate())))));
		            graphicShow.add(label);
		            add(graphicShow, BorderLayout.EAST);
		            graphicShow.setVisible(true);
			    	invalidate();
			    	validate();
			    	MainView.resizeFrame();
		    	} catch (Exception ex) {
		    		JOptionPane.showMessageDialog(null, "Грешка при графиката. Проверете връзката с интернет !");
		    	}
		    	
		});
	}
	
	private void createChartOutcome() {
		searchPanel.getOutcomeGraphChart().addActionListener((ActionEvent e) -> {
		    	try {
		    		remove(graphicShow);
		    		graphicShow = new JPanel();
		            JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(ChartController.getInstance().createChartURLExpenses("Разход",searchPanel.getFirm().getSelectedItem().toString() ,searchPanel.getDateFrom().getDate(), searchPanel.getDateTo().getDate())))));
		            graphicShow.add(label);
		            add(graphicShow, BorderLayout.EAST);
		            graphicShow.setVisible(true);
			    	invalidate();
			    	validate();
			    	MainView.resizeFrame();
		    	} catch (Exception ex) {
		    		JOptionPane.showMessageDialog(null, "Грешка при графиката. Проверете връзката с интернет !");
		    	}
		    	
		});
	}

	private void createDeletePublicationActionListener() {
		searchPanel.getDeleteExpense().addActionListener((ActionEvent e) -> {
				tablePane.setVisible(true);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
				searchPanel.setVisible(true);
				createPublicationJPanel.setVisible(false);
				JTable table = (JTable) tablePane.getViewport().getView();
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Трябва да се селектирате разход/приход");
					return;
				}
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String title = (String) model.getValueAt(row, 0);
				expenseController.deleteExpense(Integer.valueOf(title));
				model.removeRow(row);
				renderTable(table);
				remove(createPublicationJPanel);
				add(tablePane, BorderLayout.CENTER);
				add(searchPanel, BorderLayout.WEST);
				invalidate();
				validate();
				MainView.resizeFrame();

		});
	}

	private void createSearchPublicationActionListener() {
		searchPanel.getSearchExpense().addActionListener((ActionEvent e) -> {
					tablePane.setVisible(true);
					searchPanel.setVisible(true);
			    	graphicShow.setVisible(false);
			    	remove(graphicShow);
					createPublicationJPanel.setVisible(false);
					JTable table = (JTable) tablePane.getViewport().getView();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setRowCount(0);
					List<Expense> expenses = expenseController.getExpensesBetweenDates(
							searchPanel.getDateFrom().getDate(), searchPanel.getDateTo().getDate(), searchPanel.getFirm().getSelectedItem().toString());
					for (Expense pub : expenses) {
						SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
						String date = format.format(pub.getDateOfExpense());
						model.addRow(new Object[] { String.valueOf(pub.getId()), pub.getType(), pub.getDescription(), 
								pub.getFirm().getFirmName(),pub.getCurrency(), String.valueOf(pub.getPrice()),date
								});
					}
					renderTable(table);
					remove(createPublicationJPanel);
					add(tablePane, BorderLayout.CENTER);
					add(searchPanel, BorderLayout.WEST);
					invalidate();
					validate();
					MainView.resizeFrame();
				
		});
	}


	private void createSavePublicationActionListener() {
		createPublicationJPanel.getSave().addActionListener((ActionEvent e) -> {
				try {
					expenseController.createExpense(createPublicationJPanel.getExpenseType().getSelectedItem().toString(), 
							createPublicationJPanel.getDescription().getText(),
							Integer.parseInt(createPublicationJPanel.getNumberPrice().getText()), 
							createPublicationJPanel.getCurrencyType().getText(), 
							createPublicationJPanel.getFirm().getSelectedItem().toString(), 
							createPublicationJPanel.getDate().getDate());
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Попълнете всички полета!!!");
					return;
				} catch ( ExpenseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(null, "Добавихте разход/приход успешно !");
				createPublicationJPanel.clear();
				invalidate();
				validate();
				MainView.resizeFrame();

		});
	}

	private void createOpenSearchPublicationActionListener() {
		buttonPanel.getSearchExpense().addActionListener((ActionEvent e) -> {
				tablePane.setVisible(true);
				searchPanel.setVisible(true);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
				createPublicationJPanel.setVisible(false);
				searchPanel.reload();
				remove(createPublicationJPanel);
				add(tablePane, BorderLayout.CENTER);
				add(searchPanel, BorderLayout.WEST);
				invalidate();
				validate();
				MainView.resizeFrame();
		});
	}

	private void createOpenPublicationCreationActionListener() {
		buttonPanel.getCreateExpense().addActionListener((ActionEvent e) -> {
				tablePane.setVisible(false);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
				searchPanel.setVisible(false);
				createPublicationJPanel.setVisible(true);
				createPublicationJPanel.reload();
				remove(tablePane);
				remove(searchPanel);
				add(createPublicationJPanel, BorderLayout.CENTER);
				invalidate();
				validate();
				MainView.resizeFrame();
				
		});
	}

	private void renderTable(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 200;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 100, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
}
