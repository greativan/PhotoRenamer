package exceptions;

/**
 * Throw an exception if the PhotoManager does not have 
 * the path corresponding to the name that user given.
 */

@SuppressWarnings("serial")
public class PathNotFoundException extends Exception{
	
	public PathNotFoundException(String message) {
		super(message);
	}
}
