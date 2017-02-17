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


import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.control.textfield.TextFields;
import se.chalmers.ait.dat215.project.*;



/**
 * Created by latiif on 2/6/17.
 */
public class ShopView extends AnchorPane implements Initializable {

	@FXML
	private Label lblBakery, lblFruit,lblMeat,lblMilk,lblPantry,lblCandy;



	private ItemsGrid itemsGrid;
	@FXML
	AnchorPane paneCartList,paneGrid;
	@FXML
	private TextField searchField;

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


	@FXML
	private void searchOnAction(ActionEvent event){
		itemsGrid.reset();
		for (Product product: Inventory.getInstance().favFirst(IMatDataHandler.getInstance().findProducts(searchField.getText()))){
			itemsGrid.addItem(product);
		}
	}

	@FXML
	private void lblFavoriteOnAction(MouseEvent event){
		itemsGrid.reset();
		for (Product product : Inventory.getInstance().favFirst(IMatDataHandler.getInstance().getProducts())){
			if (!IMatDataHandler.getInstance().isFavorite(product)){
				break;
			}
			itemsGrid.addItem(product);
		}
	}

	@FXML
	private void lblBakeryOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : Inventory.getInstance().favFirst(IMatDataHandler.getInstance().getProducts(ProductCategory.BREAD))){
				itemsGrid.addItem(product);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paneCartList.getChildren().add(new CartList());
		itemsGrid= new ItemsGrid();
		AnchorPane.setLeftAnchor(itemsGrid,0.0);
		AnchorPane.setBottomAnchor(itemsGrid,0.0);
		AnchorPane.setRightAnchor(itemsGrid,0.0);
		AnchorPane.setTopAnchor(itemsGrid,0.0);


		itemsGrid.reset();
		for(Product product: Inventory.getInstance().favFirst(Inventory.getInstance().getProductList())){
			itemsGrid.addItem(product);
		}

		TextFields.bindAutoCompletion(searchField,Inventory.getInstance().getNames());



		paneGrid.getChildren().add(itemsGrid);
	}
}
