import Commons.Inventory;
import Commons.MostBoughtManager;
import Commons.Receipt;
import Commons.ReceiptManager;
import Controllers.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by latiif on 2/1/17.
 */
public class Main extends Application{
	public static void main(String[] args) throws ItemAddedException {



		launch(args);
	}


	private void prepare(){
		Inventory.getInstance();
		ReceiptManager.readAll();
		MostBoughtManager.readAll();


	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		prepare();


		Font.loadFont(getClass().getResourceAsStream("Controllers/Fonts/TypewriterCondensed/typewcond_regular.otf"), 14);


		primaryStage.setTitle("iMat");
		primaryStage.getIcons().add(new Image("Controllers/Icons/imat-icon.png"));

		Parent root = new ShopView();


		Scene mainScene= new Scene(root);

		primaryStage.setMinHeight(720);


		primaryStage.setScene(mainScene);
		primaryStage.show();


		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				ReceiptManager.saveAll();
				MostBoughtManager.saveAll();
				IMatDataHandler.getInstance().shutDown();
			}
		});



	}

	private static void showError(Thread thread, Throwable throwable){

		if (throwable instanceof ItemAddedException) {
			System.out.println(throwable.toString());
		}
	}
}
