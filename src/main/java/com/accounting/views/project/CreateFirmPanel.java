package com.accounting.views.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class CreateFirmPanel extends JPanel{
	
	private JButton addOwner;
	private JButton save;
	private JTextField owner;
	private JTextField firmName;
	private JPasswordField password;
	private JComboBox<String> firmType;
	private JXDatePicker pickerFrom;
	public CreateFirmPanel() {
		 super();
		 	DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(25dlu;pref), 3dlu, pref:grow",
	                "pref"));
	        builder.appendColumn("right:pref");
	        builder.appendColumn("3dlu");
	        builder.appendColumn("fill:max(pref; 100px)");
	        builder.appendColumn("5dlu");
	        builder.appendColumn("right:pref");
	        builder.appendColumn("3dlu");
	        builder.appendColumn("fill:max(pref; 100px)");
	        
	        addOwner= new JButton("Добави собственик");
	        save = new JButton("Запази");
	        
	        firmName = new JTextField(5);
	        builder.append("Име на фирмата:", firmName);
	        builder.nextLine();
	        
	        password = new JPasswordField(5);
	        builder.append("Парола:", password);
	        builder.nextLine();
	        
	        String[] contractType = { "ЕТ", "ООД", "ЕООД"};
	        firmType = new JComboBox<String>(contractType);
	        firmType.setSelectedIndex(1);
	        builder.append("Тип на фирмата:", firmType);
	        builder.nextLine();
	        
	        
	        
	        
	        pickerFrom = new JXDatePicker();
	        pickerFrom.setDate(Calendar.getInstance().getTime());
	        pickerFrom.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
	        pickerFrom.getEditor().setEditable(false);
	        builder.append("Ден на отваряне:", pickerFrom);
	        builder.nextLine();
	        
	        
	        owner = new JTextField();
	        owner.setEditable(false);
	        builder.append("Име и фамилия на собственика:", owner);
	        builder.nextLine();
	        builder.append(addOwner);
	        builder.nextLine();
	        
	        
	        builder.append(save);
	        this.add(builder.getPanel());
	        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	public JButton getAddOwner() {
		return addOwner;
	}
	public JButton getSave() {
		return save;
	}
	public JTextField getOwner() {
		return owner;
	}
	
	public JPasswordField getPassword() {
		return password;
	}
	public JTextField getFirmName() {
		return firmName;
	}
	public JComboBox<String> getFirmType() {
		return firmType;
	}
	public JXDatePicker getPickerFrom() {
		return pickerFrom;
	}
	public void clear() {
		owner.setText("");
		firmName.setText("");
		password.setText("");
	}
	
}
