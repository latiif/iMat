package Controllers;

import Commons.Receipt;
import Commons.ReceiptManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/20/17.
 */
public class ReceiptView extends AnchorPane implements Initializable {



	@FXML
	TitledPane titledPane;



	@FXML
	private ListView lstItems;

	@FXML
	private AnchorPane ReceiptView;

	@FXML
	private Label lblTotal;


	private final Receipt receipt;


	public ReceiptView(final Receipt receipt){
		this.receipt = receipt;


		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/ReceiptView.fxml"));


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
		titledPane.setText(receipt.date);
		lblTotal.setText(String.format(Locale.ENGLISH,"Summa: %.2f kr", receipt.price));

		for(String item: receipt.items){
			String[] strings=item.split("\t");
			lstItems.getItems().add(new ReceiptItem(strings[0],strings[1],strings[2]));
		}

		//lstItems.getItems().addAll(receipt.items);

		lstItems.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() {
					public void run() {
						lstItems.getSelectionModel().select(-1);

					}
				});
			}
		});

	}
}
