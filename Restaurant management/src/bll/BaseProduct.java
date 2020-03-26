package bll;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseProduct implements MenuItem, Serializable {
	private String name;
	private double price;
	
	public BaseProduct(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public void add(MenuItem item) {	
	}

	public void remove(MenuItem item) {
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
