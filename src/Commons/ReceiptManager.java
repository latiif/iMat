package Commons;

import org.apache.commons.io.FileUtils;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by latiif on 2/20/17.
 */
public class ReceiptManager {

	public static List<Receipt> receipts= new ArrayList<>();


	public static void saveAll(){

		File file= new File(IMatDataHandler.getInstance().imatDirectory()+"/receipts.list");

		try (ObjectOutputStream oos =
					 new ObjectOutputStream(new FileOutputStream(file))) {

			oos.writeObject(receipts);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void readAll(){
		File file= new File(IMatDataHandler.getInstance().imatDirectory()+"/receipts.list");

		try (ObjectInputStream oos =
					 new ObjectInputStream(new FileInputStream(file))) {

			receipts.addAll((List<Receipt>)oos.readObject());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
