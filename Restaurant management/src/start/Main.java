package start;

import dataLayer.FileWriter;
import bll.*;
import presentation.Chef;
import presentation.Controller;
import presentation.View;

public class Main {
	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant();
		FileWriter fileWrite = new FileWriter();
		fileWrite.deserializationArrayList();
		fileWrite.deserializationHashMap();
		Chef chef = new Chef();
		restaurant.addObserver(chef);
		View view = new View();
		Controller controller = new Controller(view, restaurant);
		view.setVisible(true);
	}
}
