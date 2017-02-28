package Commons;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.util.Comparator;

/**
 * Created by latiif on 2/22/17.
 */
public class ItemComparatorDefault implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {

		if (o1.equals(o2)){
			return 0;
		}
		if (o2==null){
			return -1;
		}


		boolean o1isFav=IMatDataHandler.getInstance().isFavorite(o1);
		boolean o2isFav=IMatDataHandler.getInstance().isFavorite(o2);

		int o1Clicks= MostBoughtManager.getClicks(o1.getName());
		int o2Clicks= MostBoughtManager.getClicks(o2.getName());


		if (o1isFav && o2isFav){
			if (o1Clicks>=o2Clicks){
				return -1;
			}
			else {
				return 1;
			}
		}else if(!o1isFav && !o2isFav){
			if (o1Clicks>=o2Clicks){
				return -1;
			}
			else {
				return 1;
			}
		}
		else{
			if (o1isFav){
				return -1;
			}
			else {
				return 1;
			}
		}




	}
}
