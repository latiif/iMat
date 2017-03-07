package Controllers;

import Commons.CartManager;
import Commons.Inventory;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/8/17.
 */
public class CartList extends AnchorPane implements Initializable, ICartList {
	@FXML
	CheckBox chkSave;
	@FXML
	Button btnRemoveAll,btnCheckout;

	@FXML
	VBox vList;

	@FXML
	Label lblCost;

	@FXML
	ImageView imgCheckout;




	public List<CartItem> getCartItems(){
		return new ArrayList<>(this.cartItems);
	}

	 List<CartItem> cartItems = new ArrayList<>();

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


		if (this.isVisible()){
			btnCheckout.setVisible(true);
		}
		else {
			btnCheckout.setVisible(false);
		}

		imgCheckout.visibleProperty().bind(btnCheckout.visibleProperty());
		btnRemoveAll.visibleProperty().bind(btnCheckout.visibleProperty());
		updateCost();

	}


	private void updateCost() {
		double price = 0;
		for (CartItem cartItem : cartItems) {
			price += cartItem.cost;
		}

		lblCost.setText(String.format(Locale.ENGLISH,"%.2f",price) + " kr");

		if (btnCheckout != null && btnRemoveAll!=null) {

			if (cartItems.isEmpty()) {
				btnRemoveAll.setDisable(true);
				btnCheckout.setDisable(true);
			} else {
				btnRemoveAll.setDisable(false);
				btnCheckout.setDisable(false);
			}
		}


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

	@FXML
	private void btnCheckoutAction (ActionEvent actionEvent){
		Inventory.shopView.deliveryInformation.toFront();
		Inventory.shopView.updateStackPane();
		Inventory.shopView.hideCashout();
	}

}
