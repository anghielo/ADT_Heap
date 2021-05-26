import java.io.*;
import java.util.Scanner;

public class HeapTest
{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException
	{
		PrintWriter printWriter = new PrintWriter("output.txt");
		File file = new File("data.txt");
		Scanner inputFile = new Scanner(file);
		
		String line = null;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		int count = 0;
		
		// Find out how many items are in the text document
		while ((line = bufferedReader.readLine()) != null)
			count++;
		
		// Create the array from the amount of items in the text document
		int[] intArray = new int [count];

		for(int i = 0; i < intArray.length; i++)
			intArray[i] = inputFile.nextInt();
		
		Heap<Integer> heap = new Heap<>();
		
		// Add sequential values
		for(int i = 0; i < intArray.length; i++)
			heap.add(intArray[i]);
		
		printer(printWriter, "\nHeap built using sequential insertions: " + heap.toString());
		printer(printWriter, "Number of swaps in the heap creation: " + heap.getSwaps());
		// Remove maximum value which is at the root 10 times
		for(int i = 0; i < 10; i++)
			heap.removeMax();
		
		printer(printWriter, "Heap after 10 removals: " + heap.toString());
		
		// Repeat same using optimal method
		Heap<Integer> heap2 = new Heap<>(intArray);
		
		
		printer(printWriter, "\nHeap built using optimal method: " + heap2.toString());
		printer(printWriter, "Number of swaps in the heap creation: " + heap2.getSwaps());
		
		for(int i = 0; i < 10; i++)
			heap2.removeMax();
		
		printer(printWriter,"Heap after 10 removals: " + heap2.toString());
		printWriter.close();
	}
	
	public static void printer(PrintWriter pw, String text)
	{
		pw.println(text);
		System.out.println(text);
	}
}
