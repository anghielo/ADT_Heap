import java.util.Arrays;

public final class Heap <T extends Comparable <? super T>> implements MaxHeapInterface<T>
{
	private T[] heap;												// Array of heap entries
	private int lastIndex;											// Index of last entry
	private int numberOfSwaps;
	private boolean initialized = false;							// Set this initially to false
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
	
	/** No-argument constructor sets the DEFAULT_CAPACITY for the array heap. */
	public Heap()
	{
		this(DEFAULT_CAPACITY);										// Call One-Arg constructor
	}
	
	// One-Arg constructor
	public Heap(int initialCapacity)
	{
		// Is initialCapacity too small?
		if(initialCapacity < DEFAULT_CAPACITY)
			initialCapacity = DEFAULT_CAPACITY;
		// Is initialCapacity too big?
		else
			checkCapacity(initialCapacity);
		
		@SuppressWarnings("unchecked")
		T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];	// Add 1 because array position 0 is not used
		heap = tempHeap;
		lastIndex = 0;												// Initialization
		numberOfSwaps = 0;
		initialized = true;
	}
	
	@SuppressWarnings("unchecked")
	public Heap(int[] entries)
	{
		numberOfSwaps = 0;
		Object [] tempHeap = new Object[entries.length + 1];  		// Add 1 because array position 0 is not used
		T[] tempHeap2 = (T[]) new Comparable[entries.length + 1];;  // Add 1 because array position 0 is not used
		
		heap = tempHeap2;
		
		lastIndex = entries.length;									// Initialization
		
		// Copy given array to data field
		for(int index = 0; index < entries.length; index++)
			tempHeap[index] = entries[index];
		
		for(int index = 0; index < entries.length; index++)
			heap[index + 1] = (T)tempHeap[index];
		
		initialized = true;
		
		// Create heap
		for(int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
			reheap(rootIndex);
	}
	
	/** Throws an exception if the client requests a capacity that is too large. */
	private void checkCapacity(int capacity)
	{
		if(capacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempted to create a heap whose capacity exceeds " +
											"allowed maximum of " + MAX_CAPACITY);
	}
	
	public T getMax()
	{
		checkInitialization();
		T root = null;
		if(!isEmpty())
			root = heap[1];
		return root;
	}
	
	public boolean isEmpty()
	{
		return lastIndex < 1;
	}
	
	public int getSize()
	{
		return lastIndex;
	}
	
	public void clear()
	{
		checkInitialization();
		while(lastIndex > -1)
		{
			heap[lastIndex] = null;							// Use this because we care about security
			lastIndex--;
		}
		lastIndex = 0;
	}
	
	public int getSwaps()
	{
		return numberOfSwaps;
	}
	
	public void add(T newEntry)								// O(log n) but it is used to create a heap then its O(n log n)
	{
		checkInitialization();
		int newIndex = lastIndex + 1;
		int parentIndex = newIndex / 2;
		while((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0)
		{
			heap[newIndex] = heap[parentIndex];
			newIndex = parentIndex;
			parentIndex = newIndex/2;
			numberOfSwaps++;								// Keep track of number of how many swaps are done
		}
		heap[newIndex] = newEntry;
		lastIndex++;
		ensureCapacity();
	}
	
	/** Doubles the size of the array heap.
    Precondition: checkInitialization() has been called. */
	private void ensureCapacity()
	{
		if(lastIndex >= heap.length - 1)					// If array is full, double its size
		{
			int newLength = 2 * heap.length;
			checkCapacity(newLength);						// Check that MAX_CAPACITY has not been reached
			heap = Arrays.copyOf(heap, newLength);			// O(n) because array elements have to be copied
		}
	}
	
	public T removeMax()
	{
		checkInitialization();									// Ensure initialization of data fields
		T root = null;
		
		if(!isEmpty())
		{
			root = heap[1];										// Return value
			heap[1] = heap[lastIndex];							// Form a semiheap
			heap[lastIndex] = null;								// Remove data from index for security reasons
			lastIndex--;										// Decrease size
			reheap(1);											// Transform to a heap
		}
		
		return root;
	}
	
	/** Checks the integrity of the object and throws an exception if this object is not initialized. */
	private void checkInitialization()
	{
		if(!initialized)
			throw new SecurityException("Array heap object is corrupt.");
	}
	
	// Downheap operation will also be used for smart way O(n)
	private void reheap(int rootIndex)
	{
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = 2 * rootIndex;
		
		while(!done && (leftChildIndex <= lastIndex))
		{
			int largerChildIndex = leftChildIndex;				// We assume the left value is the larger value initially
			int rightChildIndex = leftChildIndex + 1;
			
			if((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0)
				largerChildIndex = rightChildIndex;
			
			if(orphan.compareTo(heap[largerChildIndex]) < 0)	// Swap happens here
			{
				heap[rootIndex] = heap[largerChildIndex];
				rootIndex = largerChildIndex;
				leftChildIndex = 2 * rootIndex;
				numberOfSwaps++;								// Keep track of number of how many swaps are done
			}
			else
				done = true;
		}
		heap[rootIndex] = orphan;
	}
	
	public String toString()
	{
		String myString = "";
		for(int i = 1; i <= lastIndex; i++)
			myString += heap[i] + ",";
		
		return myString;
	}
}
