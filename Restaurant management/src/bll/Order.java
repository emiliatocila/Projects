package bll;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {
	private int orderID;
	private String date;
	private int table;
	
	public Order(int orderID, String date, int table) {
		this.orderID = orderID;
		this.date = date;
		this.table = table;
	}
	
	public Order getOrder() {
		return this;
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getTable() {
		return table;
	}
	
	public void setTable(int table) {
		this.table = table;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Order)) return false;
		Order order = (Order)o;
		return order.orderID == this.orderID && order.date.equals(this.date) && order.table == this.table;
	}
	
	@Override
	public int hashCode() {
		return orderID + date.hashCode() + table;
	}
	
	public String toString() {
		return "ID: " + orderID + "\nDate: " + date + "\nTable: " + table;
	}
}
