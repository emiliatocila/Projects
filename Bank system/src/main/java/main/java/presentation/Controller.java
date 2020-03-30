package main.java.presentation;

import main.java.bll.*;
import main.java.model.*;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class Controller {
    private View view;
    private int idLoggedEmployee;

    public Controller(View view) {
        this.view = view;
        view.addLoginListener(new loginListener());

        view.addShowAllEmployeesListenerA(new showAllEmployeesListenerA());
        view.addFindEmployeeByIdListenerA(new findEmployeeByIdListenerA());
        view.addInsertListenerA(new insertListenerA());
        view.addDeleteListenerA(new deleteListenerA());
        view.addUpdateListenerA(new updateListenerA());
        view.addGenerateReportListenerA(new generateReportListenerA());

        view.addShowAllClientsListenerE(new showAllClientsListenerE());
        view.addFindClientByIdListenerE(new findClientByIdListenerE());
        view.addInsertListenerE(new insertListenerE());
        view.addUpdateListenerE(new updateListenerE());

        view.addShowAllAccountsListenerAc(new showAllAccountsListenerAc());
        view.addFindAccountByIdListenerAc(new findAccountByIdListenerAc());
        view.addInsertListenerAc(new insertListenerAc());
        view.addDeleteListenerAc(new deleteListenerAc());
        view.addUpdateListenerAc(new updateListenerAc());
        view.addTransferListenerAc(new transferListenerAc());
        view.addPayUtilityListenerAc(new payUtilityBillListenerAc());
    }

    class loginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LoginBLL loginBLL = new LoginBLL();
            User u = null;
            String username = view.getUsername();
            String password = view.getPassword();
            int isAdmin = 0;
            if (loginBLL.findUserByUsername(username) != null) {
                u = loginBLL.findUserByUsername(username).get(0);
                idLoggedEmployee = u.getId();
                isAdmin = u.getIsAdmin();
            }
            if (u != null) {
                if (!password.equals(u.getPassword()))
                    JOptionPane.showMessageDialog(view.getLoginPanel(), "Password incorrect!", "Try again", JOptionPane.PLAIN_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(view.getLoginPanel(), "Login successful!", "Welcome!", JOptionPane.PLAIN_MESSAGE);
                    if (isAdmin == 1) view.setAdminUser();
                    else view.setRegUser();
                    view.loginSuccessful(username);
                }
            } else
                JOptionPane.showMessageDialog(view.getLoginPanel(), "Username does not exist!", "Try again", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class showAllEmployeesListenerA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.clearAdministratorPane();
            view.initAdministrator();
        }
    }

    class showAllClientsListenerE implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.clearEmployeePane();
            view.initEmployee();
        }
    }

    class showAllAccountsListenerAc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.clearEmployeePane();
            view.initEmployee();
        }
    }

    class findEmployeeByIdListenerA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String id = (String) JOptionPane.showInputDialog(view.getAdministratorPanel(), "Search by ID:", "Find by ID", JOptionPane.PLAIN_MESSAGE);
            if (id != null && id.length() > 0) {
                UserBLL userBll = new UserBLL();
                if (userBll.findEmployeeByID(id) != null) {
                    view.setFindEmployeeByIdE(Integer.parseInt(id));
                    view.clearAdministratorPane();
                    view.initAdministrator();
                    view.setFalseFindEmployeeByIdE();
                } else
                    JOptionPane.showMessageDialog(view.getAdministratorPanel(), "There is no employee with the selected ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
            } else
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "ID field is blank!", "Try again", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class findClientByIdListenerE implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String id = (String) JOptionPane.showInputDialog(view.getAdministratorPanel(), "Search by ID:", "Find by ID", JOptionPane.PLAIN_MESSAGE);
            if (id != null && id.length() > 0) {
                ClientBLL clientBll = new ClientBLL();
                if (clientBll.findClientByID(id) != null) {
                    view.setFindClientByIdE(Integer.parseInt(id));
                    view.clearEmployeePane();
                    view.initEmployee();
                    view.setFalseFindClientByIdE();
                } else
                    JOptionPane.showMessageDialog(view.getAdministratorPanel(), "There is no client with the selected ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "ID field is blank!", "Try again", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    class findAccountByIdListenerAc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String id = (String) JOptionPane.showInputDialog(view.getAdministratorPanel(), "Search by ID:", "Find by ID", JOptionPane.PLAIN_MESSAGE);
            if (id != null && id.length() > 0) {
                AccountBLL accountBll = new AccountBLL();
                if (accountBll.findAccountByID(id) != null) {
                    view.setFindAccountByIdE(Integer.parseInt(id));
                    view.clearEmployeePane();
                    view.initEmployee();
                    view.setFalseFindAccountByIdE();
                } else
                    JOptionPane.showMessageDialog(view.getAdministratorPanel(), "There is no account with the selected ID!", "Try again", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "ID field is blank!", "Try again", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    class insertListenerA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField firstName = new JTextField(20);
            JTextField lastName = new JTextField(20);
            JTextField username = new JTextField(20);
            JTextField password = new JTextField(20);
            Object[] message = {"First Name: ", firstName, "Last Name: ", lastName, "Username: ", username, "Password: ", password};
            int option = JOptionPane.showConfirmDialog(view.getAdministratorPanel(), message, "Add a new employee", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                UserBLL userBll = new UserBLL();
                String mess = userBll.insert(firstName.getText(), lastName.getText(), username.getText(), password.getText(), 0);
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearAdministratorPane();
                view.initAdministrator();
            }
        }
    }

    class insertListenerE implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField firstName = new JTextField(20);
            JTextField lastName = new JTextField(20);
            JTextField idCardNr = new JTextField(20);
            JTextField cnp = new JTextField(20);
            JTextField address = new JTextField(20);
            Object[] message = {"First Name: ", firstName, "Last Name: ", lastName, "Identity card number: ", idCardNr, "CNP: ", cnp, "Address: ", address};
            int option = JOptionPane.showConfirmDialog(view.getAdministratorPanel(), message, "Add a new client", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                ClientBLL clientBll = new ClientBLL();
                String mess = clientBll.insert(firstName.getText(), lastName.getText(), idCardNr.getText(), cnp.getText(), address.getText(), idLoggedEmployee);
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearEmployeePane();
                view.initEmployee();
            }
        }
    }

    class insertListenerAc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int idSelectedClient = 0;
            JTable table = view.getJTable("clients");
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
            }
            if ((table.isRowSelected(table.getSelectedRow())))
                idSelectedClient = Integer.parseInt(table.getValueAt(row, 0).toString());
            String[] typesOfCards = {"credit", "debit"};
            JComboBox type = new JComboBox(typesOfCards);
            type.setSelectedIndex(0);
            JTextField amount = new JTextField(20);
            UtilDateModel model = new UtilDateModel();
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate crtDate = LocalDate.now();
            model.setDate(crtDate.getYear(), crtDate.getMonthValue(), crtDate.getDayOfMonth());
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.Year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
            Object[] message = {"Type of card: ", type, "Amount of money: ", amount, "Date of creation: ", datePicker};
            if (idSelectedClient == 0)
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select a client to create an account for!", "Try again", JOptionPane.PLAIN_MESSAGE);
            else {
                int option = JOptionPane.showConfirmDialog(view.getAdministratorPanel(), message, "Add a new client", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Date selectedDate = (Date) datePicker.getModel().getValue();
                    if (selectedDate == null) {
                        JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Please select a date!", "Try again", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        selectedDate.setHours(0);
                        selectedDate.setMinutes(0);
                        selectedDate.setSeconds(0);
                        Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                        if (selectedDate.after(dateToday) && !(selectedDate.toString().equals(dateToday.toString()))) {
                            JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Cannot select a date from the future!", "Try again", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String selectedDateStr = df.format(selectedDate);
                            AccountBLL accountBll = new AccountBLL();
                            String mess = accountBll.insert(idSelectedClient, (String) type.getSelectedItem(), amount.getText(), selectedDateStr, idLoggedEmployee);
                            JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                            view.clearEmployeePane();
                            view.initEmployee();
                        }
                    }
                }
            }
        }
    }

    class deleteListenerA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            UserBLL userBll = new UserBLL();
            JTable table = view.getJTable("employees");
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                String mess = userBll.delete((int)table.getValueAt(row, 0));
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearAdministratorPane();
                view.initAdministrator();
            } else
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select an employee to be deleted!", "Try again", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class deleteListenerAc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AccountBLL accountBll = new AccountBLL();
            JTable table = view.getJTable("accounts");
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                String mess = accountBll.delete((int) table.getValueAt(row, 0), idLoggedEmployee);
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearEmployeePane();
                view.initEmployee();
            } else
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select an account to be deleted!", "Try again", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class updateListenerA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            UserBLL userBll = new UserBLL();
            JTable table = view.getJTable("employees");
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                String mess = userBll.update(Integer.parseInt(table.getValueAt(row, 0).toString()), table.getValueAt(row, 1).toString(), table.getValueAt(row, 2).toString(),  table.getValueAt(row, 3).toString());
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearAdministratorPane();
                view.initAdministrator();
            } else
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select an employee to be updated!", "Try again", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class updateListenerE implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ClientBLL clientBll = new ClientBLL();
            JTable table = view.getJTable("clients");
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                String mess = clientBll.update(Integer.parseInt(table.getValueAt(row, 0).toString()), table.getValueAt(row, 1).toString(), table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(), table.getValueAt(row, 4).toString(), table.getValueAt(row, 5).toString(), idLoggedEmployee);
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearEmployeePane();
                view.initEmployee();
            } else
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select a client to be updated!", "Try again", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class updateListenerAc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AccountBLL accountBll = new AccountBLL();
            JTable table = view.getJTable("accounts");
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                String mess = accountBll.update(Integer.parseInt(table.getValueAt(row, 0).toString()), Integer.parseInt(table.getValueAt(row, 1).toString()), table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(), table.getValueAt(row, 4).toString(), idLoggedEmployee);
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearEmployeePane();
                view.initEmployee();
            } else
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select an account to be updated!", "Try again", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class transferListenerAc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AccountBLL accountBll = new AccountBLL();
            JTextField transferFrom = new JTextField(20);
            JTextField transferTo = new JTextField(20);
            JTextField amount = new JTextField(20);
            Object[] message = {"Transfer from account with ID: ", transferFrom, "Transfer to account with ID: ", transferTo, "Amount to transfer: ", amount};
            int option = JOptionPane.showConfirmDialog(view.getAdministratorPanel(), message, "Transfer money between accounts", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String idFrom = transferFrom.getText();
                String idTo = transferTo.getText();
                String sum = amount.getText();
                String mess = accountBll.transferMoney(idFrom, idTo, sum, idLoggedEmployee);
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                view.clearEmployeePane();
                view.initEmployee();
            }
        }
    }

    class payUtilityBillListenerAc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AccountBLL accountBll = new AccountBLL();
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate crtDate = LocalDate.now();
            Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(dateToday);
            JTextField utility = new JTextField(20);
            JTextField amount = new JTextField(20);
            JTable table = view.getJTable("accounts");
            String idAccount = null;
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                idAccount = table.getValueAt(row, 0).toString();
            }
            if (idAccount == null) {
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select an account to pay the utility bill!", "Try again", JOptionPane.PLAIN_MESSAGE);
            } else {
                String idClient = table.getValueAt(row, 1).toString();
                Object[] message = {"Utility: ", utility, "Amount: ", amount};
                int option = JOptionPane.showConfirmDialog(view.getAdministratorPanel(), message, "Pay utility bill", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String sum = amount.getText();
                    String utilityS = utility.getText();
                    String mess = accountBll.payUtilityBill(idAccount, idClient, sum, utilityS, strDate, idLoggedEmployee);
                    JOptionPane.showConfirmDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                    view.clearEmployeePane();
                    view.initEmployee();
                }
            }
        }
    }

    class generateReportListenerA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            UserBLL userBll = new UserBLL();
            JTable table = view.getJTable("employees");
            String idEmployee = null;
            int row = table.getSelectedRow();
            if (table.isRowSelected(table.getSelectedRow())) {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                idEmployee = table.getValueAt(row, 0).toString();
            }
            if (idEmployee == null) {
                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select an employee to generate a report for!", "Try again", JOptionPane.PLAIN_MESSAGE);
            } else {
                UtilDateModel modelFrom = new UtilDateModel();
                UtilDateModel modelTo = new UtilDateModel();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                LocalDate crtDate = LocalDate.now();
                modelFrom.setDate(crtDate.getYear(), crtDate.getMonthValue(), crtDate.getDayOfMonth());
                modelTo.setDate(crtDate.getYear(), crtDate.getMonthValue(), crtDate.getDayOfMonth());
                Properties pFrom = new Properties();
                Properties pTo = new Properties();
                pFrom.put("text.today", "Today");
                pFrom.put("text.month", "Month");
                pFrom.put("text.Year", "Year");
                pTo.put("text.today", "Today");
                pTo.put("text.month", "Month");
                pTo.put("text.Year", "Year");
                JDatePanelImpl dateFromPanel = new JDatePanelImpl(modelFrom, pFrom);
                JDatePanelImpl dateToPanel = new JDatePanelImpl(modelTo, pTo);
                JDatePicker datePickerFrom = new JDatePickerImpl(dateFromPanel, new DateComponentFormatter());
                JDatePicker datePickerTo = new JDatePickerImpl(dateToPanel, new DateComponentFormatter());
                Object[] message = {"From date: ", datePickerFrom, "To date: ", datePickerTo};
                int option = JOptionPane.showConfirmDialog(view.getAdministratorPanel(), message, "Generate report for employee", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Date selectedDateFrom = (Date) datePickerFrom.getModel().getValue();
                    Date selectedDateTo = (Date) datePickerTo.getModel().getValue();
                    if (selectedDateFrom == null) {
                        JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Please select a date!", "Try again", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        if (selectedDateTo == null) {
                            JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Please select a date!", "Try again", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                            selectedDateFrom.setHours(0);
                            selectedDateFrom.setMinutes(0);
                            selectedDateFrom.setSeconds(0);
                            dateToday.setHours(12);
                            dateToday.setMinutes(0);
                            dateToday.setSeconds(0);
                            selectedDateTo.setHours(23);
                            selectedDateTo.setMinutes(59);
                            selectedDateTo.setSeconds(59);
                            if (selectedDateFrom.after(dateToday) || selectedDateFrom.after(selectedDateTo)) {
                                JOptionPane.showMessageDialog(view.getAdministratorPanel(), "Select a correct interval!", "Try again", JOptionPane.PLAIN_MESSAGE);
                            } else {
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String selectedDateFromStr = df.format(selectedDateFrom);
                                String selectedDateToStr = df.format(selectedDateTo);
                                String mess = userBll.generateReport(idEmployee, selectedDateFromStr, selectedDateToStr);
                                JOptionPane.showMessageDialog(view.getAdministratorPanel(), mess, null, JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }
                }
            }
        }
    }
}