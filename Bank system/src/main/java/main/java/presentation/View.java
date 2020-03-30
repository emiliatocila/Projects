package main.java.presentation;

import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.java.bll.*;
import main.java.model.*;

public class View extends JFrame{
    private static final long serialVersionUID = 0;
    protected static final Logger LOGGER = Logger.getLogger(View.class.getName());

    JPanel loginPanel;
    JButton loginButton = new JButton("Login");
    JTextField username = new JTextField(15);
    JPasswordField password = new JPasswordField(15);

    int isAdmin = 0;

    JPanel administratorPanel;
    JTabbedPane employeePanel;

    JTable employeesTable;
    JPanel employeesPanel;
    JScrollPane scrollEmployees;
    JTable usersTable;
    JScrollPane scrollUsers;
    private JButton aShowAllEmployeesBtn =  new JButton("Show all employees");
    private JButton aFindEmployeeByIdBtn = new JButton("Find employee by ID");
    private JButton aInsertBtn = new JButton("Add new employee");
    private JButton aDeleteBtn = new JButton("Delete employee");
    private JButton aUpdateBtn = new JButton("Update employee");
    private JButton aGenerateReportBtn = new JButton("Generate report for employee");
    int findEmployeeByIdEOK = 0;

    JTable clientsTable;
    JPanel clientsPanel;
    JScrollPane scrollClients;
    private JButton eShowAllClientsBtn =  new JButton("Show all clients");
    private JButton eFindClientByIdBtn = new JButton("Find client by ID");
    private JButton eInsertBtn = new JButton("Add new client");
    private JButton eUpdateBtn = new JButton("Update client");
    int findClientByIdEOK = 0;

    JTable accountsTable;
    JPanel accountsPanel;
    JScrollPane scrollAccounts;
    private JButton acShowAllAccountsBtn =  new JButton("Show all accounts");
    private JButton acFindAccountByIdBtn = new JButton("Find account by ID");
    private JButton acInsertBtn = new JButton("Add new account");
    private JButton acDeleteBtn = new JButton("Delete account");
    private JButton acUpdateBtn = new JButton("Update account");
    private JButton acTransferMoneyBetweenAccounts = new JButton("Transfer money between accounts");
    private JButton acPayUtilities = new JButton("Process utility bill");
    int findAccountByIdEOK = 0;

    public View(){
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        this.initLogin();
        this.setContentPane(loginPanel);
        this.pack();
        this.setSize(400, 200);
        this.setTitle("Bank system");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initLogin(){
        JPanel loginInfoPanel = new JPanel();
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout());
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        loginInfoPanel.setLayout((new BoxLayout(loginInfoPanel, BoxLayout.Y_AXIS)));
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        password.setEchoChar('*');
        usernamePanel.add(usernameLabel);
        usernamePanel.add(username);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);
        loginInfoPanel.add(usernamePanel);
        loginInfoPanel.add(passwordPanel);
        loginInfoPanel.add(loginButton);
        loginPanel.add(loginInfoPanel);
    }

    public void clearLoginPane(){
        loginPanel.setVisible(false);
    }


    public void clearAdministratorPane(){
        administratorPanel.removeAll();
        administratorPanel.revalidate();
    }

    public void clearEmployeePane(){
        employeePanel.removeAll();
        employeePanel.revalidate();
        clientsPanel.removeAll();
        clientsPanel.revalidate();
        accountsPanel.removeAll();
        accountsPanel.revalidate();
    }

    public void initAdministrator(){
        employeesPanel = new JPanel();
        employeesPanel.setLayout(new FlowLayout());
        if (findEmployeeByIdEOK != 0)
            this.createTableFindEmployeeByIdE(findEmployeeByIdEOK);
         else
             this.createTableE();
        JPanel employeesPanelB = new JPanel();
        employeesPanelB.setLayout(new BoxLayout(employeesPanelB, BoxLayout.Y_AXIS));
        employeesPanelB.add(aShowAllEmployeesBtn);
        employeesPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        employeesPanelB.add(aFindEmployeeByIdBtn);
        employeesPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        employeesPanelB.add(aInsertBtn);
        employeesPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        employeesPanelB.add(aDeleteBtn);
        employeesPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        employeesPanelB.add(aUpdateBtn);
        employeesPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        employeesPanelB.add(aGenerateReportBtn);
        employeesPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        employeesPanel.add(employeesPanelB);
        administratorPanel.add(employeesPanel);
    }

    public void initEmployee(){
        initC();
        initAc();
        employeePanel.addTab("Clients", clientsPanel);
        employeePanel.addTab("Accounts", accountsPanel);
        this.setContentPane(employeePanel);
    }

    public void initC(){
        clientsPanel = new JPanel();
        clientsPanel.setLayout(new FlowLayout());
        if (findClientByIdEOK != 0)
            this.createTableFindClientByIdE(findClientByIdEOK);
        else
            this.createTableC();
        JPanel clientsPanelB = new JPanel();
        clientsPanelB.setLayout(new BoxLayout(clientsPanelB, BoxLayout.Y_AXIS));
        clientsPanelB.add(eShowAllClientsBtn);
        clientsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        clientsPanelB.add(eFindClientByIdBtn);
        clientsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        clientsPanelB.add(eInsertBtn);
        clientsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        clientsPanelB.add(eUpdateBtn);
        clientsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        clientsPanel.add(clientsPanelB);
    }

    public void initAc(){
        accountsPanel = new JPanel();
        accountsPanel.setLayout(new FlowLayout());
        if (findAccountByIdEOK != 0)
            this.createTableFindAccountByIdE(findAccountByIdEOK);
        else
            this.createTableAc();
        JPanel accountsPanelB = new JPanel();
        accountsPanelB.setLayout(new BoxLayout(accountsPanelB, BoxLayout.Y_AXIS));
        accountsPanelB.add(acShowAllAccountsBtn);
        accountsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        accountsPanelB.add(acFindAccountByIdBtn);
        accountsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        accountsPanelB.add(acInsertBtn);
        accountsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        accountsPanelB.add(acDeleteBtn);
        accountsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        accountsPanelB.add(acUpdateBtn);
        accountsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        accountsPanelB.add(acTransferMoneyBetweenAccounts);
        accountsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        accountsPanelB.add(acPayUtilities);
        accountsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        accountsPanel.add(accountsPanelB);
    }

    public void loginSuccessful(String usernameText){
        clearLoginPane();
        String title = null;
        if (isAdmin == 1) {
            administratorPanel = new JPanel();
            administratorPanel.setLayout(new FlowLayout());
            this.initAdministrator();
            this.setContentPane(administratorPanel);
            title = "Bank system - Logged in as administrator: " + usernameText;
        } else if (isAdmin == 0){
            employeePanel = new JTabbedPane();
            this.initEmployee();
            title = "Bank system - Logged in as employee: " + usernameText;
        }
        this.pack();
        this.setSize(1100, 600);
        this.setTitle(title);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getUsername(){
        return username.getText();
    }

    public String getPassword(){
        char[] pass1 = password.getPassword();
        String pass2 = new String(pass1);
        return pass2;
    }

    public void createTableE(){
        ArrayList<User> employees;
        UserBLL userBLL = new UserBLL();
        employees = userBLL.viewAllEmployees();
        employeesTable = generateTable(employees);
        employeesTable.removeColumn(employeesTable.getColumnModel().getColumn(4));
        employeesTable.removeColumn(employeesTable.getColumnModel().getColumn(4));
        scrollEmployees = new JScrollPane(employeesTable);
        scrollEmployees.setPreferredSize(new Dimension(800, 500));
        employeesPanel.add(scrollEmployees);
    }

    public void createTableC(){
        ArrayList<Client> clients;
        ClientBLL clientBLL = new ClientBLL();
        clients = clientBLL.viewAll();
        clientsTable = generateTable(clients);
        scrollClients = new JScrollPane(clientsTable);
        scrollClients.setPreferredSize(new Dimension(800, 500));
        clientsPanel.add(scrollClients);
    }

    public void createTableAc() {
        ArrayList<Account> accounts;
        AccountBLL accountBLL = new AccountBLL();
        accounts = accountBLL.viewAll();
        accountsTable = generateTable(accounts);
        scrollAccounts = new JScrollPane(accountsTable);
        scrollClients.setPreferredSize(new Dimension(800, 500));
        accountsPanel.add(scrollAccounts);
    }

    public void createTableU(){
        ArrayList<User> users;
        UserBLL userBLL = new UserBLL();
        users = userBLL.viewAll();
        usersTable = generateTable(users);
        usersTable.removeColumn(usersTable.getColumnModel().getColumn(4));
        usersTable.removeColumn(usersTable.getColumnModel().getColumn(4));
        scrollUsers = new JScrollPane(usersTable);
        scrollUsers.setPreferredSize(new Dimension(800, 500));
        employeesPanel.add(scrollUsers);
    }

    public void createTableFindEmployeeByIdE(int id){
        ArrayList<User> employees;
        UserBLL userBLL = new UserBLL();
        employees = userBLL.findEmployeeByID(String.valueOf(id));
        employeesTable = generateTable(employees);
        employeesTable.removeColumn(employeesTable.getColumnModel().getColumn(3));
        employeesTable.removeColumn(employeesTable.getColumnModel().getColumn(4));
        scrollEmployees = new JScrollPane(employeesTable);
        scrollEmployees.setPreferredSize(new Dimension(800, 500));
        employeesPanel.add(scrollEmployees);
    }

    public void createTableFindClientByIdE(int id){
        ArrayList<Client> clients;
        ClientBLL clientBLL = new ClientBLL();
        clients = clientBLL.findClientByID(String.valueOf(id));
        clientsTable = generateTable(clients);
        scrollClients = new JScrollPane(clientsTable);
        scrollClients.setPreferredSize(new Dimension(800, 500));
        clientsPanel.add(scrollClients);
    }

    public void createTableFindAccountByIdE(int id){
        ArrayList<Account> accounts;
        AccountBLL accountBLL = new AccountBLL();
        accounts = accountBLL.findAccountByID(String.valueOf(id));
        accountsTable = generateTable(accounts);
        scrollAccounts = new JScrollPane(accountsTable);
        scrollAccounts.setPreferredSize(new Dimension(800, 500));
        accountsPanel.add(scrollAccounts);
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
                return !(getColumnName(column).equals("ID") || getColumnName(column).equals("IDCLIENT") || getColumnName(column).equals("DATEOFCREATION"));
            }
        };
        table.setModel(tableModel);
        return table;
    }

    JTable getJTable(String o) {
        if (o.equals("employees"))
            return employeesTable;
        else if (o.equals("clients"))
            return clientsTable;
        else if (o.equals("accounts"))
            return accountsTable;
        else return null;
    }


    public void setFindEmployeeByIdE(int id){
        findEmployeeByIdEOK = id;
    }

    public void setFalseFindEmployeeByIdE(){
        findEmployeeByIdEOK = 0;
    }

    public void setFindClientByIdE(int id){
        findClientByIdEOK = id;
    }

    public void setFalseFindClientByIdE(){
        findClientByIdEOK = 0;
    }

    public void setFindAccountByIdE(int id){
        findAccountByIdEOK = id;
    }

    public void setFalseFindAccountByIdE(){
        findAccountByIdEOK = 0;
    }

    public void setAdminUser(){
        isAdmin = 1;
    }

    public void setRegUser(){ isAdmin = 0; }

    public JPanel getLoginPanel(){
        return loginPanel;
    }

    public JPanel getAdministratorPanel(){
        return administratorPanel;
    }

    public JTabbedPane getEmployeePanel(){
        return employeePanel;
    }

    void showError(String errMessage){
        JOptionPane.showMessageDialog(this, errMessage);
    }

    void addLoginListener(ActionListener al){
        loginButton.addActionListener(al);
    }
    void addShowAllEmployeesListenerA(ActionListener al) {
        aShowAllEmployeesBtn.addActionListener(al);
    }
    void addFindEmployeeByIdListenerA(ActionListener al) {
        aFindEmployeeByIdBtn.addActionListener(al);
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
    void addGenerateReportListenerA(ActionListener al) {
        aGenerateReportBtn.addActionListener(al);
    }
    void addShowAllClientsListenerE(ActionListener al) {
        eShowAllClientsBtn.addActionListener(al);
    }
    void addFindClientByIdListenerE(ActionListener al) {
        eFindClientByIdBtn.addActionListener(al);
    }
    void addInsertListenerE(ActionListener al) {
        eInsertBtn.addActionListener(al);
    }
    void addUpdateListenerE(ActionListener al) {
        eUpdateBtn.addActionListener(al);
    }
    void addShowAllAccountsListenerAc(ActionListener al) {
        acShowAllAccountsBtn.addActionListener(al);
    }
    void addFindAccountByIdListenerAc(ActionListener al) {
        acFindAccountByIdBtn.addActionListener(al);
    }
    void addInsertListenerAc(ActionListener al) {
        acInsertBtn.addActionListener(al);
    }
    void addDeleteListenerAc(ActionListener al) {
        acDeleteBtn.addActionListener(al);
    }
    void addUpdateListenerAc(ActionListener al) {
        acUpdateBtn.addActionListener(al);
    }
    void addTransferListenerAc(ActionListener al) { acTransferMoneyBetweenAccounts.addActionListener(al);}
    void addPayUtilityListenerAc(ActionListener al) { acPayUtilities.addActionListener(al);}
}
