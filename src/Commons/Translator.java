package Commons;

import se.chalmers.ait.dat215.project.ProductCategory;

/**
 * Created by latiif on 2/28/17.
 */
public class Translator {
	public static String getSwedishName(ProductCategory category){
		switch (category){
			case BERRY: return "Bär";
			case BREAD:return "Bröd";
			case CABBAGE: return "Kål";
			case CITRUS_FRUIT: return "Citrusfrukt";
			case COLD_DRINKS:return "Kalla drycker";
			case DAIRIES: return "Mejeri";
			case EXOTIC_FRUIT:return "Exotiska frukter";
			case FISH:return "Fisk";
			case FLOUR_SUGAR_SALT:return "Skafferi";
			case FRUIT:return "Frukter";
			case HERB:return "Örter";
			case HOT_DRINKS:return "Varma drycker";
			case MEAT:return "Kött";
			case MELONS:return "Meloner";
			case NUTS_AND_SEEDS: return "Nötter & Frön";
			case PASTA:return "Pasta";
			case POD: return "Baljväxter";
			case POTATO_RICE:return "Potatis & Ris";
			case ROOT_VEGETABLE: return "Rotsaker";
			case SWEET:return "Godis";
			case VEGETABLE_FRUIT:return "Grönsaksfrukter";

		}

		return "";
	}
}
