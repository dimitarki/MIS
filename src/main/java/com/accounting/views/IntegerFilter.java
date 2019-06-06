package com.accounting.views;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntegerFilter extends DocumentFilter {
	@Override
	   public void insertString(FilterBypass fb, int offset, String string,
	         AttributeSet attr) throws BadLocationException {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.insert(offset, string);
	      
	      if (test(sb.toString()) || sb.toString().equals("")) {
	         super.insertString(fb, offset, string, attr);
	      } else {
	    	  JOptionPane.showMessageDialog(null, "Само числа са позволени!");
	    	  return;
	      }
	   }

	   private boolean test(String text) {
	      try {
	         Integer.parseInt(text);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	   }

	   @Override
	   public void replace(FilterBypass fb, int offset, int length, String text,
	         AttributeSet attrs) throws BadLocationException {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.replace(offset, offset + length, text);

	      if (test(sb.toString()) || sb.toString().equals("")) {
	         super.replace(fb, offset, length, text, attrs);
	      } else {
	    	  JOptionPane.showMessageDialog(null, "Само числа са позволени!");
	    	  return;
	      }

	   }

	   @Override
	   public void remove(FilterBypass fb, int offset, int length)
	         throws BadLocationException {
	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.delete(offset, offset + length);

	      if (test(sb.toString()) || sb.toString().equals("")) {
	         super.remove(fb, offset, length);
	      } else {
	    	  JOptionPane.showMessageDialog(null, "Само числа са позволени!");
	    	  return;
	      }

	   }
}
