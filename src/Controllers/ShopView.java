package Controllers;

import Commons.CategoryManager;
import Commons.Inventory;
import Commons.ItemComparatorDefault;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;


import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.textfield.TextFields;
import se.chalmers.ait.dat215.project.*;


/**
 * Created by latiif on 2/6/17.
 */
public class ShopView extends AnchorPane implements Initializable {

	@FXML
	private Label lblBakery, lblFruit, lblMeat, lblMilk, lblPantry, lblCandy,lblHistory;

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
	 History history;
	 ConfrimView confrimView;

	 @FXML
	VBox boxCatContainer;

	 @FXML
	 AnchorPane paneSideFade,paneTopFade;
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
		Collections.sort(result,new ItemComparatorDefault());
		//Collections.reverse(result);

		for (Product product : result) {
			itemsGrid.addItem(product);
		}

		itemsGrid.toFront();
		updateStackPane();
		showCartList();
	}

	@FXML
	 void lblFavoriteOnAction(Event event) {
		itemsGrid.reset();
		List<Product> result=IMatDataHandler.getInstance().getProducts();
		Collections.sort(result,new ItemComparatorDefault());
		//Collections.reverse(result);
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


	List<Product> getProductsMainCategory(ProductCategory[] MainCategory){
		List<Product> res=new ArrayList<>();

		for (int i=0;i<MainCategory.length;i++){
			res.addAll(IMatDataHandler.getInstance().getProducts(MainCategory[i]));
		}

		return res;

	}



	private void selectCategory(Object object){

		Label label=(Label)object;
		for(Node node : boxCatContainer.getChildren()){
			Label temp=(Label) node;
			temp.getStyleClass().clear();
			temp.getStyleClass().add("label");
			if (temp.equals(label)){
				label.getStyleClass().add("lblActive");
			}



		}
	}

	@FXML
	private void lblBakeryOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : getProductsMainCategory(CategoryManager.BAGERI)) {
			itemsGrid.addItem(product);
		}
		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
		selectCategory(event.getSource());
	}

	@FXML
	private void lblDairyOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : getProductsMainCategory(CategoryManager.MEJERI)) {
			itemsGrid.addItem(product);
		}
		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
		selectCategory(event.getSource());
	}

	@FXML
	private void lblFruitVegOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : getProductsMainCategory(CategoryManager.FRUKT_OCH_GROENT)) {
			itemsGrid.addItem(product);
		}
		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
		selectCategory(event.getSource());
	}


	@FXML
	private void lblPantryOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : getProductsMainCategory(CategoryManager.SKAFFERI)) {
			itemsGrid.addItem(product);
		}
		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
		selectCategory(event.getSource());
	}

	@FXML
	private void lblMeatOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : getProductsMainCategory(CategoryManager.KOETT_OCH_FISK)) {
			itemsGrid.addItem(product);
		}
		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
		selectCategory(event.getSource());
	}

@FXML
	private void lblCandyOnAction(MouseEvent event) {
		itemsGrid.reset();
		for (Product product : getProductsMainCategory(CategoryManager.GODIS)) {
			itemsGrid.addItem(product);
		}
		itemsGrid.toFront();
		updateStackPane();
		showCashout();
		showCartList();
	selectCategory(event.getSource());
	}


	void removeShadow(){
		paneSideFade.setVisible(false);
		paneTopFade.setVisible(false);
	}

	void dropShadow(){
		paneSideFade.setVisible(true);
		paneTopFade.setVisible(true);
	}

	void hideCashout(){
		cartList.btnCheckout.setVisible(false);
		dropShadow();
	}


	void showCashout(){
		removeShadow();
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
		history= new History();
		confrimView= new ConfrimView();

		itemsGrid.reset();
		Collections.sort(Inventory.productList,new ItemComparatorDefault());


		for (Product product : Inventory.productList) {
			itemsGrid.addItem(product);
		}

		TextFields.bindAutoCompletion(searchField, Inventory.getInstance().getNames());


		stackPane.getChildren().addAll(startView,itemsGrid,deliveryInformation,paymentInformation,finalView,accountInformation,history,confrimView);

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






		/*
		Fading Panes Controller
		 */

	}




	@FXML
	void accountInfoAction(MouseEvent mouseEvent){
		accountInformation.initialize(null,null);
		accountInformation.loadCreditCardInformation();
		accountInformation.toFront();
		updateStackPane();
	}

	@FXML
	void historyOnAction (MouseEvent event){
		history.initialize(null,null);

		history.toFront();
		updateStackPane();
	}

	public void updateStackPane(){
		int i;
		for (i=0;i<stackPane.getChildren().size()-1;i++){
			stackPane.getChildren().get(i).setVisible(false);
		}
		stackPane.getChildren().get(i).setVisible(true);
	}

	@FXML
	private void homeOnClicked(MouseEvent event){
		startView.initialize(null,null);
		startView.toFront();
		updateStackPane();
	}

}
