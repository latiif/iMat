package Controllers;

import Commons.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by latiif on 2/4/17.
 */
public class ItemsGrid extends AnchorPane implements Initializable {


	private ToggleGroup toggleGroup=new ToggleGroup();

	@FXML
	AnchorPane anchorPane,paneLoadMore;
	@FXML
	HBox catHBox;

	@FXML
	ScrollPane scrollPane;

	@FXML
	FlowPane container;

	@FXML
	ToggleButton btnDefault,btnPrice,btnAlpha;

	private int rows=0, columns =0;

	private final static int ITEMS_PER_ROW=3;
	private final static int ITEMVIEW_HEIGHT=309;
	private final static int ITEMVIEW_WIDTH=239;

	Hashtable<ToggleButton,ProductCategory> categories=new Hashtable<>();

	public List<Product> items=new ArrayList<>();


	final private int ITEMS_PER_SCREEN=20;

	private int currentIndex=-1;

	/*
	Adds an Item object to the grid, creating an ItemView
	 */
	public void addItem(Product item){



		if (!categories.containsValue(item.getCategory())){//add a new category-button pair
			ToggleButton toggleButton= new ToggleButton();
			toggleButton.setText(Translator.getSwedishName(item.getCategory()));
			toggleButton.setSelected(false);
			toggleButton.setToggleGroup(toggleGroup);

			if (categories.size()>10){
				catHBox.setVisible(false);
			}else {

				catHBox.setVisible(true);
				catHBox.getChildren().add(toggleButton);
			}

			toggleButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					updateFromCategories();
				}
			});

			categories.put(toggleButton,item.getCategory());
		}


		if (!items.contains(item)){
			items.add(item);
			if (items.size()<ITEMS_PER_SCREEN) {
				container.getChildren().add(new ItemView(item));
				currentIndex++;
			}
		}
		else{
			container.getChildren().add(new ItemView(item));
		}



	}


	private void flush(){

		if (items.size()==0){
			return;
		}

		for (int i=0;(currentIndex<items.size()) && (i<ITEMS_PER_SCREEN);i++,currentIndex++){
			container.getChildren().add(new ItemView(items.get(currentIndex)));
		}

		System.out.println("Curr index:"+currentIndex);
		if (currentIndex!=items.size()) {
			setLoadMoreVisibilty(true);
		}
		else{
			setLoadMoreVisibilty(false);
		}
	}




	private static boolean listenersAdded=false;
	private void addListeners(){
		if (listenersAdded){
			return;
		}else {
			listenersAdded=true;
		}

		btnDefault.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue){

					List<Product> newItems;
					if (!visibleItems.isEmpty()){
						newItems=visibleItems;
					}
					else {
						newItems=items;
					}

					reset_local();

					Collections.sort(newItems,new ItemComparatorDefault());

					for(Product product:newItems){
						addItem(product);
					}
				}
				else {
					if (!btnAlpha.isSelected() && !btnPrice.isSelected() && !btnDefault.isSelected()){
						btnDefault.setSelected(true);
					}
				}
			}
		});


		btnPrice.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue){
					List<Product> newItems;
					if (!visibleItems.isEmpty()){
						newItems=visibleItems;
					}
					else {
						newItems=items;
					}

					reset_local();

					Collections.sort(newItems,new ItemComparatorPrice());

					for(Product product:newItems){
						addItem(product);
					}


				}
				else {
					if (!btnAlpha.isSelected() && !btnDefault.isSelected() && !btnPrice.isSelected()){
						btnPrice.setSelected(true);
					}
				}
			}
		});

		btnAlpha.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue){
					List<Product> newItems;
					if (!visibleItems.isEmpty()){
						newItems=visibleItems;
					}
					else {
						newItems=items;
					}

					reset_local();

					Collections.sort(newItems,new ItemComparatorAlphabetically());

					for(Product product:newItems){
						addItem(product);
					}
				}else {
					if (!btnAlpha.isSelected() && !btnPrice.isSelected() && !btnDefault.isSelected()){
						btnAlpha.setSelected(true);
					}
				}
			}
		});

	}


	public ItemsGrid(){
		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("FXMLFiles/ItemsGrid.fxml"));



		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}


	List<ProductCategory> getCurrCats(){
		List<ProductCategory> result= new ArrayList<>();



		for(Node node:catHBox.getChildren()){
			ToggleButton toggleButton= (ToggleButton)node;

			if (toggleButton.isSelected()){
				result.add(categories.get(toggleButton));
			}
		}

		return result;

	}


	List<Product> visibleItems=new ArrayList<>();
	void updateFromCategories(){

		visibleItems.clear();
		container.getChildren().clear();
		List<ProductCategory> currCats= getCurrCats();

		boolean addAll=getCurrCats().isEmpty();

		for (Product product:items){
			if (addAll ||currCats.contains(product.getCategory())){
				visibleItems.add(product);
				container.getChildren().add(new ItemView(product));
			}

		}


		System.out.println("Wokring with:"+visibleItems.size()+" items");
	}


	void reset_local(){
		currentIndex=0;
		container.getChildren().clear();
		setLoadMoreVisibilty(false);
		scrollPane.setVvalue(0);
	}

	public void reset(){
		currentIndex=0;
		items.clear();
		categories.clear();
		catHBox.getChildren().clear();
		container.getChildren().clear();
		setLoadMoreVisibilty(false);
		scrollPane.setVvalue(0);
		visibleItems.clear();
	}


	private void setLoadMoreVisibilty(boolean visible){
		if (visible){
			AnchorPane.setBottomAnchor(scrollPane,paneLoadMore.getHeight());
			paneLoadMore.setVisible(true);

		}
		else {
			AnchorPane.setBottomAnchor(scrollPane,0.0);
			paneLoadMore.setVisible(false);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		addListeners();

		Inventory.shopView.showCashout();

		container.prefWidthProperty().bind(scrollPane.widthProperty());
		container.prefHeightProperty().bind(scrollPane.heightProperty());


		scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.doubleValue()*100>95){
					flush();
				}
			}
		});

		flush();

	}


	@FXML
	private void loadMoreAction(MouseEvent mouseEvent){
		flush();
		scrollPane.setVvalue(0.94);
	}
}
