import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment3_Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
		String fileName = "Timing_262144.txt";
		ArrayList<String> file = new ArrayList<>();
		ArrayList<Integer> fileArray = new ArrayList<>();
		ArrayList<Integer> fileArray2 = new ArrayList<>();
		PrintWriter writeFile = null;
		
		Scanner in = new Scanner(new FileInputStream(fileName));

		while (in.hasNext()) {

			file.add(in.next());
		}

		in.close();
		
		for (int i = 0; i < file.size(); i++) {
			fileArray.add(Integer.valueOf(file.get(i)));
			fileArray2.add(Integer.valueOf(file.get(i)));
		}
		
		int sum = 0;
		int sum2 = 0;
		
		int numIterations = Math.max(4, 8192/fileArray.size());
		
		CpuTimer timer = new CpuTimer();
		for (int n = 0; n < numIterations; n++) {
		sum = CircularMaximumSubarray(fileArray);
		}
		
		float RecursiveAvg = (float) timer.getElapsedCpuTime()/numIterations;
		
		CpuTimer timer2 = new CpuTimer();
		for (int n = 0; n < numIterations; n++) {
		sum2 = CircularKardane(fileArray2);
		}
		
		float KardaneAvg = (float) timer2.getElapsedCpuTime()/numIterations;
		
		try
		{
			writeFile = new PrintWriter( new FileOutputStream("Timing.out262144.txt"));
		}
		
		catch(FileNotFoundException e)
	    {
	           System.out.println("File Not found. Exiting...");
	           System.exit(0);
	    }
		finally
		{
		String text = fileArray.size() + ", \"R\", " + RecursiveAvg + ", " + sum;
		String text2 = fileArray2.size() + ", \"K\", " + KardaneAvg + ", " + sum2;
		writeFile.println(text);
		writeFile.println(text2);
		writeFile.close();
		}
	
	}
	
	public static int CircularMaximumSubarray(ArrayList<Integer> A) {
		//Wrapping for Circular Array 
		
		int sum = 0;
		
		int [] s = MaximumSubarray(A, 0, A.size()-1);
		
		int maxValue = s[2];
		
		for (int i = 0; i < A.size(); i++) {
			sum = sum + A.get(i);
			A.set(i, -1 * A.get(i));
		}
		
		s = MaximumSubarray(A, 0, A.size()-1);
		
		sum = sum + s[2];
		
		if (sum > maxValue) {
			return sum;
		}
		else {
			return maxValue;
		}
		
	}
	
	public static int CircularKardane(ArrayList<Integer> A) {
		
		//Wrapping for Circular Array 
		
		int sum = 0;
		
		int maxValue = Kadane_MaxSubSum(A);
		
		for (int i = 0; i < A.size(); i++) {
			sum = sum + A.get(i);
			A.set(i, -1 * A.get(i));
		}
			
		sum = sum + Kadane_MaxSubSum(A);
		
		if (sum > maxValue) {
			return sum;
		}
		else {
			return maxValue;
		}
		
	}
	
	public static int[] MaximumSubarray(ArrayList<Integer> A, int low, int high) {
	
		//From Pseudo-Code
		
		if (high == low) {
			return new int[] {low, high, A.get(low)};
		}
		
		else {
			int mid = (low + high)/2;
			int[] left = MaximumSubarray(A, low, mid); //{leftLow, leftHigh, leftSum}
			int[] right = MaximumSubarray(A, mid + 1, high); //{rightLow, rightHigh, rightSum}
			int[] cross = MaximumCrossingSubarray(A, low, mid, high); //{crossLow, crossHigh, crossSum};
			
			//Returns array of value - Index 2 contains sum
			if (left[2] >= right[2] && left[2] >= cross[2]) {
				return new int[] {left[0], left[1], left[2]};
			}
			else if (right[2] >= left[2] && right[2] >= cross[2]) {
				return new int[] {right[0], right[1], right[2]};
			}
			else {
				return new int[] {cross[0], cross[1], cross[2]};
			}
				
		}
		
	}
	
	public static int[] MaximumCrossingSubarray(ArrayList<Integer> A, int low, int mid, int high) {
		//From Pseudo-Code
		
		int leftSum = Integer.MIN_VALUE; //Replace -Infinity
		int sum = 0;
		int maxLeft = 0;
		
		for(int i = mid; i >= low; i--) {
			
			sum = sum + A.get(i);

			if(sum > leftSum) {
				leftSum = sum;
				maxLeft = i;
			}
		}
		
		int rightSum = Integer.MIN_VALUE; //Replace -Infinity
		int maxRight = 0;
		sum = 0;
		
		for(int j = mid + 1; j <= high; j++) {
			
			sum = sum + A.get(j);
			
			if(sum > rightSum) {
				rightSum = sum;
				maxRight = j;
			}
		}
		
		return new int[] {maxLeft, maxRight, leftSum + rightSum};	
	}
	
	public static int Kadane_MaxSubSum(ArrayList<Integer> A) {
		
		//From Pseudo-Code
		
		int maxSumSoFar = 0;
		int maxSumTok = 0;
		
		for (int k = 0; k < A.size(); k++) {
			maxSumTok = maxSumTok + A.get(k);
			if(maxSumTok < 0) {
				maxSumTok = 0;
			}
			if(maxSumSoFar < maxSumTok) {
				maxSumSoFar = maxSumTok;
			}
		}
		return maxSumSoFar;
	}

}
