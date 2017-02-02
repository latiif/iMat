package Controllers;

import FavoriteManager.FavManager;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
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

	public void setLblName(String name){
		lblName.setText(name);
	}


	@FXML

	private void updatePriceAmountLabel(){
		lblPriceInfo.setText(format(Integer.parseInt(txtAmount.getText())));

		lblMessage.setText(txtAmount.getText()+ "st " + item);
	}

	@FXML
	private void handleBtnPlusAction(ActionEvent event) {
		txtAmount.setText(String.valueOf(Integer.parseInt(txtAmount.getText())+1));


		//we've changed the amount
		updatePriceAmountLabel();
	}

	@FXML
	private void handleBtnMinusAction(ActionEvent event) {

		//make sure it does not get below 1
		if (txtAmount.getText().equals("1")){
			return;
		}
		txtAmount.setText(String.valueOf(Integer.parseInt(txtAmount.getText())-1));

		//we've changed the amount
		updatePriceAmountLabel();
	}


	private String format(int amount){
		return (PRICE_PER_1*amount + "kr /"+amount+"st");
	}


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
			}
		};

		thread.start();
		updatePriceAmountLabel();
		txtAmount.setText("1");




		//come up with a way to handle adding an element
		//throw new Exception(" some thing went wrong");
	}


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

	@FXML
	private void handleFavAction(MouseEvent event){

		System.out.println("SSS");

		ImageView imgFav = (ImageView)(lblFav.getGraphic());
		if (FavManager.getInstance().deal(this.item)){
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_ENABLED))));
			lblFav.setText("Ta bort från favorit");
		}else {
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_DISABLED))));
			lblFav.setText("Spara som favorit!");
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtAmount.setText("1");

		ImageView imgFav = (ImageView)(lblFav.getGraphic());

		System.out.println(getClass());

		if (FavManager.getInstance().isFav(this.item)){
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_ENABLED))));
			lblFav.setText("Ta bort från favorit");

		}
		else {
			imgFav.setImage(new Image(String.valueOf(getClass().getResource(IconManager.FAV_DISABLED))));
			lblFav.setText("Spara som favorit!");

		}


		//lblFav.setGraphic(imgFav);
		updatePriceAmountLabel();
	}
}
