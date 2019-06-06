package com.accounting.views;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class CreateAuthorsPanel extends JPanel{
	private JButton addPerson;
	private JButton ready;
	private JTextField firstName;
	private JTextField familyName;
	private JTextField egn;
	public CreateAuthorsPanel() {
		super();
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(25dlu;pref), 3dlu, pref:grow",
                "pref"));
       
        firstName = new JTextField(5);
        builder.append("Име:", firstName);
        builder.nextLine();
        familyName = new JTextField(5);
        builder.append("Фамилия:", familyName);
        builder.nextLine();
        egn = new JTextField(5);
        builder.append("ЕГН:", egn);
        builder.nextLine();
        addPerson = new JButton("Добави");
        ready = new JButton("Готово");
        builder.append(addPerson,ready);
        this.add(builder.getPanel());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	public JButton getReady() {
		return ready;
	}
	public JTextField getFirstName() {
		return firstName;
	}
	public JTextField getFamilyName() {
		return familyName;
	}
	public JTextField getEGN() {
		return egn;
	}
	public JButton getAddPerson() {
		return addPerson;
	}
	public void clear() {
		firstName.setText("");
		familyName.setText("");
		egn.setText("");
	}
	
}
