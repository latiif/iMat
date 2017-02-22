package Commons;

import Controllers.ShopView;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by latiif on 2/14/17.
 */
public class Inventory {

	private static Inventory instance;

	public static ShopView shopView;

	public  static Customer customer;

	public  static Inventory getInstance(){
		if (instance==null){
			instance= new Inventory();
		}
		return instance;
	}




	public static List<Product> productList = new ArrayList<>();

	List<String> names= new ArrayList<>();

	public Collection getNames(){
		return names;
	}


	public static boolean  hasCustomer()
	{
		if (IMatDataHandler.getInstance().getCustomer().getFirstName().equals("") &&
				IMatDataHandler.getInstance().getCustomer().getLastName().equals("") &&
		IMatDataHandler.getInstance().getCustomer().getPostCode().equals("") &&
				IMatDataHandler.getInstance().getCustomer().getAddress().equals("") &&
				IMatDataHandler.getInstance().getCustomer().getPostAddress().equals("")){
			return false;
		}
		else {
			customer=IMatDataHandler.getInstance().getCustomer();
			return true;
		}
	}

	public  static void notificate(String msg){
		shopView.notificationPane.show(msg);
	}


	public List<Product> favFirst(List<Product> products){
		ArrayList<Product> favs=new ArrayList<>(),normals= new ArrayList();

		for (Product product : products){
			if (IMatDataHandler.getInstance().isFavorite(product)){
				favs.add(product);
			}
			else {
				normals.add(product);
			}
		}

		favs.addAll(favs.size(),normals);

		return favs;

	}

	public List<Product> getProductList(){
		return productList;
	}

	private Inventory(){
		for(Product product: IMatDataHandler.getInstance().getProducts()){
			productList.add(product);
			names.add(product.getName());
			hasCustomer();
		}

	}

}
