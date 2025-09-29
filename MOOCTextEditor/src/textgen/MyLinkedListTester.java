/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove() {
	    // Remove first element
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// Week 4 assignment.
		
		// Remove last element
	    int lastIndex = list1.size() - 1;
	    int removedLast = list1.remove(lastIndex);
	    assertEquals("Remove: check last removed element is correct", (Integer)42, (Integer)removedLast);
	    assertEquals("Remove: check size decreased", 1, list1.size());
	    
	    
	    // Remove middle element (only if list has 3+ elements initially)
	    if (list1.size() >= 3) {
		    int middleIndex = list1.size() / 2;
		    int removedMiddle = list1.remove(middleIndex);
		    assertEquals("Remove: check middle removed element is correct", (Integer)21, (Integer)removedMiddle);
		    assertEquals("Remove: check size decreased after middle removal", 2, list1.size());
	    }
	    
	    // Try removing with invalid index (negative)
	    try {
	        list1.remove(-1);
	        fail("Remove: should throw IndexOutOfBoundsException for negative index");
	    } catch (IndexOutOfBoundsException e) {
	        // Expected
	    }
	    
	    // Try removing with invalid index (>= size)
	    try {
	        list1.remove(list1.size());
	        fail("Remove: should throw IndexOutOfBoundsException for out of bounds index");
	    } catch (IndexOutOfBoundsException e) {
	        // Expected
	    }
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd() {
        // Week 4 assignment.
		
		int initialSize = list1.size();

		Integer newElement = 99;  // Use Integer if list is Integer typed
	    assertTrue("Add should return true", list1.add(newElement));

	    assertEquals("Size should increase by 1 after add", initialSize + 1, list1.size());

	    assertEquals("Last element should be the newly added element", newElement, list1.get(list1.size() - 1));

	    try {
	        list1.add(null);
	        fail("Adding null should throw NullPointerException");
	    } catch (NullPointerException e) {
	        // expected
	    }
	}

	
	/** Test the size of the list */
	@Test
	public void testSize() {
		// Week 4 assignment
		
		// Initially empty list should have size 0
	    assertEquals("Empty list should have size 0", 0, emptyList.size());

	    // Add elements and check size increments
	    emptyList.add(1);
	    assertEquals("Size should be 1 after adding one element", 1, emptyList.size());

	    emptyList.add(2);
	    assertEquals("Size should be 2 after adding second element", 2, emptyList.size());

	    // Remove element and check size decrements
	    emptyList.remove(0);
	    assertEquals("Size should be 1 after removing one element", 1, emptyList.size());

	    // Clear list or remove remaining elements if clear() is not implemented
	    emptyList.remove(0);
	    assertEquals("Size should be 0 after removing all elements", 0, emptyList.size());

	    // Add and remove multiple times to test consistency
	    emptyList.add(10);
	    emptyList.add(20);
	    emptyList.add(30);
	    assertEquals("Size should be 3 after adding three elements", 3, emptyList.size());

	    emptyList.remove(1); // remove middle element
	    assertEquals("Size should be 2 after removing one element", 2, emptyList.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // Week 4 assignment
		
		// Start with an empty list
	    MyLinkedList<Integer> list = new MyLinkedList<>();

	    // Add to index 0 in empty list (valid)
	    list.add(0, 10);
	    assertEquals("First element should be 10", (Integer)10, list.get(0));
	    assertEquals("Size should be 1", 1, list.size());

	    // Add to beginning
	    list.add(0, 5);
	    assertEquals("First element should now be 5", (Integer)5, list.get(0));
	    assertEquals("Second element should be 10", (Integer)10, list.get(1));
	    assertEquals("Size should be 2", 2, list.size());

	    // Add to end
	    list.add(2, 20);  // index == size, valid
	    assertEquals("Last element should be 20", (Integer)20, list.get(2));
	    assertEquals("Size should be 3", 3, list.size());

	    // Add to middle
	    list.add(1, 7);
	    assertEquals("Element at index 1 should be 7", (Integer)7, list.get(1));
	    assertEquals("Element at index 2 should be 10", (Integer)10, list.get(2));
	    assertEquals("Size should be 4", 4, list.size());

	    // Add null (should throw)
	    try {
	        list.add(2, null);
	        fail("Adding null should throw NullPointerException");
	    } catch (NullPointerException e) {
	        // expected
	    }

	    // Invalid negative index
	    try {
	        list.add(-1, 99);
	        fail("Negative index should throw IndexOutOfBoundsException");
	    } catch (IndexOutOfBoundsException e) {
	        // expected
	    }

	    // Invalid index > size
	    try {
	        list.add(10, 99);  // current size is 4
	        fail("Out-of-bounds index should throw IndexOutOfBoundsException");
	    } catch (IndexOutOfBoundsException e) {
	        // expected
	    }
		
	}
	
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // Week 4 assignment.
		
	    // Prepare a list with known elements
	    MyLinkedList<Integer> list = new MyLinkedList<>();
	    list.add(10);
	    list.add(20);
	    list.add(30); // list = [10, 20, 30]

	    // Set first element
	    Integer oldFirst = list.set(0, 100);
	    assertEquals("Old value at index 0 should be 10", (Integer)10, oldFirst);
	    assertEquals("New value at index 0 should be 100", (Integer)100, list.get(0));

	    // Set middle element
	    Integer oldMiddle = list.set(1, 200);
	    assertEquals("Old value at index 1 should be 20", (Integer)20, oldMiddle);
	    assertEquals("New value at index 1 should be 200", (Integer)200, list.get(1));

	    // Set last element
	    Integer oldLast = list.set(2, 300);
	    assertEquals("Old value at index 2 should be 30", (Integer)30, oldLast);
	    assertEquals("New value at index 2 should be 300", (Integer)300, list.get(2));

	    // Size should remain unchanged
	    assertEquals("Size should remain 3 after set operations", 3, list.size());

	    // Try to set at negative index
	    try {
	        list.set(-1, 999);
	        fail("Setting at negative index should throw IndexOutOfBoundsException");
	    } catch (IndexOutOfBoundsException e) {
	        // expected
	    }

	    // Try to set at index == size (invalid)
	    try {
	        list.set(3, 999);  // size is 3; valid indices are 0–2
	        fail("Setting at index == size should throw IndexOutOfBoundsException");
	    } catch (IndexOutOfBoundsException e) {
	        // expected
	    }

	    // Try to set a null element (should throw)
	    try {
	        list.set(1, null);
	        fail("Setting null element should throw NullPointerException");
	    } catch (NullPointerException e) {
	        // expected
	    }
	    
	}
	
	
	// Week 4 assignment: Optionally add more test methods.
	
}
