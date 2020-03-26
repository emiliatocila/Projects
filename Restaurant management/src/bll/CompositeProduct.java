package bll;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CompositeProduct implements MenuItem, Serializable {
	private String name;
	private double price;
	private ArrayList<MenuItem> baseProducts;
	
	public CompositeProduct(String name, ArrayList<MenuItem> baseProducts) {
		this.name = name;
		this.baseProducts = new ArrayList<MenuItem>();
		this.baseProducts = baseProducts;
		computePrice();
	}
	
	public void computePrice() {
		for(MenuItem baseProduct : baseProducts)
			this.price += baseProduct.getPrice();
	}

	public void add(MenuItem baseProduct) {
		baseProducts.add(baseProduct);
	}

	public void remove(MenuItem baseProduct) {
		baseProducts.remove(baseProduct);
	}

	public String getName() {
		return this.name;
	}

	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double newPrice) {
		this.price = newPrice;
	}
}
