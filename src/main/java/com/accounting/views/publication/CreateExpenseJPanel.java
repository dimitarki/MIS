package com.accounting.views.publication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXDatePicker;

import com.accounting.controller.FirmController;
import com.accounting.model.firm.Firm;
import com.accounting.views.IntegerFilter;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class CreateExpenseJPanel extends JPanel {
	private JButton currency;
	private JButton save;
	private JTextField price;
	private JComboBox<String> expenseType;
	private JTextField description;
	private JComboBox<String> firm;
	private JTextField currencyType;
	private JXDatePicker date;
	public CreateExpenseJPanel() {
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
	        
	        String[] expenseTypeSt = { "Приход", "Разход"};
	        expenseType = new JComboBox<String>(expenseTypeSt);
	        expenseType.setSelectedIndex(0);
	        builder.append("Тип плащане:", expenseType);
	        builder.nextLine();
	        
	        description = new JTextField(5);
	        builder.append("Описание:", description);
	        builder.nextLine();
	        
	        price = new JTextField();
	        PlainDocument doc = (PlainDocument) price.getDocument();
	        doc.setDocumentFilter(new IntegerFilter());
	        builder.append("Цена:", price);
	        builder.nextLine();
	        
	        
	        currencyType = new JTextField(50);
	        builder.append("Вид валута:", currencyType);
	        builder.nextLine();
	        
	        FirmController controller = FirmController.getInstance();
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
	        
	        date = new JXDatePicker();
	        date.setDate(Calendar.getInstance().getTime());
	        date.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
	        date.getEditor().setEditable(false);
	        builder.append("Дата:", date);
	        builder.nextLine();
	        
	        
	        save = new JButton("Запази");
	        builder.append(save);
	        this.add(builder.getPanel());
	        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	public JButton getAddCurrency() {
		return currency;
	}

	public JButton getSave() {
		return save;
	}


	public JTextField getNumberPrice() {
		return price;
	}

	public JComboBox<String> getExpenseType() {
		return expenseType;
	}

	public JTextField getDescription() {
		return description;
	}

	public JComboBox<String> getFirm() {
		return firm;
	}


	public JTextField getCurrencyType() {
		return currencyType;
	}


	public JXDatePicker getDate() {
		return date;
	}


	public void clear() {
		price.setText("");
		description.setText("");
		currencyType.setText("");
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
