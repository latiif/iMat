package Commons;

import se.chalmers.ait.dat215.project.Product;

import java.util.Comparator;

/**
 * Created by latiif on 2/28/17.
 */
public class ItemComparatorPrice implements Comparator<Product> {
	@Override
	public int compare(Product o1, Product o2) {
		if (o1.equals(o2)){
			return 0;
		}
		if (o2==null){
			return 1;
		}

		return (int) (o1.getPrice()-o2.getPrice());
	}
}
