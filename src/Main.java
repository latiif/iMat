import Controllers.ItemAddedException;
import Controllers.ItemView;
import Controllers.ItemsGrid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by latiif on 2/1/17.
 */
public class Main extends Application{
	public static void main(String[] args) throws ItemAddedException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Item View");
		ItemsGrid itemsGrid = new ItemsGrid();

		//Thread.setDefaultUncaughtExceptionHandler(Main::showError);

		Parent root = itemsGrid;

		//System.out.print(getClass());

		//Parent root= FXMLLoader.load(getClass().getResource("Controllers/FXMLFiles/ItemView.fxml"));



		itemsGrid.addItem("Item1");
		itemsGrid.addItem("Item 2");

		itemsGrid.addItem("Item 3");

		itemsGrid.addItem("Item 4");


		Scene mainScene= new Scene(root);

		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	private static void showError(Thread thread, Throwable throwable){

		if (throwable instanceof ItemAddedException) {
			System.out.println(throwable.toString());
		}
	}
}
