package Controllers;

import Commons.CartManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/8/17.
 */
public class CartList extends AnchorPane implements Initializable, ICartList {
	@FXML
	CheckBox chkSave;
	@FXML
	Button btnRemoveAll;

	@FXML
	VBox vList;

	@FXML
	Label lblCost;


	/*

	* */


	private List<CartItem> cartItems = new ArrayList<>();

	public CartList() {

		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/CartList.fxml"));


		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		CartManager.setMainCartList(this);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//vList.getChildren().add(new CartItem("Chocolate","st",24,2));

	}


	private void updateCost() {
		int price = 0;
		for (CartItem cartItem : cartItems) {
			price += cartItem.cost;
		}

		lblCost.setText(price + " kr");
	}

	@Override
	public void AddItem(ItemView itemView) {
		for (CartItem cartItem : cartItems) {
			if (cartItem.getItemName().equals(itemView.getItemName())) {
				cartItem.addQuantity(itemView.getQuantity());

				return;
			}
		}

		cartItems.add(new CartItem(itemView.getItemName(), itemView.getUnit(), itemView.getUnitPrice(), itemView.getQuantity()));

		vList.getChildren().setAll(cartItems);
		System.out.println(vList.getChildren().size());

		updateCost();
	}

	@Override
	public void deleteItem(String name) {
		for(CartItem cartItem: cartItems){
			if (cartItem.getItemName().equals(name)){
				cartItems.remove(cartItem);
				break;
			}
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				vList.getChildren().setAll(cartItems);
			}
		});

		updateCost();
	}

	@FXML
	private void handleRemoveAllAction(ActionEvent event) {
		cartItems.clear();
		vList.getChildren().clear();
		updateCost();
	}

}