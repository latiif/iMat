package Controllers;

import Commons.Inventory;
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

import java.io.IOException;
import java.net.URL;
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
	private VBox boxCredit;

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
			return;
		}

		if (txtHolder.getStyleClass().contains("valid") &&
				(txtMonth.getStyleClass().contains("valid")) &&
				(txtCvc.getStyleClass().contains("valid")) &&
				(cn2.getStyleClass().contains("valid")) &&
				(cn3.getStyleClass().contains("valid")) &&
				(cn4.getStyleClass().contains("valid")) &&
				(txtYear.getStyleClass().contains("valid")) &&
				(cn1.getStyleClass().contains("valid")))
		{
			btnFinish.setDisable(false);
		}
		else {
			btnFinish.setDisable(true);
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


		txtYear.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				validate(txtYear,newValue.matches("\\d{2}"));
				if (newValue.length()==2){
					txtCvc.requestFocus();
				}
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

		//btnInvoice.selectedProperty().bind(btnInvoice.selectedProperty().not());

	}

	@FXML
	void InvoiceSelection(ActionEvent event){
		if (btnInvoice.isSelected()){
			btnFinish.setDisable(false);
		}
		else {
			checkAll();
		}
	}


	@FXML
	void btnFinishAction(ActionEvent event) {

	}

	@FXML
	void btnBackAction(ActionEvent event){
		Inventory.shopView.deliveryInformation.toFront();
		Inventory.shopView.updateStackPane();
	}
}
