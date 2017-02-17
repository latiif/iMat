package Controllers;

import Controllers.ItemView;
import javafx.beans.value.ObservableValue;
import se.chalmers.ait.dat215.project.Product;

/**
 * Created by latiif on 2/8/17.
 */
public interface ICartList {
	public void AddItem(ItemView itemView);
	public void deleteItem(String name);

}
