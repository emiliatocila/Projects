package bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import dataLayer.FileWriter;

public class Restaurant extends Observable implements RestaurantProcessing {
	public static ArrayList<MenuItem> items;
	public static HashMap<Order, ArrayList<MenuItem>> itemsOfOrders;

	public Restaurant() {
		items = new ArrayList<MenuItem>();
		itemsOfOrders = new HashMap<Order, ArrayList<MenuItem>>();
	}

	public ArrayList<MenuItem> copyMenuItem(){
		ArrayList<MenuItem> newItems = new ArrayList<MenuItem>();
		for (MenuItem item : items)
			newItems.add(item);
		return newItems;
	}

	public boolean wellFormed() {
		if(items == null) return false;
		if(items.size() < 3 || items.size() > 150) return false;
		if(itemsOfOrders.keySet().size() > 50) return false;
		for(ArrayList<MenuItem> itemsOfEachOrder : itemsOfOrders.values())
			for(MenuItem item : itemsOfEachOrder)
				if(item.getPrice() <= 0) return false;
		return true;
	}

	public void add(MenuItem item) {
		assert wellFormed() : "Restaurant is not well formed!";
		if(item.getPrice() > 0)
			items.add(item);
		FileWriter.serializationArrayList();
		assert wellFormed() : "Restaurant is not well formed!";
	}

	public void remove(String name) {
		assert wellFormed() : "Restaurant is not well formed!";
		if(items.size() >= 4) {
			ArrayList<MenuItem> oldItems = this.copyMenuItem();
			for(MenuItem itemToRemove : oldItems) {
				if(itemToRemove.getName().equals(name)) 
					items.remove(itemToRemove);
			}
		}
		FileWriter.serializationArrayList();
		assert wellFormed() : "Restaurant is not well formed!";
	}

	public void edit(String name, double newPrice) {
		assert wellFormed() : "Restaurant is not well formed!";
		if(newPrice > 0) {
			for(MenuItem itemToEdit : items)
				if(itemToEdit.getName().equals(name))
					itemToEdit.setPrice(newPrice);
		}
		FileWriter.serializationArrayList();
		assert wellFormed() : "Restaurant is not well formed!";
	}

	public void createOrder(Order order, ArrayList<String> newProducts) {
		assert wellFormed() : "Restaurant is not well formed!";
		ArrayList<MenuItem> products = new ArrayList<MenuItem>();
		for(MenuItem item : items)
			for(String productName : newProducts)
				if(item.getName().equals(productName))
					products.add(item);
		itemsOfOrders.put(order, products);
		FileWriter.serializationHashMap();
		setChanged();
		notifyObservers(order);
		assert wellFormed() : "Restaurant is not well formed!";
	}

	public double computePrice(Order order) {
		double finalPrice = 0;
		ArrayList<MenuItem> itemsOfAnOrder = itemsOfOrders.get(order);
		for(MenuItem item : itemsOfAnOrder) 
			finalPrice += item.getPrice();
		return finalPrice;
	}

	public List<String> generateBillContent(Order order) {
		List<String> lines = new ArrayList<String>();
		lines.add("BON FISCAL");
		lines.add(order.getDate());
		ArrayList<MenuItem> itemsOfAnOrder = itemsOfOrders.get(order);
		for(MenuItem item : itemsOfAnOrder) {
			lines.add(item.getName() + "..................€" + item.getPrice());
		}
		lines.add("TOTAL..................€" + computePrice(order));
		itemsOfOrders.remove(order);
		FileWriter.serializationHashMap();
		return lines;
	}
	
}
