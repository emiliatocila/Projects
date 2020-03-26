package model;

public class Order {
	private int orderId;
	private int productId; 
	private int clientId;
	private int quantity;
	
	public Order() {
	}
	
	public Order(int orderId, int productId, int clientId, int quantity) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.clientId = clientId;
		this.quantity = quantity;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return "Order [orderId=" + orderId + ", productId=" + productId + ", clientId=" + clientId + ", quantity="
				+ quantity + "]";
	}
	
}
