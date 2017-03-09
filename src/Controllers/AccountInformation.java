package Controllers;

import Commons.CreditCardManager;
import Commons.Inventory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.IMatDataHandler;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/24/17.
 */
public class AccountInformation extends AnchorPane implements Initializable {

	private static boolean listenersAdded=false;

	@FXML
	private TextField txtCity;

	@FXML
	private Label lblCardNumber;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtZipCode;

	@FXML
	private Label lblCardHolder;

	@FXML
	private Label lblCvc;

	@FXML
	private TitledPane paneCardInformation;

	@FXML
	private Button btnClear,btnSave;

	@FXML
	private TextField txtStreet;

	@FXML
	private Label lblExpiration;


	public AccountInformation(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLFiles/AccountInfo.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void loadUserInformation(){
		if (Inventory.hasCustomer()){
			txtName.setText(Inventory.customer.getFirstName()+" "+Inventory.customer.getLastName());
			txtStreet.setText(Inventory.customer.getAddress());
			txtCity.setText(Inventory.customer.getPostAddress());
			txtZipCode.setText(Inventory.customer.getPostCode());
		}
	}


	public void loadCreditCardInformation(){
		CreditCard creditCard= CreditCardManager.getCreditCard();
		if (creditCard==null){
			paneCardInformation.setCollapsible(true);
			paneCardInformation.setExpanded(false);
			paneCardInformation.setCollapsible(false);

			paneCardInformation.setText("Kortinformation - (inget kort är sparat)");
		}
		else {
			paneCardInformation.setCollapsible(true);
			paneCardInformation.setExpanded(true);
			paneCardInformation.setCollapsible(false);
			paneCardInformation.setText("Kortinformation");

			lblCardHolder.setText(creditCard.getHoldersName());
			lblCardNumber.setText("xxxx-xxxx-xxxx-"+creditCard.getCardNumber().split("-")[3]);
			lblExpiration.setText(creditCard.getValidMonth()+"\\"+creditCard.getValidYear());
			lblCvc.setText("xxx");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListeners();
		loadUserInformation();
		loadCreditCardInformation();

	}

	@FXML
	void btnClearAction(ActionEvent actionEvent){
		CreditCardManager.clearCreditCard();

		loadCreditCardInformation();
		Inventory.shopView.paymentInformation.CheckCreditCard();
		Inventory.shopView.paymentInformation.initialize(null,null);


	}


	@FXML
	void btnSaveACtion(ActionEvent actionEvent){

		IMatDataHandler.getInstance().getCustomer().setFirstName(txtName.getText().split(" ")[0]);
		IMatDataHandler.getInstance().getCustomer().setLastName(txtName.getText().split(" ")[1]);
		IMatDataHandler.getInstance().getCustomer().setAddress(txtStreet.getText());
		IMatDataHandler.getInstance().getCustomer().setPostCode(txtZipCode.getText());
		IMatDataHandler.getInstance().getCustomer().setPostAddress(txtCity.getText());


		Inventory.shopView.deliveryInformation.loadUserInfo();

		this.toBack();
		Inventory.shopView.updateStackPane();

	}


	private void validate(Node node, boolean valid){
		if (node.getStyleClass().size()==2){
			node.getStyleClass().add("place-holder");
		}

		if (valid){
			node.getStyleClass().set(2,"valid");
		}
		else {
			node.getStyleClass().set(2,"error");
		}

		checkAll();
	}


	private void checkAll(){
		if (txtCity.getStyleClass().contains("valid") &&
				(txtName.getStyleClass().contains("valid")) &&
				(txtZipCode.getStyleClass().contains("valid")) &&
				(txtStreet.getStyleClass().contains("valid")))
		{
			btnSave.setDisable(false);
		}
		else {
			btnSave.setDisable(true);
		}
	}

	private void addListeners(){
		if (listenersAdded){
			return;
		}else {


			txtName.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(txtName,txtName.getText().split("\\s+").length==2 && newValue.matches("^[\\p{L}\\s'.-]+$"));
				}
			});

			txtStreet.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(txtStreet,newValue.matches("(\\p{L}*\\s*)+(\\s+\\d+\\s*\\p{L}*)?"));
				}
			});

			txtZipCode.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(txtZipCode,newValue.matches("(\\d[\\s-]*){5}"));
				}
			});

			txtCity.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(txtCity,newValue.matches("^\\p{L}*(?:[\\s-]\\p{L}*)*"));
				}
			});


			listenersAdded=true;
		}
	}
}
