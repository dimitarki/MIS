package com.accounting.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.accounting.controller.FirmController;
import com.accounting.controller.PeopleController;
import com.accounting.controller.PersonException;
import com.accounting.model.Person;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class AuthorsJPanel extends JPanel {
	
	private JButton addPerson;
	private JButton ready;
	private AuthorsSearch authorsSearch;
	private CreateAuthorsPanel createAuthors;
	private JTextField passedFieldForOutput;
	private int counterNames = 0;
	private int maximum;
	private PeopleController peopleController;
	public AuthorsJPanel(String type, JTextField fieldForOutput, int max) {
		super(new BorderLayout(10,10));
		peopleController = PeopleController.getInstance();
		this.maximum = max;
		this.setBorder( new TitledBorder(type));
		ready = new JButton();
		authorsSearch = new AuthorsSearch();
		createAuthors = new CreateAuthorsPanel();
		passedFieldForOutput = fieldForOutput;
		createAuthors.setVisible(false);
		this.add(authorsSearch);
		createOpenCreateActionListener();
		createSearchActionListener();
		createGetBackActionListener();
		createAddActionListener();
		createReadyActionListener();
		createAddPersonActionListener();
	}

	private void createAddPersonActionListener() {
		createAuthors.getAddPerson().addActionListener((ActionEvent e) -> {
		    	try {
					peopleController.createPerson(createAuthors.getFirstName().getText(), 
							createAuthors.getFamilyName().getText(), 
							createAuthors.getEGN().getText());
				} catch (PersonException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
		    		return;
				}
		    	JOptionPane.showMessageDialog(null, "Успешно добавихте !");
		    	createAuthors.clear();
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		});
	}

	private void createReadyActionListener() {
		createAuthors.getReady().addActionListener((ActionEvent e) -> {
		       	createAuthors.setVisible(false);
		    	authorsSearch.setVisible(true);
		    	remove(createAuthors);
		    	add(authorsSearch, BorderLayout.CENTER);
		    	DefaultTableModel model = authorsSearch.getTableModel();
		    	model.setRowCount(0);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		});
	}

	private void createAddActionListener() {
		authorsSearch.getAdd().addActionListener((ActionEvent e) -> {
		    	if(counterNames > maximum) {
		    		JOptionPane.showMessageDialog(null, "Не може да добавяте повече хора!");
		    		return;
		    	}
		    	String person = authorsSearch.getSelectedValuesFromTable();
		    	if(person == null || person.equals("")) {
		    		JOptionPane.showMessageDialog(null, "Моля селектирайте човек от таблицата");
		    		return;
		    	}
		    	passedFieldForOutput.setText(passedFieldForOutput.getText() + person);
		    	counterNames++;
		});
	}

	private void createGetBackActionListener() {
		authorsSearch.getBack().addActionListener((ActionEvent e) -> {
		    	ready.doClick();
		});
	}

	private void createSearchActionListener() {
		authorsSearch.getSearch().addActionListener((ActionEvent e) -> {
		    	DefaultTableModel model = authorsSearch.getTableModel();
		    	List<Person> people = peopleController.getPeopleContainingName(authorsSearch.getFamilyName().getText());
		    	model.setRowCount(0);
		    	for (Person per : people) {
					model.addRow(new Object[] {
							per.getEgn(),per.getFirstName(),per.getFamilyName()
					});
				}
		});
	}

	private void createOpenCreateActionListener() {
		authorsSearch.getCreate().addActionListener((ActionEvent e) -> {
		    	createAuthors.setVisible(true);
		    	authorsSearch.setVisible(false);
		    	remove(authorsSearch);
		    	add(createAuthors, BorderLayout.CENTER);
		    	invalidate();
		    	validate();
		    	MainView.resizeFrame();
		});
	}
	
	public JButton getReady() {
		return ready;
	}

	public void clearGarbage() {
    	createAuthors.setVisible(false);
    	authorsSearch.setVisible(true);
    	remove(createAuthors);
    	counterNames = 0;
    	add(authorsSearch, BorderLayout.CENTER);
    	DefaultTableModel model = authorsSearch.getTableModel();
    	passedFieldForOutput.setText("");
    	model.setRowCount(0);
    	invalidate();
    	validate();
    	MainView.resizeFrame();
	}
}
