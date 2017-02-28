package Controllers;

import Commons.Receipt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
	private Label lblName1;

	@FXML
	private Label lblName11;

	@FXML
	private Label lblReceipt;

	@FXML
	private VBox lstItems;

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
		lblReceipt.setText(receipt.date);
		lblTotal.setText(String.format(Locale.ENGLISH,"Summa: %.2f kr", receipt.price));

		for(String item: receipt.items){
			String[] strings=item.split("\t");
			lstItems.getChildren().add(new ReceiptItem(strings[0],strings[1],strings[2]));
		}

		//lstItems.getItems().addAll(receipt.items);
	}
}
