package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bll.*;
import model.*;

public class View extends JFrame {
	private static final long serialVersionUID = 1502202796906896029L;
	protected static final Logger LOGGER = Logger.getLogger(View.class.getName());
	JPanel clientPanel;
	JPanel productPanel;
	JPanel orderPanel;
	JTable clientTable;
	JTable productTable;
	JTable orderTable;
	JScrollPane scrollClient;
	JScrollPane scrollProduct;
	JScrollPane scrollOrder;
	JTabbedPane tabbedPane = new JTabbedPane();
	int findByIdCOk = 0;
	int findByIdPOk = 0;
	int findByIdOOk = 0;
	private JButton cShowAllBtn = new JButton("Show all");
	private JButton cFindByIdBtn = new JButton("Find by ID");
	private JButton cInsertBtn = new JButton("Insert");
	private JButton cDeleteBtn = new JButton("Delete");
	private JButton cUpdateBtn = new JButton("Update");
	private JButton pShowAllBtn = new JButton("Show all");
	private JButton pFindByIdBtn = new JButton("Find by ID");
	private JButton pInsertBtn = new JButton("Insert");
	private JButton pDeleteBtn = new JButton("Delete");
	private JButton pUpdateBtn = new JButton("Update");
	private JButton oShowAllBtn = new JButton("Show all");
	private JButton oFindByIdBtn = new JButton("Find by ID");
	private JButton oInsertBtn = new JButton("Place order");

	public View() {  
		clientPanel = new JPanel();
		productPanel = new JPanel();
		orderPanel = new JPanel();

		clientPanel.setLayout(new FlowLayout());
		productPanel.setLayout(new FlowLayout());
		orderPanel.setLayout(new FlowLayout());

		this.init();
		tabbedPane.addTab("Client", clientPanel);
		tabbedPane.addTab("Product", productPanel);
		tabbedPane.addTab("Order", orderPanel);
		this.setContentPane(tabbedPane);

		this.pack();
		this.setSize(1000, 600);
		this.setTitle("Order management");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void clearPane() {
		clientPanel.removeAll();
		clientPanel.revalidate();
		productPanel.removeAll();
		productPanel.revalidate();
		orderPanel.removeAll();
		orderPanel.revalidate();
	}

	public void init() {
		this.initC();
		this.initP();
		this.initO();
	}

	public void initC() {
		if (findByIdCOk != 0)
			this.createTableFindByIdC(findByIdCOk);
		else
			this.createTableC();
		JPanel clientPanelB = new JPanel();
		clientPanelB.setLayout((new BoxLayout(clientPanelB, BoxLayout.Y_AXIS)));
		clientPanelB.add(cShowAllBtn);
		clientPanelB.add(Box.createRigidArea(new Dimension(0, 20))); 
		clientPanelB.add(cFindByIdBtn);
		clientPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		clientPanelB.add(cInsertBtn);
		clientPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		clientPanelB.add(cDeleteBtn);
		clientPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		clientPanelB.add(cUpdateBtn);
		clientPanel.add(clientPanelB);
	} 

	public void initP() {
		if (findByIdPOk != 0)
			this.createTableFindByIdP(findByIdPOk);
		else
			this.createTableP();
		JPanel productPanelB = new JPanel();
		productPanelB.setLayout((new BoxLayout(productPanelB, BoxLayout.Y_AXIS)));
		productPanelB.add(pShowAllBtn);
		productPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		productPanelB.add(pFindByIdBtn);
		productPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		productPanelB.add(pInsertBtn);
		productPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		productPanelB.add(pDeleteBtn);
		productPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		productPanelB.add(pUpdateBtn);
		productPanel.add(productPanelB);
	}

	public void initO() {
		if (findByIdOOk != 0)
			this.createTableFindByIdO(findByIdOOk);
		else
			this.createTableO();
		JPanel orderPanelB = new JPanel();
		orderPanelB.setLayout((new BoxLayout(orderPanelB, BoxLayout.Y_AXIS)));
		orderPanelB.add(oShowAllBtn);
		orderPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		orderPanelB.add(oFindByIdBtn);
		orderPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
		orderPanelB.add(oInsertBtn);
		orderPanel.add(orderPanelB);
	}

	public void createTableC() {
		ArrayList<Client> clients;
		ClientBLL clientBll = new ClientBLL();
		clients = clientBll.viewAll();
		clientTable = generateTable(clients);
		JScrollPane scrollClient = new JScrollPane(clientTable);
		scrollClient.setPreferredSize(new Dimension(800, 500));
		clientPanel.add(scrollClient);
	}

	public void createTableP() {
		ArrayList<Product> products;
		ProductBLL productBll = new ProductBLL();
		products = productBll.viewAll();
		productTable = generateTable(products);
		JScrollPane scrollProduct = new JScrollPane(productTable);
		scrollProduct.setPreferredSize(new Dimension(800, 500));
		productPanel.add(scrollProduct);
	}

	public void createTableO() {
		ArrayList<Order> orders;
		OrderBLL orderBll = new OrderBLL();
		orders = orderBll.viewAll();
		orderTable = generateTable(orders);
		JScrollPane scrollOrder = new JScrollPane(orderTable);
		scrollOrder.setPreferredSize(new Dimension(800, 500));
		orderPanel.add(scrollOrder);
	}

	public void createTableFindByIdC(int id) {
		ArrayList<Client> clients;
		ClientBLL clientBll = new ClientBLL();
		clients = clientBll.findClientById(id);
		clientTable = generateTable(clients);
		JScrollPane scrollClient = new JScrollPane(clientTable);
		scrollClient.setPreferredSize(new Dimension(800, 500));
		clientPanel.add(scrollClient);
	}

	public void createTableFindByIdP(int id) {
		ArrayList<Product> products;
		ProductBLL productBll = new ProductBLL();
		products = productBll.findProductById(id);
		productTable = generateTable(products);
		JScrollPane scrollProduct = new JScrollPane(productTable);
		scrollProduct.setPreferredSize(new Dimension(800, 500));
		productPanel.add(scrollProduct);
	}

	public void createTableFindByIdO(int id) {
		ArrayList<Order> orders;
		OrderBLL orderBll = new OrderBLL();
		orders = orderBll.findOrderById(id);
		orderTable = generateTable(orders);
		JScrollPane scrollOrder = new JScrollPane(orderTable);
		scrollOrder.setPreferredSize(new Dimension(800, 500));
		orderPanel.add(scrollOrder);
	}

	public static JTable generateTable(ArrayList <? extends Object> list) {
		String[] columnNames = new String[list.get(0).getClass().getDeclaredFields().length];
		Object[][] data = new Object[list.size()][list.get(0).getClass().getDeclaredFields().length];
		int nrOfColumns = 0;
		int rowNr = 0;
		int columnNr = 0;
		for (Field field : list.get(0).getClass().getDeclaredFields())
			columnNames[nrOfColumns++] = field.getName().toUpperCase();
		for (Object o : list) {
			for (Field field : o.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					data[rowNr][columnNr++] = field.get(o);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			rowNr++;
			columnNr = 0;
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		JTable table = new JTable(data, columnNames);
		for (int i = 0; i < columnNames.length; i++) 
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		table.getTableHeader().setBackground(new Color(234, 223, 231));
		table.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 15));
		table.setFont(new Font("Helvetica", Font.PLAIN, 15));
		table.getTableHeader().setPreferredSize(new Dimension(15, 40));
		table.setRowHeight(30);
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return !(column == 0);
			}
		};
		table.setModel(tableModel);
		return table;
	}

	void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	JTabbedPane getMainPane() {
		return tabbedPane;
	}

	JTable getJTable(String o) {
		if (o.equals("client"))
			return clientTable;
		else if (o.equals("product"))
			return productTable;
		else if (o.equals("order"))
			return orderTable;
		else return null;
	}

	public void setFindByIdC(int id) {
		findByIdCOk = id;
	}

	public void setFindByIdP(int id) {
		findByIdPOk = id;
	}

	public void setFindByIdO(int id) {
		findByIdOOk = id;
	}

	public void setFalseFindByIdC() {
		findByIdCOk = 0;
	}

	public void setFalseFindByIdP() {
		findByIdPOk = 0;
	}

	public void setFalseFindByIdO() {
		findByIdOOk = 0;
	}

	void addShowAllListenerC(ActionListener al) {
		cShowAllBtn.addActionListener(al);
	}
	void addFindByIdListenerC(ActionListener al) {
		cFindByIdBtn.addActionListener(al);
	}
	void addInsertListenerC(ActionListener al) {
		cInsertBtn.addActionListener(al);
	}
	void addDeleteListenerC(ActionListener al) {
		cDeleteBtn.addActionListener(al);
	}
	void addUpdateListenerC(ActionListener al) {
		cUpdateBtn.addActionListener(al);
	}
	void addShowAllListenerP(ActionListener al) {
		pShowAllBtn.addActionListener(al);
	}
	void addFindByIdListenerP(ActionListener al) {
		pFindByIdBtn.addActionListener(al);
	}
	void addInsertListenerP(ActionListener al) {
		pInsertBtn.addActionListener(al);
	}
	void addDeleteListenerP(ActionListener al) {
		pDeleteBtn.addActionListener(al);
	}
	void addUpdateListenerP(ActionListener al) {
		pUpdateBtn.addActionListener(al);
	}
	void addShowAllListenerO(ActionListener al) {
		oShowAllBtn.addActionListener(al);
	}
	void addFindByIdListenerO(ActionListener al) {
		oFindByIdBtn.addActionListener(al);
	}
	void addInsertListenerO(ActionListener al) {
		oInsertBtn.addActionListener(al);
	}
}
