package com.accounting.views;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class AuthorsSearch extends JPanel{

	private JButton search;
	private JButton add;
	private JButton create;
	private JButton back;
	private JScrollPane tablePane;
	private JTextField familyName;
	public AuthorsSearch() {
		super();
        search = new JButton("Tърси");
        add = new JButton("Добави");
        create = new JButton("Създай");
        back = new JButton("Готово");
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(25dlu;pref), 3dlu, pref:grow",
                "pref"));
		tablePane = TableCommonGenerator.getTableWithPeople(null);
		familyName = new JTextField(5);
        builder.append("Фамилия", familyName);
        builder.nextLine();
        builder.append(search);
        builder.nextLine();
        builder.append(tablePane);
        builder.nextLine();
        builder.append(add, create, back);
        this.add(builder.getPanel());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	public JButton getSearch() {
		return search;
	}
	public JButton getAdd() {
		return add;
	}
	public JButton getCreate() {
		return create;
	}
	public DefaultTableModel getTableModel() {
		JTable table = (JTable)this.tablePane.getViewport().getView();
    	DefaultTableModel model = (DefaultTableModel)table.getModel();
    	return model;
	}
	public String getSelectedValuesFromTable() {
		JTable table = (JTable)this.tablePane.getViewport().getView();
    	int row = table.getSelectedRow();
    	if(row == -1) {
    		JOptionPane.showMessageDialog(null, "Трябва да изберете човек от таблицата!");
    		return "";
    	}
    	String name = (String) table.getModel().getValueAt(row, 1);
    	String familyName = (String) table.getModel().getValueAt(row, 2);
    	return name + " " + familyName + ",";
	}
	public JButton getBack() {
		return back;
	}
	public JTextField getFamilyName() {
		return familyName;
	}
}
