package bll;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import dao.ProductDAO;
import model.Product;

public class ProductBLL {

	public ProductBLL() {	
	}

	public ArrayList<Product> findProductById(int id) {
		ArrayList<Product> p = new ArrayList<Product>();
		ProductDAO productDAO = new ProductDAO();
		if (productDAO.findById("id", id) == null)
			return null;
		p.add(productDAO.findById("id", id));
		return p; 
	}

	public ArrayList<Product> viewAll() {
		ProductDAO productDAO = new ProductDAO();
		ArrayList<Product> products = productDAO.viewAll();
		if (products == null) {
			return null;
		}
		return products;
	}

	public String insert(Product p) {
		ProductDAO productDAO = new ProductDAO();
		int minPossibleNewId = 0;
		int i = 0;
		int ok = 0;
		for (Product product : productDAO.viewAll()) {
			i++;
			if (i != product.getId() && ok == 0) {
				minPossibleNewId = i;
				ok = 1;
			}
		}
		System.out.println(minPossibleNewId);
		if (minPossibleNewId == 0)
			minPossibleNewId = ++i;
		p.setId(minPossibleNewId);
		if (productDAO.insert(p) == -1)
			return "Inserarea produsului a esuat!";
		else
			return "Produsul a fost inserat cu succes!";
	}

	public String delete(Product p) {
		ProductDAO productDAO = new ProductDAO();
		if (productDAO.delete(p.getId()) == -1)
			return "Stergerea produsului a esuat!";
		else
			return "Produsul a fost sters cu succes!";
	}

	public String update(Product p) {
		ProductDAO productDAO = new ProductDAO();
		if (productDAO.update(p, p.getId()) == -1)
			return "Actualizarea produsului a esuat!";
		else
			return "Produsul a fost actualizat cu succes!";
	}

}

