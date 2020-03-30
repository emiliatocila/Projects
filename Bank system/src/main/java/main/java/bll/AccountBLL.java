package main.java.bll;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import main.java.model.Client;
import main.java.repository.dao.AccountDAO;
import main.java.model.Account;

public class AccountBLL {

    public AccountBLL() {
    }

    public ArrayList<Account> findAccountByID(String idS) {
        if (Pattern.matches("[0-9]+", idS)) {
            int id = Integer.parseInt(idS);
            ArrayList<Account> a = new ArrayList<Account>();
            AccountDAO accountDAO = new AccountDAO();
            if (accountDAO.findById("id", id) == null)
                return null;
            else
                a.add(accountDAO.findById("id", id));
            return a;
        } else {
            return null;
        }
    }

    public ArrayList<Account> viewAll() {
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = accountDAO.viewAll();
        if (accounts == null) {
            return null;
        }
        return accounts;
    }

    public String insert(int idSelectedClient, String type, String amountS, String selectedDateStr, int idLoggedEmployee) {
        if (Pattern.matches("[0-9]+", amountS)) {
            int amount = Integer.parseInt(amountS);
            AccountDAO accountDAO = new AccountDAO();
            int minPossibleNewId = 0;
            int i = 0;
            int ok = 0;
            for (Account account : accountDAO.viewAll()) {
                i++;
                if (i != account.getId() && ok == 0) {
                    minPossibleNewId = i;
                    ok = 1;
                }
            }
            if (minPossibleNewId == 0)
                minPossibleNewId = ++i;
            Account a = new Account(minPossibleNewId, idSelectedClient, type, amount, selectedDateStr);
            if (accountDAO.insert(a) == -1)
                return "Cannot create account!";
            else {
                ActivityBLL activityBll = new ActivityBLL();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                LocalDate crtDate = LocalDate.now();
                Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dayTodayStr = df.format(dateToday);
                activityBll.insert(idLoggedEmployee, "ADD NEW ACCOUNT", "Account with ID " + minPossibleNewId + " for client with ID " + idSelectedClient, dayTodayStr);
                return "Account created successfully!";
            }
        } else return "Amount invalid!";
    }

    public String delete(int id, int idLoggedEmployee) {
        AccountDAO accountDAO = new AccountDAO();
        if (accountDAO.delete(id) == -1)
            return "Cannot delete account!";
        else {
            ActivityBLL activityBll = new ActivityBLL();
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate crtDate = LocalDate.now();
            Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dayTodayStr = df.format(dateToday);
            activityBll.insert(idLoggedEmployee, "DELETE ACCOUNT", "Account with ID " + id, dayTodayStr);
            return "Account deleted successfully!";
        }
    }

    public String update(int id, int idClient, String type, String amountS, String dateOdCreation, int idLoggedEmployee) {
        if (type.equals("credit") || type.equals("debit")) {
            if (Pattern.matches("[0-9]+", amountS)) {
                int amount = Integer.parseInt(amountS);
                AccountDAO accountDAO = new AccountDAO();
                Account a = new Account(id, idClient, type, amount, dateOdCreation);
                if (accountDAO.update(a, id) == -1)
                    return "Cannot update account!";
                else {
                    ActivityBLL activityBll = new ActivityBLL();
                    ZoneId defaultZoneId = ZoneId.systemDefault();
                    LocalDate crtDate = LocalDate.now();
                    Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dayTodayStr = df.format(dateToday);
                    activityBll.insert(idLoggedEmployee, "UPDATE ACCOUNT", "Account with ID " + id, dayTodayStr);
                    return "Account updated successfully!";
                }
            } else return "Amount invalid!";
        } else return "Type invalid!";
    }
    public void update(Account a) {
        AccountDAO accountDAO = new AccountDAO();
        if (accountDAO.update(a, a.getId()) == -1)
            return;
    }

    public String transferMoney(String idFrom, String idTo, String amountS, int idLoggedEmployee) {
        if (!idFrom.equals(idTo)) {
            if (findAccountByID(idFrom) != null) {
                if (findAccountByID(idTo) != null) {
                    Account acFrom = findAccountByID(idFrom).get(0);
                    Account acTo = findAccountByID(idTo).get(0);
                    if (Pattern.matches("[0-9]+", amountS)) {
                        int sum = Integer.parseInt(amountS);
                        if (sum <= acFrom.getAmount()) {
                            acFrom.setAmount(acFrom.getAmount() - sum);
                            acTo.setAmount(acTo.getAmount() + sum);
                            update(acFrom);
                            update(acTo);
                            ActivityBLL activityBll = new ActivityBLL();
                            ZoneId defaultZoneId = ZoneId.systemDefault();
                            LocalDate crtDate = LocalDate.now();
                            Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dayTodayStr = df.format(dateToday);
                            activityBll.insert(idLoggedEmployee, "TRANSFER MONEY BETWEEN ACCOUNTS", "From account with ID " + acFrom.getId() + " to account with ID " + acTo.getId(), dayTodayStr);
                            return "Money successfully transferred!";
                        } else return "Insufficient funds!";
                    } else return "Amount invalid!";
                } else return "The ID of the receiving account is invalid!";
            } else return "The ID of the transferring account is invalid!";
        } else return "Cannot transfer money to the same account!";
    }

    public String payUtilityBill(String idAccount, String idClient, String amountS, String utility, String strDate, int idLoggedEmployee) {
        ClientBLL clientBLL = new ClientBLL();
        Account ac = findAccountByID(idAccount).get(0);
        Client c = clientBLL.findClientByID(idClient).get(0);
        if (Pattern.matches("[0-9]+", amountS)) {
            int sum = Integer.parseInt(amountS);
            if (sum <= ac.getAmount()) {
                ac.setAmount(ac.getAmount() - sum);
                List<String> lines = ac.generateBillContent(utility, sum, strDate, c.getFirstName(), c.getLastName());
                Path file = Paths.get("Payments/PaymentBill_" + utility + "_" + c.getFirstName() + "_" + c.getLastName() + "_AccountID" + idAccount + "_" + strDate.substring(0, 10) + ".txt");
                try {
                    Files.write(file, lines, Charset.forName("UTF-8"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                update(ac);
                ActivityBLL activityBll = new ActivityBLL();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                LocalDate crtDate = LocalDate.now();
                Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dayTodayStr = df.format(dateToday);
                activityBll.insert(idLoggedEmployee, "PAY UTILITY BILL - " + utility, "Client " + c.getFirstName() + " " + c.getLastName() + " with account ID " + ac.getId(), dayTodayStr);
                return "Utility bill payment successful!";
            } else return "Insufficient funds!";
        } else return "Amount invalid!";
    }

}
