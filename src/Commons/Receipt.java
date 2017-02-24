package Commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by latiif on 2/20/17.
 */
public class Receipt implements Serializable {
	public List<String> items= new ArrayList<>();
	public double price;
	public String date;
}
