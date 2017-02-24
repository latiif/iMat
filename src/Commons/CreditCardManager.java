package Commons;

import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.IMatDataHandler;

/**
 * Created by latiif on 2/24/17.
 */
public class CreditCardManager {

	public static CreditCard getCreditCard(){
		if (IMatDataHandler.getInstance().getCreditCard().getCardNumber().equals("") &&
				IMatDataHandler.getInstance().getCreditCard().getHoldersName().equals("") &&
				IMatDataHandler.getInstance().getCreditCard().getValidYear()== 0 &&
				IMatDataHandler.getInstance().getCreditCard().getValidMonth()==0 &&
				IMatDataHandler.getInstance().getCreditCard().getVerificationCode()==0){
			return null;
		}
		else {
			return IMatDataHandler.getInstance().getCreditCard();
		}
	}

	public static void clearCreditCard(){
		IMatDataHandler.getInstance().getCreditCard().setCardNumber("");
		IMatDataHandler.getInstance().getCreditCard().setHoldersName("");
		IMatDataHandler.getInstance().getCreditCard().setValidMonth(0);
		IMatDataHandler.getInstance().getCreditCard().setValidYear(0);
		IMatDataHandler.getInstance().getCreditCard().setVerificationCode(0);
	}


	public static void setCreditCard(String holdersName,String cardNumber, int validMonth, int validYear, int cvc){
		IMatDataHandler.getInstance().getCreditCard().setCardNumber(cardNumber);
		IMatDataHandler.getInstance().getCreditCard().setHoldersName(holdersName);
		IMatDataHandler.getInstance().getCreditCard().setValidMonth(validMonth);
		IMatDataHandler.getInstance().getCreditCard().setValidYear(validYear);
		IMatDataHandler.getInstance().getCreditCard().setVerificationCode(cvc);
	}


}
