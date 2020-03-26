package dataLayer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import bll.*;

public class FileWriter {
	public static void serializationArrayList() {
		try {
			FileOutputStream fos = new FileOutputStream("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//arraylist.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Restaurant.items);
			oos.close();
			fos.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public static void serializationHashMap() {
		try {
			FileOutputStream fos = new FileOutputStream("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//hashmap.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Restaurant.itemsOfOrders);
			oos.close();
			fos.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void deserializationArrayList() {
		try{
			FileInputStream fis = new FileInputStream("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//arraylist.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Restaurant.items = (ArrayList<MenuItem>)ois.readObject();
			ois.close();
			fis.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			return; 
		}
		catch (ClassNotFoundException c){
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deserializationHashMap() { 
		try{
			FileInputStream fis = new FileInputStream("C://Users//EMILIA//Desktop//Facultate//AN 2//SEMESTRUL 2//Tehnici de programare//Laborator//HW4//hashmap.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Restaurant.itemsOfOrders = (HashMap<Order, ArrayList<MenuItem>>)ois.readObject();
			ois.close();
			fis.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			return;
		}
		catch (ClassNotFoundException c){
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}
}
