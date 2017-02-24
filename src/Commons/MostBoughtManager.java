package Commons;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by latiif on 2/22/17.
 */
public class MostBoughtManager {

	public static HashMap<String,Integer> products= new LinkedHashMap<>();

	public static void readAll(){
		File file= new File(IMatDataHandler.getInstance().imatDirectory()+"/MostBought.list");

		try (ObjectInputStream oos =
					 new ObjectInputStream(new FileInputStream(file))) {

			products=((LinkedHashMap<String,Integer>)oos.readObject());

		} catch (Exception ex) {
			return;
		}
	}

	public static void saveAll(){

		File file= new File(IMatDataHandler.getInstance().imatDirectory()+"/MostBought.list");

		try (ObjectOutputStream oos =
					 new ObjectOutputStream(new FileOutputStream(file))) {

			oos.writeObject(products);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public static void addItem(String itemName){
		if (products.containsKey(itemName)){
			products.put(itemName,products.get(itemName)+1);
		}
		else
		{
			products.put(itemName,1);
		}
	}

	public static int getClicks(String itemName){
		if (products.containsKey(itemName)){
			return products.get(itemName);
		}

		return 0;
	}

}
