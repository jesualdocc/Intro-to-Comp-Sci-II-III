/*Jesualdo Cristovao
Project 4 - Steganography
March 1, 2018*/

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;

public class Steganography {

	public static String decodeMessage(File inputFile) throws SecretMessageException {
		String decodedMessage = "";
		int messageLength = 0;
		long location;

		try {
			RandomAccessFile file = new RandomAccessFile(inputFile, "r");

			file.seek(50);
			messageLength = file.readInt();

			if (!inputFile.exists()) {
				file.close();
				throw new SecretMessageException("File Cannot be Found");
			}

			if (messageLength <= 0) {
				file.close();
				throw new SecretMessageException("File is Empty");
			}

			char[] msg = new char[messageLength];
			int i = 0;

			do {
				msg[i] = file.readChar();
				location = file.readLong();
				file.seek(location);
				i++;
			} while (i < messageLength || location != 0);

			decodedMessage = new String(msg);
			file.close();
		}

		catch (IOException e) {
			System.out.println("Caught IOException: " + e.getMessage());
		}

		catch (SecretMessageException e) {
			System.out.println(e.getMessage());
		}

		return decodedMessage;

	}

	public static void encodeMessage(File inputFile, File outputFile, String message)
			throws SecretMessageException, NotEnoughSpaceException {
		int lengthMsg = message.length();
		try {
			Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			RandomAccessFile file = new RandomAccessFile(outputFile.getPath(), "rw");
			long[] nextLocation = getDataLocations(50, outputFile.length() - 1, lengthMsg);
			file.seek(50);
			file.writeInt(lengthMsg);
			for (int i = 0; i < lengthMsg + 1; i++) {
				if (i != lengthMsg) {
					file.seek(nextLocation[i]);// 54
					file.writeChar(message.charAt(i));// a
					file.writeLong(nextLocation[i + 1]);
				}
			}
			file.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new SecretMessageException("Cannot Read the message");
		}
	}

	public static long[] getDataLocations(long start, long stop, int numLocations) throws NotEnoughSpaceException {
		long[] nextLocation = new long[numLocations + 1];
		long next = 0;
		int offset = (int) Math.abs((start - stop) / (numLocations));
		long space = start + 4 + 2 * numLocations + 8 * numLocations + offset;

		if (space > (stop - start)) {
			throw new NotEnoughSpaceException("Maximum Capacity Reached!");
		}
		for (int i = 0; i < nextLocation.length; i++) {
			// Position 54 would be first since the integer length takes 4 bytes
			nextLocation[i] = start + 4;
		}
		
		for (int i = 1; i < nextLocation.length; i++) {
			if (i != nextLocation.length - 1) {
				next += offset;
				nextLocation[i] += next;
			} else {
				nextLocation[i] = 0;
			}
		}
		return nextLocation;
	}

}
