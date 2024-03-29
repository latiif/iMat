package Controllers;

import Commons.Inventory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/18/17.
 */
public class StartView extends AnchorPane implements Initializable {

	@FXML AnchorPane paneDefault, paneRegistered;
	@FXML
	StackPane stackPane;
	@FXML
	HBox hbox;

	@FXML
	Label lblWelcomeUser;


	public StartView(){
		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/StartView.fxml"));


		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	 void check(){
		if (!Inventory.hasCustomer()){
			paneDefault.toFront();
			Inventory.shopView.lblFav.setDisable(true);
			Inventory.shopView.lblOffers.setDisable(false);
		}
		else {
			Inventory.shopView.lblFav.setDisable(false);
			Inventory.shopView.lblOffers.setDisable(false);
			paneRegistered.toFront();
			lblWelcomeUser.setText("Všlkommen till iMat, "+IMatDataHandler.getInstance().getCustomer().getFirstName());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Inventory.shopView.removeShadow();


		stackPane.prefWidthProperty().bind(hbox.widthProperty());
		stackPane.prefHeightProperty().bind(hbox.heightProperty());

		check();

	}

	@FXML
	private void btnShopAction(MouseEvent mouseEvent){
		Inventory.shopView.itemsGrid.toFront();
		Inventory.shopView.itemsGrid.initialize(null,null);
		Inventory.shopView.showCartList();
		Inventory.shopView.updateStackPane();
	}

	@FXML
	private void lblFavoriteOnAction(Event event){
		Inventory.shopView.lblFavoriteOnAction(event);
	}
}
