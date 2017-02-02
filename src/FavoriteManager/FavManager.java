package FavoriteManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by latiif on 2/1/17.
 */
public class FavManager {
	private static FavManager instance=null;


	//CHANGE TO ITEMS WHEN BACKEND COMES
	private static List<String> list = new ArrayList<>();


	public boolean deal(String item){

		if (list.contains(item)){
			list.remove(item);
			return false;
		}
		else {
			list.add(item);
			return true;
		}
	}



	public  boolean isFav(String item){
		return list.contains(item);
	}



	public static FavManager getInstance(){
		if (instance==null){
			return new FavManager();
		}
		else return instance;
	}

	private FavManager(){

	}
}
