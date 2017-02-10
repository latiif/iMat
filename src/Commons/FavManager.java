package Commons;

import org.apache.commons.io.FileUtils;

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
	private  List<String> list = new ArrayList<>();



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
			instance= new FavManager();
		}
		return instance;
	}

	private FavManager(){
		File input=new File("favs.dat");
		String data;
		try {
			data = FileUtils.readFileToString(input,"UTF-8");
		} catch (IOException e) {
			data=null;
			System.out.println("File not found");
			return;
		}

		list.clear();
		list= new ArrayList<String >(Arrays.asList(data.split(",")));


	}


	public void saveFavorites(){

		StringBuilder stringBuilder= new StringBuilder();
		for(String item: list){
			stringBuilder=stringBuilder.append(item).append(",");
		}

		System.out.println(stringBuilder.toString());

		File output= new File("favs.dat");
		try {
			FileUtils.writeStringToFile(output,stringBuilder.toString(),"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
