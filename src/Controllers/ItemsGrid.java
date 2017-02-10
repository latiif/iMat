package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by latiif on 2/4/17.
 */
public class ItemsGrid extends ScrollPane implements Initializable {


	@FXML
	AnchorPane anchorPane;

	@FXML
	FlowPane container;

	private int rows=0, columns =0;

	private final static int ITEMS_PER_ROW=3;
	private final static int ITEMVIEW_HEIGHT=309;
	private final static int ITEMVIEW_WIDTH=239;


	private List<ItemView> items=new ArrayList<>();



	/*
	Adds an Item object to the grid, creating an ItemView
	TODO replaec String Item with Item item, when the backend arrives
	 */
	public void addItem(String item){

		ItemView t= new ItemView(item);

		/*
		if (columns ==3){
			columns =0;
			container.addRow(++rows);
			anchorPane.setMinHeight((rows+1)*ITEMVIEW_HEIGHT);


		}

		GridPane.setRowIndex(t,rows);
		GridPane.setColumnIndex(t, columns++);
		*/


		System.out.println(rows + " "+ columns);

		container.getChildren().add(t);

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		container.prefWidthProperty().bind(this.widthProperty());
		container.prefHeightProperty().bind(this.heightProperty());



	}
}
