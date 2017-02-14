package Commons;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by latiif on 2/14/17.
 */
public class Inventory {

	private static Inventory instance;

	public  static Inventory getInstance(){
		if (instance==null){
			instance= new Inventory();
		}
		return instance;
	}




	List<Product> productList = new ArrayList<>();

	List<String> names= new ArrayList<>();

	public Collection getNames(){
		return names;
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

		}

	}

}
