package Controllers;

import Commons.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by latiif on 3/7/17.
 */
public class ConfrimView extends AnchorPane implements Initializable{

	@FXML
	public Label lblAddress;
	@FXML
	public Label lbl_Payment;

	public ConfrimView(){
		FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLFiles/ConfirmView.fxml"));

		loader.setController(this);
		loader.setRoot(this);

		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void btnConfrimOrder(ActionEvent actionEvent){

		Inventory.shopView.hideCashout();
		Inventory.shopView.finalView.initialize(null,null);
		Inventory.shopView.finalView.toFront();
		Inventory.shopView.updateStackPane();
	}

	@FXML
	void btnBackAction(ActionEvent event){
		Inventory.shopView.paymentInformation.toFront();
		Inventory.shopView.updateStackPane();
	}

	@FXML
	void editDelivery(ActionEvent event){
		Inventory.shopView.deliveryInformation.toFront();
		Inventory.shopView.updateStackPane();
	}

	@FXML
	void editPayment(ActionEvent event){
		Inventory.shopView.paymentInformation.toFront();
		Inventory.shopView.updateStackPane();
	}

}
