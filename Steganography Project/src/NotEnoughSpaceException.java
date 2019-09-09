/*Jesualdo Cristovao
Project 4 - Steganography
March 1, 2018*/

public class NotEnoughSpaceException extends Exception {
	
	public NotEnoughSpaceException() {
		super("NotEnoughSpaceException");
	}

	public NotEnoughSpaceException(String message) {
		super(message);
	}

}
