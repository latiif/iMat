package Controllers;

/**
 * Created by latiif on 2/1/17.
 */
public class ItemAddedException extends Throwable {

	private int amount;

	private String item;

	public ItemAddedException(int amount, String item){
		this.amount=amount;
		this.item=item;
	}


	public int getAmount()
	{
		return this.amount;
	}

	public String getItem(){
		return this.item;
	}

	@Override
	public String toString(){
		return this.amount + "of item "+ this.item;
	}
}
