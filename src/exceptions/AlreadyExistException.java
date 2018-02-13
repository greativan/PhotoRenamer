package exceptions;

/**
 * To avoid there is a file that has the exactly same name with the 
 * name we are going to give to our file. 
 * Otherwise, two files will merge.
 */

@SuppressWarnings("serial")
public class AlreadyExistException extends Exception {
	
	public AlreadyExistException(String message) {
		super(message);
	}

}
