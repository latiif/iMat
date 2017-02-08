package Controllers;

import Controllers.ItemView;

/**
 * Created by latiif on 2/8/17.
 */
public interface ICartList {
	public void AddItem(ItemView itemView);
	public void deleteItem(String name);
}
