package exception;

@SuppressWarnings("serial")
public class IllegalUseOfConditionException extends Exception{

	public String getMessage(){
		return "Non è possibile eseguire await o signal su una variabile condition senza" +
				"prima aver effettuato la lock.";
		
	}

}
