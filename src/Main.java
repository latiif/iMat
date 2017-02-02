import Controllers.ItemAddedException;
import Controllers.ItemView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
		ItemView itemView = new ItemView();

		//Thread.setDefaultUncaughtExceptionHandler(Main::showError);

		Parent root = itemView;

		//System.out.print(getClass());

		//Parent root= FXMLLoader.load(getClass().getResource("Controllers/FXMLFiles/ItemView.fxml"));




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
