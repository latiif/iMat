package Controllers;

import Commons.CartManager;
import Commons.Inventory;
import Commons.ItemComparator;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import java.util.*;


import javafx.scene.layout.StackPane;
import org.controlsfx.control.NotificationPane;
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
	Label lblFav,lblOffers;

	@FXML
	AnchorPane stackContainer;

	@FXML
	StackPane stackPane;
	 ItemsGrid itemsGrid;
	 StartView startView;
	 DeliveryInformation deliveryInformation;
	 PaymentInformation paymentInformation;
	 FinalView finalView;
	 CartList cartList;
	 AccountInformation accountInformation;
	@FXML
	AnchorPane paneCartList, paneGrid, paneCart;
	@FXML
	private TextField searchField;

	public NotificationPane notificationPane;

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

		List<Product> result=IMatDataHandler.getInstance().findProducts(searchField.getText());
		Collections.sort(result,new ItemComparator());
		Collections.reverse(result);

		for (Product product : result) {
			itemsGrid.addItem(product);
			itemsGrid.toFront();
			updateStackPane();
			showCartList();
		}
	}

	@FXML
	 void lblFavoriteOnAction(Event event) {
		itemsGrid.reset();
		List<Product> result=IMatDataHandler.getInstance().getProducts();
		Collections.sort(result,new ItemComparator());
		Collections.reverse(result);
		for (Product product : result) {
			if (!IMatDataHandler.getInstance().isFavorite(product)) {
				break;
			}
			itemsGrid.addItem(product);
		}

		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
	}

	@FXML
	private void lblBakeryOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : Inventory.getInstance().favFirst(IMatDataHandler.getInstance().getProducts(ProductCategory.BREAD))) {
			itemsGrid.addItem(product);
		}
		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
	}

	private void bringGrid() {

	}


	void hideCashout(){
		cartList.btnCheckout.setVisible(false);
	}


	void showCashout(){
		cartList.btnCheckout.setVisible(true);
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

		cartList=new CartList();
		accountInformation= new AccountInformation();
		paneCartList.getChildren().add(cartList);
		itemsGrid = new ItemsGrid();
		startView= new StartView();
		deliveryInformation= new DeliveryInformation();
		paymentInformation= new PaymentInformation();
		finalView= new FinalView();

		itemsGrid.reset();
		Collections.sort(Inventory.productList,new ItemComparator());
		Collections.reverse(Inventory.productList);

		for (Product product : Inventory.productList) {
			itemsGrid.addItem(product);
		}

		TextFields.bindAutoCompletion(searchField, Inventory.getInstance().getNames());


		stackPane.getChildren().addAll(startView,itemsGrid,deliveryInformation,paymentInformation,finalView,accountInformation);

		startView.toFront();
		updateStackPane();


		hideCartList();
		showCashout();

		notificationPane= new NotificationPane();
		notificationPane.setShowFromTop(true);
		notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
		notificationPane.setContent(stackPane);
		AnchorPane.setRightAnchor(notificationPane,0.0);
		AnchorPane.setTopAnchor(notificationPane,0.0);
		AnchorPane.setLeftAnchor(notificationPane,0.0);
		AnchorPane.setBottomAnchor(notificationPane,0.0);
		stackContainer.getChildren().add(notificationPane);

	}


	@FXML
	void accountInfoAction(MouseEvent mouseEvent){
		accountInformation.initialize(null,null);
		accountInformation.loadCreditCardInformation();
		accountInformation.toFront();
		updateStackPane();
	}


	public void updateStackPane(){
		int i;
		for (i=0;i<stackPane.getChildren().size()-1;i++){
			stackPane.getChildren().get(i).setVisible(false);
		}
		stackPane.getChildren().get(i).setVisible(true);
	}

}
