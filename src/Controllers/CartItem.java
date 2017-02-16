package Controllers;

import Commons.CartManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/8/17.
 */
public class CartItem extends AnchorPane implements Initializable {
	@FXML
	Button btnRemove;
	@FXML
	Label lblPrice, lblInfo;


	/*
	TODO: Replace with a private Item object
	 */
	private
	String itemName, itemUnit;
	private double itemPrice;
	private int quantity;

	public double cost;


	public String getItemName() {
		return this.itemName;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
		reformat();
	}


	/*
		TODO:Should take an Item object as argument
	 */
	public CartItem(String itemName, String itemUnit, double itemPrice, int quantity) {

		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemUnit = itemUnit;
		this.quantity = quantity;

		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/CartItem.fxml"));

		System.out.println(getClass().getResource("FXMLFiles/CartItem.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}


	}


	@FXML
	private void handleRemoveAction(ActionEvent event) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				CartManager.getMainCartList().deleteItem(itemName);
			}
		});
	}


	private String getCost(double price, double quantity){
		double cost = price*quantity;
		return String.format("%.2f",cost);
	}
	private void reformat() {
		if (itemUnit.equals("kg")){
			lblInfo.setText(quantity + " " + "hg" + " " + itemName);
			cost=Double.parseDouble(getCost(itemPrice/10,quantity));
		}
		else {
			lblInfo.setText(quantity + " " + itemUnit + " " + itemName);
			cost=Double.parseDouble(getCost(itemPrice,quantity));
		}

		lblPrice.setText(cost + " kr");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reformat();
	}
}
