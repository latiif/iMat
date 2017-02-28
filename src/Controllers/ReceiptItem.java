package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/28/17.
 */
public class ReceiptItem extends AnchorPane implements Initializable {

	@FXML
	Label lblName,lblCost,lblQty;

	private String name,qty,cost;

	public ReceiptItem(String name,String qty, String cost){
		this.name=name;
		this.cost=cost;
		this.qty=qty;


		FXMLLoader loader =new FXMLLoader(getClass().getResource("FXMLFiles/ReceiptItem.fxml"));

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
		lblName.setText(name);
		lblCost.setText(cost);
		lblQty.setText(qty);
	}

}
