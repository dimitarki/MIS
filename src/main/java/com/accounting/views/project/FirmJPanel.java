package com.accounting.views.project;

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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.accounting.controller.ChartController;
import com.accounting.controller.FirmController;
import com.accounting.controller.FirmException;
import com.accounting.model.firm.Firm;
import com.accounting.views.AuthorsJPanel;
import com.accounting.views.MainView;
import com.accounting.views.TableCommonGenerator;
import com.accounting.views.publication.ExpensesJPanel;

public class FirmJPanel extends JPanel{

	private JScrollPane tablePane;
	private CreateFirmPanel createFirmPanel;
	private ButtonsFirmPanel buttonPanel;
	private SearchFirmPanel searchPanel;
	private AuthorsJPanel authorsPanel;
	private FirmController firmCntroller;
	private LoginFirmPanel loginFirmPanel;
	private JPanel graphicShow;
	private ExpensesJPanel panel;
	private JTabbedPane pane;
	
	public FirmJPanel(ExpensesJPanel panel, JTabbedPane pane) {
		super(new BorderLayout(10,10));
		this.panel = panel;
		this.pane = pane;
		this.setBorder( new TitledBorder("Фирми"));
		graphicShow = new JPanel();
		graphicShow.setVisible(false);
		firmCntroller = FirmController.getInstance();
		tablePane = TableCommonGenerator.getConfiguredForFirmPanel();
		tablePane.setVisible(false);
		buttonPanel = new ButtonsFirmPanel();
		createFirmPanel = new CreateFirmPanel();
		createFirmPanel.setVisible(false);
		authorsPanel = new AuthorsJPanel("Добави собственик",createFirmPanel.getOwner(),0);
		authorsPanel.setVisible(false);
		searchPanel = new SearchFirmPanel();
		searchPanel.setVisible(false);
		loginFirmPanel = new LoginFirmPanel();
		loginFirmPanel.setVisible(false);
		this.add(buttonPanel, BorderLayout.NORTH);
		createProjectAddActionListner();
		createSearchProjectActionListner();
		createAddAuthorActionListener();
		createSaveActionListener();
		createAuthorsReadyActionListener();
		createSearchProjectsActionListener();
		createDeleteProjectActionListener();
		createLoginActionListener();
		createChart();
	}


	
	private void createChart() {
		searchPanel.getShowGraphChart().addActionListener((ActionEvent e) -> {
		    	try {
		    		remove(graphicShow);
		    		graphicShow = new JPanel();
		            JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(ChartController.getInstance().createChartURLProjects(searchPanel.getPickerFrom().getDate(), searchPanel.getPickerTo().getDate())))));
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

	private void createDeleteProjectActionListener() {
		searchPanel.getDeleteFirm().addActionListener((ActionEvent e) -> {
		    	tablePane.setVisible(true);
		    	searchPanel.setVisible(true);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
		    	createFirmPanel.setVisible(false);
		    	authorsPanel.setVisible(false);
		    	JTable table = (JTable)tablePane.getViewport().getView();
		    	int row = table.getSelectedRow();
		    	if(row == -1) {
		    		JOptionPane.showMessageDialog(null, "Трябва да селектирате фирма!");
		    		return;
		    	}
		    	DefaultTableModel model = (DefaultTableModel)table.getModel();
		    	String firmName = (String) model.getValueAt(row, 0);
		    	firmCntroller.deleteFirm(firmName);
		    	model.removeRow(row);
		    	renderTable(table);
		    	remove(authorsPanel);
		    	remove(createFirmPanel);
		    	add(tablePane, BorderLayout.CENTER);
		    	add(searchPanel, BorderLayout.WEST);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		    	
		});
	}

	private void createSearchProjectsActionListener() {
		searchPanel.getSearchFirms().addActionListener((ActionEvent e) -> {
			    	tablePane.setVisible(true);
			    	searchPanel.setVisible(true);
			    	graphicShow.setVisible(false);
			    	remove(graphicShow);
			    	createFirmPanel.setVisible(false);
			    	authorsPanel.setVisible(false);
			    	JTable table = (JTable)tablePane.getViewport().getView();
			    	DefaultTableModel model = (DefaultTableModel)table.getModel();
			    	model.setRowCount(0);
			    	if(searchPanel.getFirmName().getText() == null || searchPanel.getFirmName().getText().equals("")) {
			    	List<Firm> firms = firmCntroller.getFirmBetweenDates(searchPanel.getPickerFrom().getDate(), searchPanel.getPickerTo().getDate());
				    	for (Firm firm : firms) {
				    		String leader = firm.getLeader().getFirstName() + " " + firm.getLeader().getFamilyName();
				    		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
				    		String begin = format.format(firm.getBeginDate());
				    		model.addRow(new Object[] {
				    				firm.getFirmName(), firm.getFirmType(), begin, leader
							});
						}
			    	} else {
			    		Firm firm = firmCntroller.getFirmByName(searchPanel.getFirmName().getText());
			    		String leader = firm.getLeader().getFirstName() + " " + firm.getLeader().getFamilyName();
			    		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
			    		String begin = format.format(firm.getBeginDate());
			    		model.addRow(new Object[] {
			    				firm.getFirmName(), firm.getFirmType(), begin, leader
						});
			    	}
			    	renderTable(table);
			    	remove(authorsPanel);
			    	remove(createFirmPanel);
			    	add(tablePane, BorderLayout.CENTER);
			    	add(searchPanel, BorderLayout.WEST);
			    	invalidate();
			    	validate();
			    	MainView.resizeFrame();
		    	
		});
	}

	private void createAuthorsReadyActionListener() {
		authorsPanel.getReady().addActionListener((ActionEvent e) ->{
		    	tablePane.setVisible(false);
		    	searchPanel.setVisible(false);
		    	createFirmPanel.setVisible(true);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
		    	authorsPanel.setVisible(false);	
		    	remove(authorsPanel);
		    	remove(tablePane);
		    	remove(searchPanel);
		    	add(createFirmPanel, BorderLayout.CENTER);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		    	
		});
	}

	private void createSaveActionListener() {
		createFirmPanel.getSave().addActionListener((ActionEvent e) -> {
		    	try {
		    		
					firmCntroller
					.createProject(createFirmPanel.getFirmName().getText(),
							createFirmPanel.getFirmType().getSelectedItem().toString(), 
							createFirmPanel.getPickerFrom().getDate(), 
							createFirmPanel.getOwner().getText(),
							String.valueOf(createFirmPanel.getPassword().getPassword())
							);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Попълнете всички полета!");
					return;
				} catch (FirmException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					return;
				}
		    	JOptionPane.showMessageDialog(null, "Добавихте фирма успешно !");
		    	createFirmPanel.clear();
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		    	
		});
	}
	
	private void createLoginActionListener() {
		loginFirmPanel.getSave().addActionListener((ActionEvent e) -> {
		    	try {
		    		
					firmCntroller.loginToFirm(loginFirmPanel.getFirmName().getText(),
							String.valueOf(loginFirmPanel.getPassword().getPassword()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Попълнете всички полета!");
					return;
				} catch (FirmException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					return;
				}
		    	JOptionPane.showMessageDialog(null, "Логнахте се успешно !");
		    	loginFirmPanel.clear();
		    	pane.addTab("Разходи/Приходи", panel);
		    	pane.removeTabAt(0);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		    	
		});
	}

	private void createAddAuthorActionListener() {
		createFirmPanel.getAddOwner().addActionListener((ActionEvent e) -> {
		    	tablePane.setVisible(false);
		    	searchPanel.setVisible(false);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
		    	createFirmPanel.setVisible(true);
		    	authorsPanel.setVisible(true);
		    	authorsPanel.clearGarbage();
		    	remove(tablePane);
		    	remove(searchPanel);
		    	add(createFirmPanel, BorderLayout.CENTER);
		    	add(authorsPanel, BorderLayout.EAST);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		    	
		});
	}

	private void createSearchProjectActionListner() {
		buttonPanel.getSearchFirm().addActionListener((ActionEvent e) -> {
		    	//tablePane.setVisible(true);
		    	//searchPanel.setVisible(true);
				loginFirmPanel.setVisible(true);
		    	createFirmPanel.setVisible(false);
		    	authorsPanel.setVisible(false);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
		    	remove(authorsPanel);
		    	remove(createFirmPanel);
		    	//add(tablePane, BorderLayout.CENTER);
		    	//add(searchPanel, BorderLayout.WEST);
		    	add(loginFirmPanel, BorderLayout.CENTER);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		});
	}

	private void createProjectAddActionListner() {
		buttonPanel.getCreateFirm().addActionListener((ActionEvent e) -> {
		    	tablePane.setVisible(false);
		    	searchPanel.setVisible(false);
		    	loginFirmPanel.setVisible(false);
		    	createFirmPanel.setVisible(true);
		    	graphicShow.setVisible(false);
		    	remove(graphicShow);
		    	authorsPanel.setVisible(false);	  
		    	remove(authorsPanel);
		    	remove(loginFirmPanel);
		    	remove(tablePane);
		    	remove(searchPanel);
		    	add(createFirmPanel, BorderLayout.CENTER);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		});
	}
	
	private void renderTable(JTable table) {
 	   final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 200; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +100 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
}
