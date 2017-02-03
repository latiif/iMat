package Controllers;

import FavoriteManager.FavManager;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/1/17.
 */
public class ItemView extends AnchorPane implements Initializable {


	/*

	Test values, until we get the backend, then we can use the Item class to populate the window
	*/
	 final int PRICE_PER_1=25;
	 final String item= "chockald";


	 @FXML
	 private AnchorPane ItemView,BoughtPane;

	@FXML
	private Label lblName,lblPriceInfo,lblFav,lblMessage;

	@FXML
	private TextField txtAmount;




	/*
	Change the text when adding or deleting elements
	 */
	@FXML
	private void updatePriceAmountLabel(){
		lblPriceInfo.setText(format(Integer.parseInt(txtAmount.getText())));

		lblMessage.setText(txtAmount.getText()+ "st " + item);
	}


	/*
	Event handler for btnPlus
	 */
	@FXML
	private void handleBtnPlusAction(ActionEvent event) {
		txtAmount.setText(String.valueOf(Integer.parseInt(txtAmount.getText())+1));
	}

	@FXML
	private void handleBtnMinusAction(ActionEvent event) {

		//make sure it does not get below 1
		if (txtAmount.getText().equals("1")){
			return;
		}
		txtAmount.setText(String.valueOf(Integer.parseInt(txtAmount.getText())-1));
	}


	/*
	Formats text according to the price and amount, and unit
	TODO: Make unit dynamic and not static
	 */
	private String format(int amount){
		return "Pris: "+(PRICE_PER_1*amount + " kr /"+amount+" st");
	}


	/*
	Handles adding elements to cart
	TODO Come up with a away to handle adding elements externally, by an abstract class for example
	 */
	@FXML
	private void handleAddAction(ActionEvent event) throws Exception {

		System.out.println(Integer.parseInt(txtAmount.getText()) + "My Item");

		Thread thread= new Thread() {
			@Override
			public void run() {
				ItemView.setVisible(false);
				BoughtPane.setVisible(true);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				ItemView.setVisible(true);
				BoughtPane.setVisible(false);

				//Reset the values of txtAmount
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						txtAmount.setText("1");
					}
				});
			}
		};

		thread.start();

		//come up with a way to handle adding an element
	}


	/*
	Class constructor
	TODO make it ask for an Item element, and connect it with the component
	 */
	public ItemView(){
		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/ItemView.fxml"));



		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}


	/*
	Event handler for mouse click to favorite
	 */
	@FXML
	private void handleFavAction(MouseEvent event){

		System.out.println("Clicked!");

		ImageView imgFav = (ImageView)(lblFav.getGraphic());
		if (FavManager.getInstance().deal(this.item)){
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_ENABLED))));
			lblFav.setText("Ta bort från favorit");
		}else {
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_DISABLED))));
			lblFav.setText("Spara som favorit!");
		}
	}



	/*
	Initialize the components of the ItemView
	Processed after the constructor
	Bind the inner Item object with the controls on the view
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtAmount.setText("1");


		//Making sure the user does not input non-numeric non-integer values as amount
		txtAmount.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				txtAmount.setText(newValue.replaceAll("[^\\d]", ""));
				updatePriceAmountLabel();
			}
		});

		ImageView imgFav = (ImageView)(lblFav.getGraphic());


		if (FavManager.getInstance().isFav(this.item)){
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_ENABLED))));
			lblFav.setText("Ta bort från favorit");

		}
		else {
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_DISABLED))));
			lblFav.setText("Spara som favorit!");

		}

	}
}
