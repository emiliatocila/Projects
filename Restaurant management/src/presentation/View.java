package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bll.*;

@SuppressWarnings("serial")
public class View extends JFrame {
	JPanel administratorPanel;
	JPanel waiterPanel;
	JTable administratorTable;
	JTable waiterTable;
	JTable waiterItemsTable;
	JScrollPane administratorScroll;
	JScrollPane waiterScroll;
	JScrollPane waiterItemsScroll;
	JTabbedPane tabbedPane = new JTabbedPane();
	private JButton aShowAllBtn = new JButton("View all");
	private JButton aInsertBtn = new JButton("Add Menu Item");
	private JButton aDeleteBtn = new JButton("Delete Menu Item");
	private JButton aUpdateBtn = new JButton("Update Menu Item");
	private JButton wShowAllBtn = new JButton("View all");
	private JButton wInsertBtn = new JButton("Place New Order");
	private JButton wShowItemsBtn = new JButton("Show Items");
	private JButton wComputeBillBtn = new JButton("Compute bill");
	private int showItems = 0;

	public View() {  
		administratorPanel = new JPanel();
		waiterPanel = new JPanel();

		administratorPanel.setLayout(new FlowLayout());
		waiterPanel.setLayout(new FlowLayout());

		this.init();
		tabbedPane.addTab("Administrator", administratorPanel);
		tabbedPane.addTab("Waiter", waiterPanel);
		this.setContentPane(tabbedPane);

		this.pack();
		this.setSize(1800, 600);
		this.setTitle("Restaurant Management");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void clearPane() {
		administratorPanel.removeAll();
		administratorPanel.revalidate();
		waiterPanel.removeAll();
		waiterPanel.revalidate();
	}

	public void init() {
		this.initA();
		this.initW();
	}

	public void initA() {
		this.createTableA();
		JPanel administratorPanelB = new JPanel();
		administratorPanelB.setLayout((new BoxLayout(administratorPanelB, BoxLayout.Y_AXIS)));
		administratorPanelB.add(aShowAllBtn);
		administratorPanelB.add(Box.createRigidArea(new Dimension(0, 20))); 
		administratorPanelB.add(aInsertBtn);
		administratorPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		administratorPanelB.add(aDeleteBtn);
		administratorPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		administratorPanelB.add(aUpdateBtn);
		administratorPanel.add(administratorPanelB);
	} 

	public void initW() {
		this.createTableW();
		if(showItems == 1)
			this.createTableItemsW();
		JPanel waiterPanelB = new JPanel();
		waiterPanelB.setLayout((new BoxLayout(waiterPanelB, BoxLayout.Y_AXIS)));
		waiterPanelB.add(wShowAllBtn);
		waiterPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		waiterPanelB.add(wShowItemsBtn);
		waiterPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		waiterPanelB.add(wInsertBtn);
		waiterPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		waiterPanelB.add(wComputeBillBtn);
		waiterPanel.add(waiterPanelB);
	}

	public void createTableA() {
		administratorTable = generateTable(Restaurant.items);
		JScrollPane scrollAdministrator = new JScrollPane(administratorTable);
		scrollAdministrator.setPreferredSize(new Dimension(800, 500));
		administratorPanel.add(scrollAdministrator);
	}

	public void createTableW() {
		ArrayList<Order> orders = new ArrayList<Order>();
		orders.addAll(Restaurant.itemsOfOrders.keySet());
		waiterTable = generateTable(orders);
		JScrollPane scrollWaiter = new JScrollPane(waiterTable);
		scrollWaiter.setPreferredSize(new Dimension(800, 500));
		waiterPanel.add(scrollWaiter);
	}

	public void createTableItemsW() {
		waiterItemsTable = generateTable(Restaurant.items);
		JScrollPane scrollWaiterItems = new JScrollPane(waiterItemsTable);
		scrollWaiterItems.setPreferredSize(new Dimension(800, 500));
		waiterPanel.add(scrollWaiterItems);
	}

	public static JTable generateTable(ArrayList <? extends Object> list) {
		String[] columnNames = null;
		Vector<String> columnNamesNull = new Vector<String>();
		int length = 0;
		Object[][] data = null;
		JTable table = null;
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		try {
			length = list.get(0).getClass().getDeclaredFields().length;
		} catch (Exception e) {
		}
		if(length == 0) {
			columnNamesNull.addElement("ORDERID");
			columnNamesNull.addElement("DATE");
			columnNamesNull.addElement("TABLE");
			table = new JTable(new Vector<String>(), columnNamesNull);
		}
		else {
			columnNames = new String[list.get(0).getClass().getDeclaredFields().length];
			data = new Object[list.size()][list.get(0).getClass().getDeclaredFields().length];
			int nrOfColumns = 0;
			int rowNr = 0;
			int columnNr = 0;
			for (Field field : list.get(0).getClass().getDeclaredFields())
				columnNames[nrOfColumns++] = field.getName().toUpperCase();
			for (Object o : list) {
				for (Field field : o.getClass().getDeclaredFields()) {
					if (!field.getName().equals("baseProducts")) {
						field.setAccessible(true);
						try {
							data[rowNr][columnNr++] = field.get(o);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
				rowNr++;
				columnNr = 0;
			}
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table = new JTable(data, columnNames);
			for (int i = 0; i < columnNames.length; i++) 
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return !(column == 0);
				}
			};
			table.setModel(tableModel);
		}
		table.getTableHeader().setBackground(new Color(234, 223, 231));
		table.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 15));
		table.setFont(new Font("Helvetica", Font.PLAIN, 15));
		table.getTableHeader().setPreferredSize(new Dimension(15, 40));
		table.setRowHeight(30);
		return table;
	}

	void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	JTabbedPane getMainPane() {
		return tabbedPane;
	}

	JTable getJTable(String o) {
		if (o.equals("administrator"))
			return administratorTable;
		else if (o.equals("waiter"))
			return waiterTable;
		else if (o.equals("waiterItems"))
			return waiterItemsTable;
		else return null;
	}

	void setTrueShowItemsWaiter() {
		showItems  = 1;
	}

	void setFalseShowItemsWaiter() {
		showItems  = 0;
	}

	void addShowAllListenerA(ActionListener al) {
		aShowAllBtn.addActionListener(al);
	}
	void addInsertListenerA(ActionListener al) {
		aInsertBtn.addActionListener(al);
	}
	void addDeleteListenerA(ActionListener al) {
		aDeleteBtn.addActionListener(al);
	}
	void addUpdateListenerA(ActionListener al) {
		aUpdateBtn.addActionListener(al);
	}
	void addShowAllListenerW(ActionListener al) {
		wShowAllBtn.addActionListener(al);
	}
	void addInsertListenerW(ActionListener al) {
		wInsertBtn.addActionListener(al);
	}
	void addShowItemsListenerW(ActionListener al) {
		wShowItemsBtn.addActionListener(al);
	}
	void addComputeBillListenerW(ActionListener al) {
		wComputeBillBtn.addActionListener(al);
	}

}
