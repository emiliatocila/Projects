package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import bll.*;

public class Controller {
	private View view;
	private Restaurant restaurant;
	ImageIcon icon1 = new ImageIcon("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//icons//Apple.png", "Apple");
	ImageIcon icon2 = new ImageIcon("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//icons//Chicken.png", "Chicken");
	ImageIcon icon3 = new ImageIcon("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//icons//Cookie.png", "Cookie");
	ImageIcon icon4 = new ImageIcon("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//icons//Fish.png", "Fish");

	public Controller(View view, Restaurant restaurant) {
		this.view  = view;
		this.restaurant = restaurant;
		view.addShowAllListenerA(new showAllListenerA());
		view.addInsertListenerA(new insertListenerA());
		view.addDeleteListenerA(new deleteListenerA());
		view.addUpdateListenerA(new updateListenerA());

		view.addShowAllListenerW(new showAllListenerW());
		view.addInsertListenerW(new insertListenerW());
		view.addShowItemsListenerW(new showItemsListenerW());
		view.addComputeBillListenerW(new computeBillListenerW());
	}

	class showAllListenerA implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			view.clearPane();
			view.init();
		}
	}

	class insertListenerA implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object[] baseOrComposite = {"Base Product", "Composite Product"};
			int bOrC = JOptionPane.showOptionDialog(view.getMainPane(), "Do you want to add a base or composite product?", "Base Or Composite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon1, baseOrComposite, baseOrComposite[0]);
			if (bOrC == JOptionPane.YES_OPTION) {
				JTextField name = new JTextField(20);
				JTextField price = new JTextField(20);
				Object[] message = {"Name: ", name, "Price: ", price};
				int option = JOptionPane.showConfirmDialog(view.getMainPane(), message, "Add Base Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon3);
				if (option == JOptionPane.OK_OPTION) {
					MenuItem baseProduct = new BaseProduct(name.getText(), Double.parseDouble(price.getText()));
					restaurant.add(baseProduct);
					view.clearPane();
					view.init();
				}
			}
			else if (bOrC == JOptionPane.NO_OPTION){
				JTable table = view.getJTable("administrator");
				int []rows = table.getSelectedRows();
				JTextField name = new JTextField(20);
				Object[] message = {"Name: ", name};
				int option = JOptionPane.showConfirmDialog(view.getMainPane(), message, "Add Composite Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon4);
				ArrayList<MenuItem> itemsForComposite = new ArrayList<MenuItem>();
				for(int i = 0; i < rows.length; i++) {
					MenuItem item = new BaseProduct(table.getValueAt(rows[i], 0).toString(), (double)table.getValueAt(rows[i], 1));
					itemsForComposite.add(item);
				}
				if (option == JOptionPane.OK_OPTION) {
					if (!itemsForComposite.isEmpty()){
						MenuItem compositeProduct = new CompositeProduct(name.getText(), itemsForComposite);
						restaurant.add(compositeProduct);
						view.clearPane();
						view.init();
					}
					else
						JOptionPane.showMessageDialog(view.getMainPane(), "Select base products to form a composite product!", "Try again", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}

	class deleteListenerA implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			JTable table = view.getJTable("administrator");
			int []rows = table.getSelectedRows();
			if (rows.length != 0){
				for(int i = 0; i < rows.length; i++)
					restaurant.remove(table.getValueAt(rows[i], 0).toString());
				view.clearPane();
				view.init();
			}
			else
				JOptionPane.showMessageDialog(view.getMainPane(), "Select an item to be deleted!", "Try again", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class updateListenerA implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			JTable table = view.getJTable("administrator");
			int row = table.getSelectedRow();
			if (table.isRowSelected(table.getSelectedRow())){
				if (table.isEditing())
					table.getCellEditor().stopCellEditing();
				restaurant.edit(table.getValueAt(row, 0).toString(), Double.parseDouble(table.getValueAt(row, 1).toString()));
				view.clearPane();
				view.init();
			}
			else
				JOptionPane.showMessageDialog(view.getMainPane(), "Select a product to be updated!", "Try again", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class showAllListenerW implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			view.setFalseShowItemsWaiter();
			view.clearPane();
			view.init();
		}
	}

	class showItemsListenerW implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			view.setTrueShowItemsWaiter();
			view.clearPane();
			view.init();
		}
	}
	
	class insertListenerW implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			JTextField orderID = new JTextField(20);
			JTextField date = new JTextField(20);
			JTextField tableNr = new JTextField(20);
			Object[] message = {"Order ID: ", orderID, "Date: ", date, "Table: ", tableNr};
			int option = JOptionPane.showConfirmDialog(view.getMainPane(), message, "Place New Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon2);
			if (option == JOptionPane.OK_OPTION) {
				JTable table = view.getJTable("waiterItems");
				int []rows = table.getSelectedRows();
				ArrayList<String> products = new ArrayList<String>();
				if (rows.length != 0){
					for(int i = 0; i < rows.length; i++)
						products.add(table.getValueAt(rows[i], 0).toString());
					Order order = new Order(Integer.parseInt(orderID.getText()), date.getText(), Integer.parseInt(tableNr.getText()));
					restaurant.createOrder(order, products);
					view.clearPane();
					view.init();
				}
			}
			else
				JOptionPane.showMessageDialog(view.getMainPane(), "Select products to place the order!", "Try again", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class computeBillListenerW implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JTable table = view.getJTable("waiter");
			int row = table.getSelectedRow();
			if (table.isRowSelected(table.getSelectedRow())){ 
				List<String> lines = restaurant.generateBillContent(new Order(Integer.parseInt(table.getValueAt(row, 0).toString()), table.getValueAt(row, 1).toString(), Integer.parseInt(table.getValueAt(row, 2).toString())));
				Path file = Paths.get("order" + table.getValueAt(row, 0).toString() + "_" + table.getValueAt(row, 1).toString() + "_table" + table.getValueAt(row, 2).toString() + ".txt");
				try {
					Files.write(file, lines, Charset.forName("UTF-8"));
					view.clearPane();
					view.init();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else  
				JOptionPane.showMessageDialog(view.getMainPane(), "Select an order for which to compute bill!", "Try again", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
