package com.accounting.views;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TableCommonGenerator {

	public static JScrollPane getConfiguredForFirmPanel() {
		
		String[] header = {"Име на фирма", "Тип на фирма", "Дата на отваряне","Собственик"};
		DefaultTableModel model = null;
		model = new DefaultTableModel(null, header);

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Dimension tablePreferred = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(
            new Dimension(tablePreferred.width, tablePreferred.height/3) );
        return tableScroll;
	}
	
	public static JScrollPane getConfiguredForExpensesPanel() {
		
		String[] header = {"Идентификация","Тип",  "Описание", "Фирма", "Валута",
				"Парична стойност", "Дата"};
		DefaultTableModel model = null;
		model = new DefaultTableModel(null, header);

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Dimension tablePreferred = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(
            new Dimension(tablePreferred.width, tablePreferred.height/3) );
        return tableScroll;
	}
	
	public static JScrollPane getTableWithPeople(String searchPersonFamilyName) {
		String[] header = {"ЕГН", "Име", "Фамилия"};
		DefaultTableModel model = null;
		model = new DefaultTableModel(null, header);

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(table);
        Dimension tablePreferred = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(
            new Dimension(tablePreferred.width, tablePreferred.height/3) );
        return tableScroll;
	}
}
