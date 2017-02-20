package Controllers;

import Commons.Inventory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.NotificationPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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
		if (btnInvoice.isSelected()){
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
					(cn1.getStyleClass().contains("valid"))) {

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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


		txtYear.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {

				boolean valid=true;
				//check if credit card is valid
				if (LocalDate.of(2000+Integer.parseInt(txtYear.getText()),Integer.parseInt(txtMonth.getText()),1).isBefore(LocalDate.now())){
					valid=false;
					Inventory.notificate("Ditt kort är inte giltigt");
				}
				else {
					Inventory.shopView.notificationPane.hide();
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

		Inventory.shopView.hideCashout();
		Inventory.shopView.finalView.initialize(null,null);
		Inventory.shopView.finalView.toFront();
		Inventory.shopView.updateStackPane();
		//Inventory.shopView.hideCartList();
	}

	@FXML
	void btnBackAction(ActionEvent event){
		Inventory.shopView.deliveryInformation.toFront();
		Inventory.shopView.updateStackPane();
	}
}
