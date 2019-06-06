package com.accounting.views.project;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class LoginFirmPanel extends JPanel {
	private JButton save;
	private JTextField firmName;
	private JPasswordField password;
	public LoginFirmPanel() {
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
        
        save = new JButton("Логване");
        
        firmName = new JTextField(5);
        builder.append("Име на фирмата:", firmName);
        builder.nextLine();
        
        password = new JPasswordField(5);
        builder.append("Парола:", password);
        builder.nextLine();
        
       
        
        
        
     
        
        
        builder.append(save);
        this.add(builder.getPanel());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	public JTextField getFirmName() {
		return firmName;
	}

	public void setFirmName(JTextField firmName) {
		this.firmName = firmName;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public void setPassword(JPasswordField password) {
		this.password = password;
	}

	public JButton getSave() {
		return save;
	}

	public void clear() {
		password.setText("");
		firmName.setText("");
	}

}
