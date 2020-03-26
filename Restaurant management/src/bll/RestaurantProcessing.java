package bll;

import java.util.ArrayList;
import java.util.List;

public interface RestaurantProcessing {
	public void add(MenuItem item);
	public void remove(String name);
	public void edit(String name, double newPrice);
	public void createOrder(Order order, ArrayList<String> newProducts);
	public double computePrice(Order order);
	public List<String> generateBillContent(Order order);
}
