package main.java.bll;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import main.java.repository.dao.ClientDAO;
import main.java.model.Client;

public class ClientBLL {

    public ClientBLL() {
    }

    public ArrayList<Client> findClientByID(String idS) {
        if (Pattern.matches("[0-9]+", idS)) {
            int id = Integer.parseInt(idS);
            ArrayList<Client> c = new ArrayList<Client>();
            ClientDAO clientDAO = new ClientDAO();
            if (clientDAO.findClientById(id) == null)
                return null;
            else
                c.add(clientDAO.findClientById(id));
            return c;
        } else {
            return null;
        }
    }

    public ArrayList<Client> viewAll() {
        ClientDAO clientDAO = new ClientDAO();
        ArrayList<Client> clients = clientDAO.viewAll();
        if (clients == null) {
            return null;
        }
        return clients;
    }
    public String insert(String firstName, String lastName, String idCardNr, String cnp, String address, int idLoggedEmployee) {
        ClientDAO clientDAO = new ClientDAO();
        if (Pattern.matches("[a-zA-Z]+", firstName)) {
            if (Pattern.matches("[a-zA-Z]+", lastName)) {
                if (Pattern.matches("[a-zA-Z0-9]+", idCardNr)) {
                    if (Pattern.matches("[0-9]+", cnp)) {
                        if (address.length() > 2) {
                            int minPossibleNewId = 0;
                            int i = 0;
                            int ok = 0;
                            for (Client client : clientDAO.viewAll()) {
                                i++;
                                if (i != client.getId() && ok == 0) {
                                    minPossibleNewId = i;
                                    ok = 1;
                                }
                            }
                            if (minPossibleNewId == 0)
                                minPossibleNewId = ++i;
                            Client c = new Client(firstName, lastName, idCardNr, cnp, address, minPossibleNewId);
                            if (clientDAO.insert(c) == -1)
                                return "Cannot create client!";
                            else {
                                ActivityBLL activityBll = new ActivityBLL();
                                ZoneId defaultZoneId = ZoneId.systemDefault();
                                LocalDate crtDate = LocalDate.now();
                                Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String dayTodayStr = df.format(dateToday);
                                activityBll.insert(idLoggedEmployee, "ADD NEW CLIENT", firstName + " " + lastName, dayTodayStr);
                                return "Client created successfully!";
                            }
                        } else return "Address invalid!";
                    } else return "CNP invalid!";
                } else return "ID card number invalid!";
            } else return "Last name invalid!";
        } else return "First name invalid!";
    }

    public String delete(Client c) {
        ClientDAO clientDAO = new ClientDAO();
        if (clientDAO.delete(c.getId()) == -1)
            return "Cannot delete client!";
        else
            return "Client deleted successfully!";
    }

    public String update(int id, String firstName, String lastName, String idCardNr, String cnp, String address, int idLoggedEmployee) {
        if (Pattern.matches("[a-zA-Z]+", firstName)) {
            if (Pattern.matches("[a-zA-Z]+", lastName)) {
                if (Pattern.matches("[a-zA-Z0-9]+", idCardNr)) {
                    if (Pattern.matches("[0-9]+", cnp)) {
                        if (address.length() > 2) {
                            ClientDAO clientDAO = new ClientDAO();
                            Client c = new Client(firstName, lastName, idCardNr, cnp, address, id);
                            if (clientDAO.update(c, id) == -1)
                                return "Cannot update client!";
                            else {
                                ActivityBLL activityBll = new ActivityBLL();
                                ZoneId defaultZoneId = ZoneId.systemDefault();
                                LocalDate crtDate = LocalDate.now();
                                Date dateToday = Date.from(crtDate.atStartOfDay(defaultZoneId).toInstant());
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String dayTodayStr = df.format(dateToday);
                                activityBll.insert(idLoggedEmployee, "UPDATE CLIENT", firstName + " " + lastName, dayTodayStr);
                                return "Client updated successfully!";
                            }
                        } else return "Address invalid!";
                    } else return "CNP invalid!";
                } else return "ID card number invalid!";
            } else return "Last name invalid!";
        } else return "First name invalid!";
    }
}
