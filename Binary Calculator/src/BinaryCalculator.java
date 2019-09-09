/*Jesualdo Cristovao
Binary Calculator
March 1, 2018*/

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class BinaryCalculator {
	public static void main(String[] args) throws DivideByZeroException {
		System.out.println("Welcome to the BinaryCalculator");
		Scanner file = null;
		PrintWriter writeFile = null;

		try {
			file = new Scanner(new FileInputStream("commands.txt"));
			writeFile = new PrintWriter(new FileWriter("myResults.txt"));
			writeFile.println("Welcome to the BinaryCalculator");
		} catch (FileNotFoundException e) {
			System.out.println("File Not found. Exiting...");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			int i = 0;
			while (i < 10) {
				i++;
				String arg1 = " ";
				String arg2 = " ";
				String operator = " ";

				if (file.hasNext()) {
					arg1 = file.next();
				}

				if (arg1.equalsIgnoreCase("QUIT")) {
					break;
				}

				if (file.hasNext()) {
					operator = file.next();
				}

				if (file.hasNext()) {
					arg2 = file.next();
				}

				if (arg1.length() != arg2.length()) {
					System.err.println("ERROR: '" + arg1 + "' and '" + arg2 + "' are not the same length.");
					continue;
				}

				if (arg1.length() == arg2.length()) {

					boolean[] bits1 = string2bits(arg1);
					boolean[] bits2 = string2bits(arg2);
					String result = " ";

					if (operator.equals("+")) {
						System.out.println(bits2decimal(bits1) + " + " + bits2decimal(bits2) + " = "
								+ bits2decimal(addition(bits1, bits2)));
						result = bits2decimal(bits1) + " + " + bits2decimal(bits2) + " = "
								+ bits2decimal(addition(bits1, bits2));
					}
					if (operator.equals("-")) {
						System.out.println(bits2decimal(bits1) + " - " + bits2decimal(bits2) + " = "
								+ bits2decimal(subtraction(bits1, bits2)));
						result = bits2decimal(bits1) + " - " + bits2decimal(bits2) + " = "
								+ bits2decimal(subtraction(bits1, bits2));
					}
					if (operator.equals("*")) {
						System.out.println(bits2decimal(bits1) + " * " + bits2decimal(bits2) + " = "
								+ bits2decimal(multiplication(bits1, bits2)));
						result = bits2decimal(bits1) + " * " + bits2decimal(bits2) + " = "
								+ bits2decimal(multiplication(bits1, bits2));
					}
					if (operator.equals("/")) {
						try {
							if (bits2decimal(bits2) == 0) {
								throw new DivideByZeroException(" Error! Cannot divide by Zero");
							}

							System.out.println(bits2decimal(bits1) + " / " + bits2decimal(bits2) + " = "
									+ bits2decimal(division(bits1, bits2)));
							result = bits2decimal(bits1) + " / " + bits2decimal(bits2) + " = "
									+ bits2decimal(division(bits1, bits2));

						} catch (DivideByZeroException e) {
							System.out.println(e.getMessage());
						}
					}
					writeFile.println(result);

				} else {
					break;
				}
			}
		}
		writeFile.close();
		file.close();
	}

	// TODO: using the "operator" variable, do the appropriate operations

	// TODO: print out the result in both binary and decimal.

	// Binary (for debugging):
	// TODO: always use "System.err" for debugging in this project
	// System.err.println(String.format("DEBUG: %s %s %s =
	// TBD",bits2string(bits1),operator,bits2string(bits2)));
	// Decimal (actual output):
	// System.out.println(String.format("%d %s %d =
	// TBD",bits2decimal(bits1),operator,bits2decimal(bits2)));}in.close();}

	private static boolean[] string2bits(String arg1) {
		char[] bits = arg1.toCharArray();
		boolean boolArray[] = new boolean[arg1.length()];
		for (int i = 0; i < arg1.length(); i++) {

			if (bits[i] == '1') {
				boolArray[i] = true;
			}
			if (bits[i] == '0') {
				boolArray[i] = false;
			}
		}
		// TODO: write the code that converts a string like "010101" into an array of
		// booleans
		// TODO: take care of the order of bits. The character at position 0 in the
		// string, is the MSB
		return boolArray;
	}

	private static String bits2string(boolean[] bits) {
		char[] bit = new char[bits.length];

		for (int i = 0; i < bits.length; i++) {
			if (bits[i])
				bit[i] = '1';
			else {
				bit[i] = '0';
			}
		}
		String bitString = new String(bit);
		// TODO: write the code that converts an array of bits back into a String of '0'
		// and '1' chars.
		// TODO: make sure you treat the order of bits the same as you do in
		// string2bits.
		return bitString;

	}

	private static long bits2decimal(boolean[] bits) {
		long decimal = 0;
		int index = 0;
		long sum = 0;
		boolean newBit = bits[0];

		if (bits[0]) {
			bits = twosComplement(bits);
		}

		for (int i = bits.length - 1; i >= 0; i--) {
			if (bits[i] == true) {
				sum += (long) (Math.pow(2, index));
			}
			index++;
		}
		if (newBit) {
			decimal += sum;
			decimal = (-1) * decimal;
		} else {
			decimal += sum;
		}
		return decimal;
		// TODO: write the code that converts an array of bits into an integer value.
		// TODO: make sure you treat the order of bits the same as you do in
		// string2bits.

	}

	private static boolean[] addition(boolean[] bits1, boolean[] bits2) {
		int length = bits1.length;
		boolean[] sum = new boolean[length];
		boolean carryIn = false;

		int i = length - 1;

		while (i >= 0) {
			// Full Adder
			sum[i] = (bits1[i] ^ bits2[i]) ^ carryIn;
			carryIn = (bits1[i] && bits2[i]) || ((bits1[i] ^ bits2[i]) && carryIn);
			i--;
		}
		return sum;
	}

	private static boolean[] subtraction(boolean[] bits1, boolean[] bits2) {
		int length = bits1.length;
		boolean[] sub = new boolean[length];
		boolean[] newBits2 = new boolean[length];

		// Subtraction using Full Adder and twos Complement
		newBits2 = twosComplement(bits2);
		sub = addition(bits1, newBits2);
		return sub;
	}

	private static boolean[] multiplication(boolean[] bits1, boolean[] bits2) {
		int length = bits1.length;
		boolean[] result = new boolean[length];//
		boolean[] mult = new boolean[length];//

		for (int i = 0; i < length; i++) {
			mult[length - (length - i)] = bits1[i];
		}

		for (int i = 0; i < length; i++) {
			if (bits2[length - 1 - i]) {
				result = addition(result, leftShift(mult, i));
			}
		}
		return result;
	}

	private static boolean[] leftShift(boolean[] bits, int n) {
		boolean[] shift = new boolean[bits.length];

		int i = bits.length - 1;

		while (i > 0) {
			shift[i - 1] = bits[i];
			i--;
		}
		shift[bits.length - 1] = false;

		if (n == 0) {
			return bits;
		}
		if (n == 1) {
			return shift;
		} else {
			return leftShift(shift, n - 1);
		}
	}

	private static boolean[] division(boolean[] bits1, boolean[] bits2) {
		int length = bits1.length;
		boolean[] result = new boolean[length];

		return result;
	}

	private static boolean[] twosComplement(boolean[] bits) {
		boolean[] result = new boolean[bits.length];
		boolean carryIn = true;
		int i = bits.length - 1;
		while (i >= 0) {
			result[i] = (!bits[i] ^ carryIn);
			carryIn = (!bits[i] && carryIn);
			i--;
		}
		return result;
	}

}
