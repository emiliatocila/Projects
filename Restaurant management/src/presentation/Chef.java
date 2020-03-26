package presentation;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import bll.*;

public class Chef implements Observer {
	private JFrame chefFrame;
	
	public Chef() {
		chefFrame = new JFrame();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		String message = "";
		ArrayList<MenuItem> itemsOfOrder = Restaurant.itemsOfOrders.get(((Order)arg1));
		message += ((Order)arg1).toString();
		message += "\nItems ordered: \n";
		for(MenuItem item : itemsOfOrder)
			message += item.getName() + '\n';
		JOptionPane.showMessageDialog(chefFrame, message, "NEW ORDER ADDED", JOptionPane.PLAIN_MESSAGE);
	}

}
