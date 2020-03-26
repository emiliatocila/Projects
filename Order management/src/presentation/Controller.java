package presentation;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import bll.*;
import model.*;

public class Controller {
	private View  view;

	public Controller(View view) {
		this.view  = view;
		view.addShowAllListenerC(new showAllListenerC());
		view.addFindByIdListenerC(new findByIdListenerC());
		view.addInsertListenerC(new insertListenerC());
		view.addDeleteListenerC(new deleteListenerC());
		view.addUpdateListenerC(new updateListenerC());

		view.addShowAllListenerP(new showAllListenerP());
		view.addFindByIdListenerP(new findByIdListenerP());
		view.addInsertListenerP(new insertListenerP());
		view.addDeleteListenerP(new deleteListenerP());
		view.addUpdateListenerP(new updateListenerP());

		view.addShowAllListenerO(new showAllListenerO());
		view.addFindByIdListenerO(new findByIdListenerO());
		view.addInsertListenerO(new insertListenerO());

	}

	class showAllListenerC implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			view.clearPane();
			view.init();
		}
	}
	class findByIdListenerC implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			String id = (String)JOptionPane.showInputDialog(view.getMainPane(), "Search by ID:", "Find by ID", JOptionPane.PLAIN_MESSAGE);
			if (id != null && id.length() > 0) {
				ClientBLL clientBll = new ClientBLL();
				if (clientBll.findClientById(Integer.parseInt(id)) != null) {
					view.setFindByIdC(Integer.parseInt(id));
					view.clearPane();
					view.init();
					view.setFalseFindByIdC();
				}
				else
					JOptionPane.showMessageDialog(view.getMainPane(), "There is no client with the selected ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	class insertListenerC implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			JTextField nameC = new JTextField(20);
			JTextField addC = new JTextField(20);
			JTextField emC = new JTextField(20);
			JTextField ageC = new JTextField(5);
			Object[] message = {"Name: ", nameC, "Address: ", addC, "Email: ", emC, "Age: ", ageC};
			int option = JOptionPane.showConfirmDialog(view.getMainPane(), message, "Insert client", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				ClientBLL clientBll = new ClientBLL();
				Client c = new Client(0, nameC.getText(), addC.getText(), emC.getText(), Integer.parseInt(ageC.getText()));
				clientBll.insert(c);
				view.clearPane();
				view.init();
			}
		}
	}
	class deleteListenerC implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			ClientBLL clientBll = new ClientBLL();
			JTable table = view.getJTable("client");
			int row = table.getSelectedRow();
			if (table.isRowSelected(table.getSelectedRow())){
				Client c = new Client((int)table.getValueAt(row, 0), table.getValueAt(row, 1).toString(), table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(), (int)table.getValueAt(row, 4));
				clientBll.delete(c);
				view.clearPane();
				view.init();
			}
			else
				JOptionPane.showMessageDialog(view.getMainPane(), "Select a client to be deleted!", "Try again", JOptionPane.PLAIN_MESSAGE);
		}
	}
	class updateListenerC implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			ClientBLL clientBll = new ClientBLL();
			JTable table = view.getJTable("client");
			int row = table.getSelectedRow();
			if (table.isRowSelected(table.getSelectedRow())){
				if (table.isEditing())
					table.getCellEditor().stopCellEditing();
				Client c = new Client((int)table.getValueAt(row, 0), table.getValueAt(row, 1).toString(), table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(), Integer.parseInt(table.getValueAt(row, 4).toString()));
				clientBll.update(c);
				view.clearPane();
				view.init();
			}
			else
				JOptionPane.showMessageDialog(view.getMainPane(), "Select a client to be updated!", "Try again", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class showAllListenerP implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			view.clearPane();
			view.init();
		}
	}
	class findByIdListenerP implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			String id = (String)JOptionPane.showInputDialog(view.getMainPane(), "Search by ID:", "Find by ID", JOptionPane.PLAIN_MESSAGE);
			if (id != null && id.length() > 0) {
				ProductBLL productBll = new ProductBLL();
				if (productBll.findProductById(Integer.parseInt(id)) != null){
					view.setFindByIdP(Integer.parseInt(id));
					view.clearPane();
					view.init();
					view.setFalseFindByIdP();
				}
				else 
					JOptionPane.showMessageDialog(view.getMainPane(), "There is no product with the selected ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	class insertListenerP implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			JTextField nameP = new JTextField(1);
			JTextField priceP = new JTextField(1);
			JTextField stockP = new JTextField(1);
			Object[] message = {"Product: ", nameP, "Price: ", priceP, "Stock: ", stockP};
			int option = JOptionPane.showConfirmDialog(view.getMainPane(), message, "Insert product", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				ProductBLL productBll = new ProductBLL();
				Product p = new Product(0, nameP.getText(), Integer.parseInt(priceP.getText()), Integer.parseInt(stockP.getText()));
				productBll.insert(p);
				view.clearPane();
				view.init();
			}
		}
	}
	class deleteListenerP implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			ProductBLL productBll = new ProductBLL();
			JTable table = view.getJTable("product");
			int row = table.getSelectedRow();
			if (table.isRowSelected(table.getSelectedRow())){
				Product p = new Product((int)table.getValueAt(row, 0), table.getValueAt(row, 1).toString(), (int)table.getValueAt(row, 2), (int)table.getValueAt(row, 3));
				productBll.delete(p);
			}
			else
				JOptionPane.showMessageDialog(view.getMainPane(), "Select a product to be deleted!", "Try again", JOptionPane.PLAIN_MESSAGE);
			view.clearPane();
			view.init();
		}
	}
	class updateListenerP implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			ProductBLL productBll = new ProductBLL();
			JTable table = view.getJTable("product");
			int row = table.getSelectedRow();
			if (table.isRowSelected(table.getSelectedRow())){
				if (table.isEditing())
					table.getCellEditor().stopCellEditing();
				Product p = new Product((int)table.getValueAt(row, 0), table.getValueAt(row, 1).toString(), Integer.parseInt(table.getValueAt(row, 2).toString()), Integer.parseInt(table.getValueAt(row, 3).toString()));
				productBll.update(p);
			}
			else
				JOptionPane.showMessageDialog(view.getMainPane(), "Select a product to be updated!", "Try again", JOptionPane.PLAIN_MESSAGE);
			view.clearPane();
			view.init();
		}
	}

	class showAllListenerO implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			view.clearPane();
			view.init();
		}
	}
	class findByIdListenerO implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			String id = (String)JOptionPane.showInputDialog(view.getMainPane(), "Search by ID:", "Find by ID", JOptionPane.PLAIN_MESSAGE);
			if (id != null && id.length() > 0) {
				OrderBLL orderBll = new OrderBLL();
				if (orderBll.findOrderById(Integer.parseInt(id)) != null) {
					view.setFindByIdO(Integer.parseInt(id));
					view.clearPane();
					view.init();
					view.setFalseFindByIdO();
				}
				else
					JOptionPane.showMessageDialog(view.getMainPane(), "There is no order with the selected ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	class insertListenerO implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ProductBLL productBll = new ProductBLL();
			JTextField productO = new JTextField(1);
			JTextField clientO = new JTextField(1);
			JTextField quantO = new JTextField(1);
			Object[] message = {"Product ID: ", productO, "Client ID: ", clientO, "Quantity: ", quantO};
			int option = JOptionPane.showConfirmDialog(view.getMainPane(), message, "Place order", JOptionPane.OK_CANCEL_OPTION);
			int returned = -3;
			if (option == JOptionPane.OK_OPTION) {
				OrderBLL orderBll = new OrderBLL();
				Order o = new Order(0, Integer.parseInt(productO.getText()), Integer.parseInt(clientO.getText()), Integer.parseInt(quantO.getText()));
				returned = orderBll.insert(o);
				if (returned == 0) {
					view.clearPane();
					view.init();
					Product p = productBll.findProductById(o.getProductId()).get(0);
					List<String> lines = Arrays.asList("BON FISCAL", p.getName() + " x " + o.getQuantity() + "....." + Integer.toString(p.getPrice()*o.getQuantity()));
					Path file = Paths.get("order" + o.getOrderId() + ".txt");
					try {
						Files.write(file, lines, Charset.forName("UTF-8"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else if (returned == -2)
					JOptionPane.showMessageDialog(view.getMainPane(), "There are no products with this ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
				else if (returned == -3)
					JOptionPane.showMessageDialog(view.getMainPane(), "There are no clients with this ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
				else if (returned == -1)
					JOptionPane.showMessageDialog(view.getMainPane(), "Understock!", "Try again", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

}
