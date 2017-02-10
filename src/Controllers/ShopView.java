package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/6/17.
 */
public class ShopView extends AnchorPane implements Initializable {


	@FXML
	AnchorPane paneCartList,paneMain;

	public ShopView(){


		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/ShopView.fxml"));


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
		paneCartList.getChildren().add(new CartList());
		ItemsGrid itemsGrid= new ItemsGrid();
		AnchorPane.setLeftAnchor(itemsGrid,0.0);
		AnchorPane.setBottomAnchor(itemsGrid,0.0);
		AnchorPane.setRightAnchor(itemsGrid,0.0);
		AnchorPane.setTopAnchor(itemsGrid,0.0);
		itemsGrid.addItem("choco");
		itemsGrid.addItem("latte");
		itemsGrid.addItem("banana");
		itemsGrid.addItem("Yoghurt");
		paneMain.getChildren().add(itemsGrid);
	}
}
