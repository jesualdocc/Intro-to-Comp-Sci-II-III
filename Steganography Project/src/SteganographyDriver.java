/*Jesualdo Cristovao
Project 4 - Steganography
March 1, 2018*/

import java.io.File;
import java.util.Scanner;

public class SteganographyDriver {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		File path = new File("SecretMessage.wav");
		File path2 = new File("NewSecretMessage.wav");
		File path3 = new File("NewSecretMessage.wav");
		
		System.out.println("Enter a Message: ");
		String msg = keyboard.nextLine();
		
			try {
				Steganography.encodeMessage(path, path2, msg);
				String decodedMessage1 = Steganography.decodeMessage(path);
				String decodedMessage2 = Steganography.decodeMessage(path3);
				System.out.println(decodedMessage1);
				System.out.println();
				System.out.println(decodedMessage2);
				keyboard.close();
			} catch (SecretMessageException e) {
				
				e.printStackTrace();
		}
			catch (NotEnoughSpaceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}

