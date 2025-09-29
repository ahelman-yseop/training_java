package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// Week 4 assignment.

		head = new LLNode<E>(null); // sentinel head
	    tail = new LLNode<E>(null); // sentinel tail
	    head.next = tail;
	    tail.prev = head;
	    size = 0;
	}

	
	/**
	 * Appends an element to the end of the list
	 * @param element 	The element to add
	 */
	public boolean add(E element) {
		// Week 4 assignment.
		
	    if (element == null) {
	        throw new NullPointerException("Cannot add null elements to the list.");
	    }
	    // Insert new node before tail sentinel
	    LLNode<E> newNode = new LLNode<>(element);
	    // Link newNode between tail.prev and tail
	    LLNode<E> prevNode = tail.prev;
	    prevNode.next = newNode;
	    newNode.prev = prevNode;

	    newNode.next = tail;
	    tail.prev = newNode;
	    
	    size++;
	    return true;
	}

	
	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) {
		// Week 4 assignment.

	    // Bounds check
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }

	    // Start from head sentinel
	    LLNode<E> current = head.next; // skip dummy head

	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    return current.data;
	}

	
	/**
	 * Add an element to the list at the specified index
	 * @param 			The index where the element should be added
	 * @param element 	The element to add
	 */
	public void add(int index, E element) {
		// Week 4 assignment.
		
	    if (element == null) {
	        throw new NullPointerException("Cannot add null elements to the list.");
	    }

	    if (index < 0 || index > size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }

	    // Traverse to the node currently at the target index (or tail if index == size)
	    LLNode<E> current = head;

	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    // current is now the node BEFORE where we want to insert
	    LLNode<E> nodeAfter = current.next;
	    LLNode<E> newNode = new LLNode<E>(element);

	    // Insert newNode between current and nodeAfter
	    newNode.prev = current;
	    newNode.next = nodeAfter;
	    current.next = newNode;
	    nodeAfter.prev = newNode;

	    size++;
	}


	/** Return the size of the list */
	public int size() {
		// Week 4 assignment.

		return size;
	}

	
	/** Remove a node at the specified index and return its data element.
	 * @param index 	The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		// Week 4 assignment.
		
		// Check bounds
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }

	    // Start from head sentinel
	    LLNode<E> current = head.next;

	    // Move to the node at the specified index
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    // Remove current
	    LLNode<E> prevNode = current.prev;
	    LLNode<E> nextNode = current.next;

	    prevNode.next = nextNode;
	    nextNode.prev = prevNode;

	    size--;

	    return current.data;
	}

	
	/**
	 * Set an index position in the list to a new element
	 * @param index 	The index of the element to change
	 * @param element 	The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {
		// Week 4 assignment.

		if (element == null) {
	        throw new NullPointerException("Cannot set null element in the list.");
	    }

	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }

	    // Traverse to the node at the given index (skipping head sentinel)
	    LLNode<E> current = head.next;
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    // Replace data
	    E oldData = current.data;
	    current.data = element;

	    return oldData;
	}   
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// Week 4 assignment: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	

	
}
