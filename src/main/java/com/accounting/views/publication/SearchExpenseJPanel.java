package com.accounting.views.publication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.accounting.controller.FirmController;
import com.accounting.model.firm.Firm;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class SearchExpenseJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton searchExpenses;
	private JButton deleteExpense;
	private JButton outcomeGraphChart;
	private JButton earnGraphChart;
	private JButton showGraphChart;
	private JXDatePicker dateTo;
	private JXDatePicker dateFrom;
	private JComboBox<String> firm;	

	public SearchExpenseJPanel() {
		super();
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:max(25dlu;pref), 3dlu, pref:grow", "pref"));

        FirmController controller = FirmController.getInstance();
        //List<Firm> firms = controller.getAll();
        List<Firm> firms = new ArrayList<Firm>();
        if(controller.loggedUser != null) {
            firms.add(controller.loggedUser);
        }

        String [] firmsarray = new String[firms.size()];
        for (int i =0; i<firms.size(); i++) {
			firmsarray[i] = firms.get(i).toString();
		}
        firm = new JComboBox<>(firmsarray);
        if(firms.isEmpty()) firm.addItem("");
        firm.setSelectedIndex(0);
        builder.append("Фирмата е:", firm);
        builder.nextLine();
        
		dateFrom = new JXDatePicker();
		dateFrom.setDate(Calendar.getInstance().getTime());
		dateFrom.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		dateFrom.getEditor().setEditable(false);
		builder.append("От дата:", dateFrom);
		builder.nextLine();

		dateTo = new JXDatePicker();
		dateTo.setDate(Calendar.getInstance().getTime());
		dateTo.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		dateTo.getEditor().setEditable(false);
		builder.append("До дата:", dateTo);
		builder.nextLine();

		deleteExpense = new JButton("Изтрий избрания разход/приход");
		searchExpenses = new JButton("Търси");
		showGraphChart = new JButton("Графика на приходи за период");
		outcomeGraphChart = new JButton("Графика на разходи за период");
		earnGraphChart = new JButton("Графика за печалби за период");
		builder.append(searchExpenses, deleteExpense);
		builder.nextLine();
		builder.append(outcomeGraphChart, showGraphChart);
		builder.nextLine();
		builder.append(earnGraphChart);
		this.add(builder.getPanel());
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	public JButton getSearchExpense() {
		return searchExpenses;
	}

	public JButton getDeleteExpense() {
		return deleteExpense;
	}

	public JButton getOutcomeGraphChart() {
		return outcomeGraphChart;
	}

	public JXDatePicker getDateTo() {
		return dateTo;
	}

	public JXDatePicker getDateFrom() {
		return dateFrom;
	}

	public JButton getShowGraphChart() {
		return showGraphChart;
	}
	
	public JComboBox<String> getFirm() {
		return firm;
	}

	public JButton getEarnGraphChart() {
		return earnGraphChart;
	}

	public void reload() {
		firm.removeAllItems();
		FirmController controller = FirmController.getInstance();
        //List<Firm> firms = controller.getAll();
        List<Firm> firms = new ArrayList<Firm>();
        if(controller.loggedUser != null) {
	        firms.add(controller.loggedUser);	
        }
        if(firms.isEmpty()) firm.addItem("");
        for (int i =0; i<firms.size(); i++) {
			firm.addItem(firms.get(i).toString());
			
		}
        firm.setSelectedIndex(0);
	}
	
}
