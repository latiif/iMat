package Commons;

import se.chalmers.ait.dat215.project.ProductCategory;

/**
 * Created by latiif on 2/28/17.
 */
public class Translator {
	public static String getSwedishName(ProductCategory category){
		switch (category){
			case BERRY: return "B�r";
			case BREAD:return "Br�d";
			case CABBAGE: return "Sallad & K�l";
			case CITRUS_FRUIT: return "Citrus";
			case COLD_DRINKS:return "Kalla drycker";
			case DAIRIES: return "Mejeri";
			case EXOTIC_FRUIT:return "Exotiska frukter";
			case FISH:return "Fisk";
			case FLOUR_SUGAR_SALT:return "Skafferi";
			case FRUIT:return "Frukter";
			case HERB:return "�rter";
			case HOT_DRINKS:return "Varma drycker";
			case MEAT:return "K�tt";
			case MELONS:return "Meloner";
			case NUTS_AND_SEEDS: return "N�tter & Fr�n";
			case PASTA:return "Pasta";
			case POD: return "Baljv�xter";
			case POTATO_RICE:return "Potatis & Ris";
			case ROOT_VEGETABLE: return "Rotsaker";
			case SWEET:return "Godis";
			case VEGETABLE_FRUIT:return "Gr�nsaker";

		}

		return "";
	}


	public static String getSwedishDayName(String day){
		if (day.equals("monday")){
			return "mwndag";
		}
		if (day.equals("sunday")){
			return "s�ndag";
		}

		if (day.equals("tuseday")){
			return "tisdag";
		}

		if (day.equals("thursday")){
			return "torsdag";
		}
		if(day.equals("wednesday")){
			return "onsdag";
		}

		if (day.equals("saturday")){
			return "l�rdag";
		}

		if (day.equals("friday")){
			return "fredag";
		}

		return "n�gon dag";
	}
}
