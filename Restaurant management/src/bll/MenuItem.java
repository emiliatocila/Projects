package bll;

public interface MenuItem {
	public void add(MenuItem item);
	public void remove(MenuItem item);
	public String getName();
	public double getPrice();
	public void setPrice(double newPrice);
}
