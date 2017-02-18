package Controllers;

import Commons.CartManager;
import Commons.Inventory;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.scene.layout.StackPane;
import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.control.textfield.TextFields;
import se.chalmers.ait.dat215.project.*;


/**
 * Created by latiif on 2/6/17.
 */
public class ShopView extends AnchorPane implements Initializable {

	@FXML
	private Label lblBakery, lblFruit, lblMeat, lblMilk, lblPantry, lblCandy;

	@FXML
	AnchorPane stackContainer;

	@FXML
	StackPane stackPane;
	 ItemsGrid itemsGrid;
	 StartView startView;
	 DeliveryInformation deliveryInformation;
	 PaymentInformation paymentInformation;
	@FXML
	AnchorPane paneCartList, paneGrid, paneCart;
	@FXML
	private TextField searchField;

	public ShopView() {


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


	@FXML
	private void searchOnAction(ActionEvent event) {
		itemsGrid.reset();
		for (Product product : Inventory.getInstance().favFirst(IMatDataHandler.getInstance().findProducts(searchField.getText()))) {
			itemsGrid.addItem(product);
			itemsGrid.toFront();
			updateStackPane();
			showCartList();
		}
	}

	@FXML
	private void lblFavoriteOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : Inventory.getInstance().favFirst(IMatDataHandler.getInstance().getProducts())) {
			if (!IMatDataHandler.getInstance().isFavorite(product)) {
				break;
			}
			itemsGrid.addItem(product);
		}
	}

	@FXML
	private void lblBakeryOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : Inventory.getInstance().favFirst(IMatDataHandler.getInstance().getProducts(ProductCategory.BREAD))) {
			itemsGrid.addItem(product);
			itemsGrid.toFront();
			updateStackPane();
			showCartList();
		}
	}

	private void bringGrid() {

	}


	 void hideCartList() {
		stackContainer.toFront();
		AnchorPane.setRightAnchor(stackContainer, 0.0);
		paneCart.setVisible(false);
	}

	 void showCartList() {
		stackContainer.toBack();
		AnchorPane.setRightAnchor(stackContainer, 260.0);
		paneCart.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Inventory.shopView=this;

		paneCartList.getChildren().add(new CartList());
		itemsGrid = new ItemsGrid();
		startView= new StartView();
		deliveryInformation= new DeliveryInformation();
		paymentInformation= new PaymentInformation();

		itemsGrid.reset();
		for (Product product : Inventory.getInstance().favFirst(Inventory.getInstance().getProductList())) {
			itemsGrid.addItem(product);
		}

		TextFields.bindAutoCompletion(searchField, Inventory.getInstance().getNames());


		stackPane.getChildren().addAll(startView,itemsGrid,deliveryInformation,paymentInformation);

		startView.toFront();
		updateStackPane();

		hideCartList();
	}


	public void updateStackPane(){
		int i;
		for (i=0;i<stackPane.getChildren().size()-1;i++){
			stackPane.getChildren().get(i).setVisible(false);
		}
		stackPane.getChildren().get(i).setVisible(true);
	}

}
