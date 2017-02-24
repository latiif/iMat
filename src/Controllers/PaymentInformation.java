package Controllers;

import Commons.CreditCardManager;
import Commons.Inventory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.ait.dat215.project.CreditCard;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/18/17.
 */
public class PaymentInformation extends AnchorPane implements Initializable {

	@FXML
	private Button btnBack;

	@FXML
	private TextField txtCvc;

	@FXML
	private TextField cn2;

	@FXML
	private TextField cn1;

	@FXML
	private TextField txtMonth;

	@FXML
	private TextField cn4;

	@FXML
	private TextField txtYear;

	@FXML
	private TextField cn3;

	@FXML
	private ToggleButton btnCard;

	@FXML
	private VBox mainBox,boxCredit;

	@FXML
	private TextField txtHolder;

	@FXML
	private Button btnFinish;

	@FXML
	private ToggleButton btnInvoice;

	@FXML
	CheckBox chkSaveCard;

	private static boolean listeneresAdded=false;




	private void validate(Node node, boolean valid){
		if (node.getStyleClass().size()==2){
			node.getStyleClass().add("place-holder");
		}

		if (isCreditAvailable){
			return;
		}

		if (valid){
			node.getStyleClass().set(2,"valid");
		}
		else {
			node.getStyleClass().set(2,"error");
		}

		checkAll();
	}

	private void reset_styling(Node node){
		if (node.getStyleClass().size()==2){
			return;
		}
		else{
			node.getStyleClass().remove(2);
		}

	}

	private void checkAll(){
		if (btnInvoice.isSelected() ){
			btnFinish.setDisable(false);
			Inventory.shopView.notificationPane.hide();
			return;
		}

		if (btnCard.isSelected()) {
			if (txtHolder.getStyleClass().contains("valid") &&
					(txtMonth.getStyleClass().contains("valid")) &&
					(txtCvc.getStyleClass().contains("valid")) &&
					(cn2.getStyleClass().contains("valid")) &&
					(cn3.getStyleClass().contains("valid")) &&
					(cn4.getStyleClass().contains("valid")) &&
					(txtYear.getStyleClass().contains("valid")) &&
					(cn1.getStyleClass().contains("valid")) || isCreditAvailable) {

				btnFinish.setDisable(false);

			} else {
				btnFinish.setDisable(true);
			}

			return;
		}

		btnFinish.setDisable(true);
		if (!btnCard.isSelected() && !btnInvoice.isSelected()){
			Inventory.notificate("Var god välj ett sätt att betala");
		}
		else {
			Inventory.shopView.notificationPane.hide();
		}

	}

	public PaymentInformation() {

		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/PaymentInformation.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	private boolean isCreditAvailable;
	void CheckCreditCard(){
		CreditCard creditCard= CreditCardManager.getCreditCard();
		if (creditCard==null){//No credit card is saved


			txtHolder.setEditable(true);
			txtMonth.setEditable(true);
			txtCvc.setEditable(true);
			txtYear.setEditable(true);
			cn1.setEditable(true);
			cn2.setEditable(true);
			cn3.setEditable(true);
			cn4.setEditable(true);

			chkSaveCard.setSelected(false);
			chkSaveCard.setDisable(false);

			isCreditAvailable =false;

		}else{

			txtHolder.setEditable(false);
			txtMonth.setEditable(false);
			txtCvc.setEditable(false);
			txtYear.setEditable(false);
			cn1.setEditable(false);
			cn2.setEditable(false);
			cn3.setEditable(false);
			cn4.setEditable(false);

			chkSaveCard.setSelected(true);
			chkSaveCard.setDisable(true);


			txtHolder.setText(creditCard.getHoldersName());
			txtYear.setText(String.valueOf(creditCard.getValidYear()));
			txtMonth.setText(String.valueOf(creditCard.getValidMonth()));
			txtCvc.setText("xxx");

			String[] cardNumber = creditCard.getCardNumber().split("-");

			cn1.setText("xxxx");
			cn2.setText("xxxx");
			cn3.setText("xxxx");
			cn4.setText(cardNumber[3]);

			isCreditAvailable =true;

		}
	}


	void addListeneres(){
		if (listeneresAdded){
			return;
		}else {

			txtHolder.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(txtHolder,newValue.matches("^[\\p{L}\\s'.-]+$"));
				}
			});

			cn1.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(cn1,newValue.matches("(\\d){4}"));
					if (newValue.length()==4){
						cn2.requestFocus();
					}
				}
			});
			cn2.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(cn2,newValue.matches("(\\d){4}"));
					if (newValue.length()==4){
						cn3.requestFocus();
					}
				}
			});
			cn3.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(cn3,newValue.matches("(\\d){4}"));
					if (newValue.length()==4){
						cn4.requestFocus();
					}
				}
			});
			cn4.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(cn4,newValue.matches("(\\d){4}"));
					if (newValue.length()==4){
						txtMonth.requestFocus();
					}
				}
			});

			txtMonth.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(txtMonth,newValue.matches("[0-1][1-9]"));
					if (newValue.length()==2){
						txtYear.requestFocus();
					}
				}
			});

			txtYear.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					boolean valid=true;
					//check if credit card is valid
					if (LocalDate.of(2000+Integer.parseInt(txtYear.getText()),Integer.parseInt(txtMonth.getText()),1).isBefore(LocalDate.now())){
						valid=false;
					}
					else{
						valid=true;
					}
					validate(txtYear,txtYear.getText().matches("\\d{2}") && valid);
				}
			});



			txtCvc.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					validate(txtCvc,newValue.matches("\\d{3}"));
				}
			});





			listeneresAdded=true;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {



		CheckCreditCard();

		addListeneres();

		ToggleGroup toggleGroup= new ToggleGroup();
		btnCard.setToggleGroup(toggleGroup);
		btnInvoice.setToggleGroup(toggleGroup);



		boxCredit.visibleProperty().bind(btnCard.selectedProperty());




	}

	@FXML
	void InvoiceSelection(ActionEvent event){
		if (btnInvoice.isSelected()){
			btnFinish.setDisable(false);
		}

		checkAll();
	}


	@FXML
	void btnFinishAction(ActionEvent event) {


		if (chkSaveCard.isSelected()){
			CreditCardManager.setCreditCard(txtHolder.getText(),cn1.getText()+'-'+cn2.getText()+'-'+cn3.getText()+'-'+cn4.getText()
			,Integer.parseInt(txtMonth.getText()),Integer.parseInt(txtYear.getText()),Integer.parseInt(txtCvc.getText()));
		}else{
			txtHolder.setText("");
			txtYear.setText("");
			txtCvc.setText("");
			txtMonth.setText("");
			cn1.setText("");
			cn2.setText("");
			cn3.setText("");
			cn4.setText("");

			reset_styling(txtHolder);
			reset_styling(txtYear);
			reset_styling(txtCvc);
			reset_styling(txtMonth);
			reset_styling(cn1);
			reset_styling(cn2);
			reset_styling(cn3);
			reset_styling(cn4);


			btnInvoice.setSelected(false);
			btnCard.setSelected(false);

		}

		Inventory.shopView.hideCashout();
		Inventory.shopView.finalView.initialize(null,null);
		Inventory.shopView.finalView.toFront();
		Inventory.shopView.updateStackPane();
	}

	@FXML
	void btnBackAction(ActionEvent event){
		Inventory.shopView.deliveryInformation.toFront();
		Inventory.shopView.updateStackPane();
	}
}
