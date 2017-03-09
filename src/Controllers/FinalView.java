package Controllers;

import Commons.Inventory;
import Commons.MostBoughtManager;
import Commons.Receipt;
import Commons.ReceiptManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/20/17.
 */
public class FinalView extends AnchorPane implements Initializable{
	@FXML
	private Button btnHistory;

	@FXML
	 Label lblSubTitle2;

	@FXML
	private Button btnClose;

	@FXML
	private Label lblThankYou;

	@FXML
	private Button btnStart;

	@FXML
	private AnchorPane receiptPane;

	@FXML
	private Label lblThankYou1;


	private Receipt receipt;




	@Override
	public void initialize(URL location, ResourceBundle resources) {


		Inventory.shopView.startView.check();
		Inventory.shopView.hideCashout();

		receipt = new Receipt();
		receipt.date = LocalDate.now().toString() + " " + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();

		double price = 0;
		for (CartItem cartItem : Inventory.shopView.cartList.getCartItems()) {
			receipt.items.add(cartItem.toString());
			receipt.price += cartItem.cost;
			MostBoughtManager.addItem(cartItem.getItemName());
		}
		if (!Inventory.shopView.cartList.getCartItems().isEmpty()) {
		ReceiptManager.receipts.add(this.receipt);
		}

		if (Inventory.shopView.cartList.vList!=null){
			Inventory.shopView.cartList.vList.getChildren().clear();
			Inventory.shopView.cartList.cartItems.clear();
			Inventory.shopView.hideCartList();
			Inventory.shopView.cartList.initialize(null,null);
			Inventory.shopView.deliveryInformation.initialize(null,null);
			Inventory.shopView.paymentInformation.initialize(null,null);

		}


		receiptPane.getChildren().clear();
		receiptPane.getChildren().add(new ReceiptView(receipt));

		Inventory.shopView.removeShadow();

		Inventory.shopView.cartList.hideRedo();

	}

	public  FinalView(){
		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/FinalView.fxml"));


		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	private void goHome(ActionEvent actionEvent){
		Inventory.shopView.startView.initialize(null,null);
		Inventory.shopView.startView.toFront();
		Inventory.shopView.updateStackPane();
	}

	@FXML
	private void historyOnAction(MouseEvent mouseEvent){
		Inventory.shopView.history.initialize(null, null);
		Inventory.shopView.history.toFront();
		Inventory.shopView.updateStackPane();
	}
}
