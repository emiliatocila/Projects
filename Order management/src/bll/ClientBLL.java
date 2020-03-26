package bll;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {

	public ClientBLL() {	
	}

	public ArrayList<Client> findClientById(int id) {
		ArrayList<Client> c = new ArrayList<Client>();
		ClientDAO clientDAO = new ClientDAO();
		if (clientDAO.findById("id", id) == null)
			return null;
		else 
			c.add(clientDAO.findById("id", id));
		return c;
	}

	public ArrayList<Client> viewAll() {
		ClientDAO clientDAO = new ClientDAO();
		ArrayList<Client> clients = clientDAO.viewAll();
		if (clients == null) {
			return null;
		}
		return clients;
	}

	public String insert(Client c) {
		ClientDAO clientDAO = new ClientDAO();
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
		c.setId(minPossibleNewId);
		if (clientDAO.insert(c) == -1)
			return "Inserarea clientului a esuat!";
		else
			return "Clientul a fost inserat cu succes!";
	}

	public String delete(Client c) { 
		ClientDAO clientDAO = new ClientDAO();
		if (clientDAO.delete(c.getId()) == -1)
			return "Stergerea clientului a esuat!";
		else
			return "Clientul a fost sters cu succes!";
	}

	public String update(Client c) {
		ClientDAO clientDAO = new ClientDAO();
		if (clientDAO.update(c, c.getId()) == -1)
			return "Actualizarea clientului a esuat!";
		else
			return "Clientul a fost actualizat cu succes!";
	}

}