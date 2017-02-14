package Commons;

import org.apache.commons.io.FileUtils;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by latiif on 2/1/17.
 */
public class FavManager {
	private static FavManager instance=null;


	//CHANGE TO ITEMS WHEN BACKEND COMES




	public boolean deal(Product item){


		if (IMatDataHandler.getInstance().isFavorite(item)){
			IMatDataHandler.getInstance().removeFavorite(item);
			return false;
		}
		else {
			IMatDataHandler.getInstance().addFavorite(item);
			return true;
		}

	}



	public  boolean isFav(Product item){
		return IMatDataHandler.getInstance().isFavorite(item);
	}



	public static FavManager getInstance(){
		if (instance==null){
			instance= new FavManager();
		}
		return instance;
	}

	private FavManager(){

	}


	public void saveFavorites(){

	}
}
