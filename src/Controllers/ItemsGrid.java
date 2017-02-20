package Controllers;

import Commons.Inventory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/4/17.
 */
public class ItemsGrid extends AnchorPane implements Initializable {


	@FXML
	AnchorPane anchorPane,paneLoadMore;

	@FXML
	ScrollPane scrollPane;

	@FXML
	FlowPane container;

	private int rows=0, columns =0;

	private final static int ITEMS_PER_ROW=3;
	private final static int ITEMVIEW_HEIGHT=309;
	private final static int ITEMVIEW_WIDTH=239;


	public List<Product> items=new ArrayList<>();


	final private int ITEMS_PER_SCREEN=20;

	private int currentIndex=-1;

	/*
	Adds an Item object to the grid, creating an ItemView
	 */
	public void addItem(Product item){

		items.add(item);

		if (items.size()<ITEMS_PER_SCREEN) {
			container.getChildren().add(new ItemView(item));
			currentIndex++;
		}
		//scrollPane.setVvalue(0);
	}


	private void flush(){
		for (int i=0;(currentIndex<items.size()) && (i<ITEMS_PER_SCREEN);i++,currentIndex++){
			container.getChildren().add(new ItemView(items.get(currentIndex)));
		}

		System.out.println("Curr index:"+currentIndex);
		if (currentIndex!=items.size()) {
			setLoadMoreVisibilty(true);
		}
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

	public void reset(){
		currentIndex=0;
		items.clear();
		container.getChildren().clear();
		setLoadMoreVisibilty(false);
		scrollPane.setVvalue(0);
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

	}
}
