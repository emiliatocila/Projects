package bll;

import java.util.ArrayList;
import dao.*;
import model.*;

public class OrderBLL {
	
	public OrderBLL() {	
	} 
	
	public ArrayList<Order> findOrderById(int id) {
		ArrayList<Order> o = new ArrayList<Order>();
		OrderDAO orderDAO = new OrderDAO();
		if (orderDAO.findById("orderid", id) == null)
			return null;
		o.add(orderDAO.findById("orderid", id));
		return o;
	}
	
	public ArrayList<Order> viewAll() {
		OrderDAO orderDAO = new OrderDAO();
		ArrayList<Order> orders = orderDAO.viewAll();
		if (orders == null) {
			return null;
		}
		return orders;
	}
	
	public int insert(Order o) {
		OrderDAO orderDAO = new OrderDAO();
		ProductDAO productDAO = new ProductDAO();
		ProductBLL productBll = new ProductBLL();
		ClientDAO clientDAO = new ClientDAO();
		ClientBLL clientBll = new ClientBLL();
		int minPossibleNewId = 0;
		int i = 0;
		int ok = 0;
		for (Order order : orderDAO.viewAll()) {
			i++;
			if (i != order.getOrderId() && ok == 0) {
				minPossibleNewId = i;
				ok = 1;
			}
		}
		if (minPossibleNewId == 0)
			minPossibleNewId = ++i;
		o.setOrderId(minPossibleNewId);
		if (productDAO.findById("id", o.getProductId()) == null)
			return -2;
		if (clientDAO.findById("id", o.getClientId()) == null)
			return -3;
		Product p = productBll.findProductById(o.getProductId()).get(0);
		Client c = clientBll.findClientById(o.getClientId()).get(0);
		int productStock = p.getStock();
		if (productStock < o.getQuantity())
			return -1;
		else {
			orderDAO.insert(o);
			p.setStock(productStock - o.getQuantity());
			productBll.update(p);
			return 0;
		}
	}
}
