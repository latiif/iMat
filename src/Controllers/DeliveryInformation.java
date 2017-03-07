package Controllers;

import Commons.Inventory;
import com.latiif.getAddressInfo.GetAddress;
import com.latiif.getAddressInfo.GetIP;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SegmentedButton;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/18/17.
 */
public class DeliveryInformation extends AnchorPane implements Initializable {

	private final static LocalDate DATE=LocalDate.now().plusDays(1);

	@FXML
	ToggleButton btnAM,btnPM, btnEvening;

	@FXML
	Button btnNext;

	@FXML
	TextField txtName,txtStreet,txtZipcode,txtCity;

	@FXML
	DatePicker date;

	public DeliveryInformation(){
		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/DeliveryInformation.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
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
				(txtZipcode.getStyleClass().contains("valid")) &&
				(txtStreet.getStyleClass().contains("valid")) &&
				(date.getStyleClass().contains("valid")))
		{
			btnNext.setDisable(false);
		}
		else {
			btnNext.setDisable(true);
		}
	}

	void getCity(){

		try {
			GetAddress.getAddress(GetIP.getIP());
		} catch (IOException e) {
			e.printStackTrace();
		}

		txtCity.setText(GetAddress.city);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		AnchorPane.setBottomAnchor(this,0.0);
		AnchorPane.setLeftAnchor(this,0.0);
		AnchorPane.setTopAnchor(this,0.0);
		AnchorPane.setRightAnchor(this,0.0);


		Inventory.shopView.hideCashout();


		SegmentedButton segmentedButton= new SegmentedButton();
		btnAM.setToggleGroup(segmentedButton.getToggleGroup());
		btnPM.setToggleGroup(segmentedButton.getToggleGroup());
		btnEvening.setToggleGroup(segmentedButton.getToggleGroup());

		btnAM.selectedProperty().addListener(changeListener);
		btnEvening.selectedProperty().addListener(changeListener);
		btnPM.selectedProperty().addListener(changeListener);



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

		txtZipcode.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				validate(txtZipcode,newValue.matches("(\\d[\\s-]*){5}"));
			}
		});

		txtCity.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				validate(txtCity,newValue.matches("^\\p{L}*(?:[\\s-]\\p{L}*)*"));
			}
		});

		date.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
				if (newValue.isBefore(DATE)){
					validate(date,false);
				}
				else {
					validate(date,true);
				}
			}
		});


	loadUserInfo();

		date.setValue(DATE);



	}

	void loadUserInfo(){
		if (Inventory.hasCustomer()){
			txtName.setText(Inventory.customer.getFirstName()+" "+Inventory.customer.getLastName());
			txtCity.setText(Inventory.customer.getPostAddress());
			txtStreet.setText(Inventory.customer.getAddress());
			txtZipcode.setText(Inventory.customer.getPostCode());
		}
		else
		{
			getCity();
		}
	}


	@FXML
	private void btnNextAction(ActionEvent actionEvent){
		if (IMatDataHandler.getInstance().isCustomerComplete()) {

		}
		else {

			IMatDataHandler.getInstance().getCustomer().setFirstName(txtName.getText().split(" ")[0]);
			IMatDataHandler.getInstance().getCustomer().setLastName(txtName.getText().split(" ")[1]);
			IMatDataHandler.getInstance().getCustomer().setAddress(txtStreet.getText());
			IMatDataHandler.getInstance().getCustomer().setPostCode(txtZipcode.getText());
			IMatDataHandler.getInstance().getCustomer().setPostAddress(txtCity.getText());
		}


		Inventory.shopView.confrimView.lblAddress.setText(txtName.getText()+", "+txtStreet.getText());

		Inventory.shopView.finalView.lblSubTitle2.setText(
date.getValue().getDayOfWeek().toString().toLowerCase() +" den "+date.getValue().toString()+" kl." 	+getTime()	);

		Inventory.shopView.paymentInformation.toFront();
		Inventory.shopView.updateStackPane();
		Inventory.shopView.paymentInformation.lblAddress.setText(txtName.getText()+"\n"+txtStreet.getText() + "\n"+txtZipcode.getText()+" "+txtCity.getText());
	}


	private String getTime(){
		if (btnAM.isSelected()){
			return "08-12";
		}
		if (btnPM.isSelected()){
			return "12-16";
		}

		return "16-20";
	}


	@FXML
	private void btnCancel(ActionEvent event){
		this.toBack();
		Inventory.shopView.updateStackPane();
		Inventory.shopView.showCashout();
		Inventory.shopView.removeShadow();
	}

	private ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (!btnAM.isSelected() && !btnPM.isSelected() && !btnEvening.isSelected()){
				btnNext.setDisable(true);
			}
			else {
				btnNext.setDisable(false);
			}
		}
	};

}
