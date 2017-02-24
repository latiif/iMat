package Commons;

import Controllers.ICartList;
import se.chalmers.ait.dat215.project.Product;


/**
 * Created by latiif on 2/8/17.
 */
public class CartManager {
	private static ICartList mainCartList;

	public static ICartList getMainCartList(){
		return mainCartList;
	}

	public static void setMainCartList(ICartList cartList){
		mainCartList=cartList;
	}

}
