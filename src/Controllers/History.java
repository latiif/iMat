package Controllers;

import Commons.Inventory;
import Commons.Receipt;
import Commons.ReceiptManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by latiif on 3/2/17.
 */
public class History extends AnchorPane implements Initializable {

	@FXML
	private FlowPane container;

	@FXML
	private ToggleButton tgWeek;

	@FXML
	private ToggleGroup tg;

	@FXML
	private Button bntClose;

	@FXML
	private ToggleButton tgYear;

	@FXML
	private ToggleButton tgMonth;

	private static final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public History(){
		FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLFiles/History.fxml"));

		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean listenersAdded=false;

	void addListeners(){
		if (listenersAdded){
			return;
		}
		else {
			listenersAdded=true;

			tgWeek.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue){
						updateReceiptList();

					}else{
						if (!tgWeek.isSelected() && !tgMonth.isSelected() && !tgYear.isSelected()){
							tgWeek.isSelected();
						}
					}
				}
			});

			tgMonth.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue){
						updateReceiptList();

					}else{
						if (!tgWeek.isSelected() && !tgMonth.isSelected() && !tgYear.isSelected()){
							tgMonth.isSelected();
						}
					}
				}
			});

			tgYear.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue){
						updateReceiptList();
					}else{
						if (!tgWeek.isSelected() && !tgMonth.isSelected() && !tgYear.isSelected()){
							tgYear.isSelected();
						}
					}
				}
			});
		}
	}


	List<Receipt> getLastWeek(){
		List<Receipt> res=new ArrayList<>();

		for(Receipt r :ReceiptManager.receipts){
			LocalDateTime date = LocalDateTime.parse(r.date,formatter);
			if (date.isAfter(LocalDateTime.now().minusWeeks(1))){
				res.add(r);
			}
		}
		return res;
	}

	List<Receipt> getLastMonth(){
		List<Receipt> res=new ArrayList<>();

		for(Receipt r :ReceiptManager.receipts){
			LocalDateTime date = LocalDateTime.parse(r.date,formatter);
			if (date.isAfter(LocalDateTime.now().minusMonths(1))){
				res.add(r);
			}
		}
		return res;
	}


	List<Receipt> getLastYear(){
		List<Receipt> res=new ArrayList<>();

		for(Receipt r :ReceiptManager.receipts){
			LocalDateTime date = LocalDateTime.parse(r.date,formatter);
			if (date.isAfter(LocalDateTime.now().minusYears(1))){
				res.add(r);
			}
		}
		return res;
	}

	void updateReceiptList(){
		container.getChildren().clear();

		List<Receipt> list;

		if (tgWeek.isSelected()){
			list=getLastWeek();
		}

		if (tgMonth.isSelected()){
			list=getLastWeek();
		}

		if (tgYear.isSelected()){
			list=getLastYear();
		}

		for (Receipt receipt: ReceiptManager.receipts){
			container.getChildren().add(new ReceiptView(receipt));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListeners();
		Inventory.shopView.removeShadow();

		updateReceiptList();

	}

	@FXML
	private void closeOnAction(ActionEvent event){
		this.toBack();
		Inventory.shopView.updateStackPane();
	}
}
