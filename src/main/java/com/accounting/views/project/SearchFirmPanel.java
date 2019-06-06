package com.accounting.views.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class SearchFirmPanel extends JPanel{

	private JButton searchFirms;
	private JButton deleteFirm;
	private JButton exportToExcel;
	private JButton showGraphChart;
	private JXDatePicker pickerTo;
	private JXDatePicker pickerFrom;
	private JTextField firmName;
	public SearchFirmPanel() {
		super();
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(25dlu;pref), 3dlu, pref:grow",
                "pref"));
       
        firmName = new JTextField(5);
        builder.append("Име на фирмата:", firmName);
        builder.nextLine();
        
        pickerFrom = new JXDatePicker();
        pickerFrom.setDate(Calendar.getInstance().getTime());
        pickerFrom.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        pickerFrom.getEditor().setEditable(false);
        builder.append("От дата:", pickerFrom);
        builder.nextLine();
        pickerTo = new JXDatePicker();
        pickerTo.setDate(Calendar.getInstance().getTime());
        pickerTo.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        pickerTo.getEditor().setEditable(false);
        builder.append("До дата:", pickerTo);
        builder.nextLine();
        deleteFirm = new JButton("Изтрий избраната фирма");
        searchFirms = new JButton("Търси");
        showGraphChart = new JButton("Графика на фирмите за период");
        builder.append(searchFirms, deleteFirm);
        builder.nextLine();
        builder.append(showGraphChart);
        this.add(builder.getPanel());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	
	public JTextField getFirmName() {
		return firmName;
	}
	public JButton getSearchFirms() {
		return searchFirms;
	}
	public JButton getDeleteFirm() {
		return deleteFirm;
	}
	public JXDatePicker getPickerTo() {
		return pickerTo;
	}
	public JXDatePicker getPickerFrom() {
		return pickerFrom;
	}
	public JButton getShowGraphChart() {
		return showGraphChart;
	}
	
	
}
