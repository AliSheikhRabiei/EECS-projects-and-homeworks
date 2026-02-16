/***************************************
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
* Done all the work alone, no group.
****************************************/

import java.util.List;

/**
 * Doublylinked list with a mid pointer for faster speed.
 * API that are expected to be used are add(E), add(int,E), remove(int), get(int), size(), clear(), toString().
 * almost everything else, or at least anything i found and remembered, that is in List<E> throws UnsupportedOperationException.
 * @param <E> element type
 */

public class MidLinkedList<E> implements List<E> {

    //Node of doublylinked list.
    private static final class Node<E> {
        E item;
        Node<E> prev, next;
        Node(E item, Node<E> prev, Node<E> next) {
            this.item = item; this.prev = prev; this.next = next;
        }
    }

    private final Node<E> head;
    private final Node<E> tail;

    private int size;

    //Pointer to node at index floor(size/2) when size>0; null if empty
    private Node<E> mid;

    //Constructs an empty list.
    public MidLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, head, null);
        head.next = tail;
        size = 0;
        mid = null;
    }

    //size checks
    private void checkIndexExclusive(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
    }

    private void checkIndexInclusive(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
    }

    //Return node at index i using nearest of head or mid or tail
    private Node<E> nodeAt(int i) {
        int k = size / 2; 
        //checking Distance to each pointer
        int dMid;
        if (mid == null) {
            dMid = size;
        } else {
            dMid = Math.abs(i - k);
        }
        int dHead = i;                
        int dTail = (size - 1) - i;;              

        if (dMid <= dHead && dMid <= dTail) {
            Node<E> x = mid;
            if (i < k) { 
            	for (int p = k; p > i; --p) {
            		x = x.prev;
            	}
            }
            else if (i > k) {
            	for (int p = k; p < i; ++p) {
            		x = x.next;
            	}
            }
            return x;
        } else if (dHead <= dTail) {
            Node<E> x = head.next; 
            for (int p = 0; p < i; ++p) {
            	x = x.next;
            }
            return x;
        } else {
            Node<E> x = tail.prev; 
            for (int p = size - 1; p > i; --p) {
            	x = x.prev;
            }
            return x;
        }
    }
  
    //Insert new node with item before node
    private void linkBefore(E item, Node<E> before) {
        Node<E> prev = before.prev;
        Node<E> n = new Node<>(item, prev, before);
        prev.next = n;
        before.prev = n;
    }

    //Unlink node and return its element
    private E unlink(Node<E> x) {
        Node<E> p = x.prev, n = x.next;
        p.next = n; n.prev = p;
        E val = x.item;
        x.item = null; x.prev = x.next = null;
        return val;
    }

    //Appends e to the end.
    @Override
    public boolean add(E e) {
        int m = size;
        linkBefore(e, tail); 
        size++;

        if ((m%2) == 0) {
            mid = head.next;
        } else if ((m%2) == 1) {
            mid = mid.next;
        }
        else {
        	//we dont need to change mid if it is even
        }
        return true;
    }

    // Inserts element at position index (0..size).
    @Override
    public void add(int index, E element) {
        checkIndexInclusive(index);
        int m = size;
        int k = m / 2;

        Node<E> before = (index == m) ? tail : nodeAt(index);
        linkBefore(element, before);
        size++;

        if (m == 0) {
            mid = head.next;
        } else if ((m & 1) == 0) {
            if (index <= k) mid = mid.prev;   
        } else {
            mid = mid.next;
        }
    }

    // Removes and returns element at position index
    @Override
    public E remove(int index) {
        checkIndexExclusive(index);
        int m = size;
        int k = m / 2;
        Node<E> x = nodeAt(index);

        if (m > 0 && mid == null) {
            mid = nodeAt(k);
        }

        if (m == 1) {
            E v = unlink(x);
            head.next = tail; tail.prev = head;
            size = 0; mid = null;
            return v;
        }

        if ((m & 1) == 0) {
            mid = mid.prev;
        } else {
            if (index <= k) mid = mid.next;
        }

        E v = unlink(x);
        size--;
        return v;
    }

    //Returns element at index
    @Override
    public E get(int index) {
        checkIndexExclusive(index);
        return nodeAt(index).item;
    }

    // Number of elements
    @Override
    public int size() { 
    	return size; 
    }

    //Removes all elements
    @Override
    public void clear() {
        Node<E> x = head.next;
        while (x != tail) {
            Node<E> n = x.next;
            x.item = null; x.prev = null; x.next = null;
            x = n;
        }
        head.next = tail; tail.prev = head;
        size = 0; mid = null;
    }

    //Bracketed, comma-separated format.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node<E> x = head.next;
        boolean first = true;
        while (x != tail) {
            if (!first) sb.append(", ");
            first = false;
            sb.append(String.valueOf(x.item));
            x = x.next;
        }
        sb.append(']');
        return sb.toString();
    }

    //all other List methods unsupported

    @Override public boolean isEmpty() { return size == 0; }
    
    @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    @Override public java.util.Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(java.util.Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(int index, java.util.Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public E set(int index, E element) { throw new UnsupportedOperationException(); }
    @Override public int indexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public java.util.ListIterator<E> listIterator() { throw new UnsupportedOperationException(); }
    @Override public java.util.ListIterator<E> listIterator(int index) { throw new UnsupportedOperationException(); }
    @Override public java.util.List<E> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
//    { throw new UnsupportedOperationException(); }
//    { throw new UnsupportedOperationException(); }
//    { throw new UnsupportedOperationException(); }
//    { throw new UnsupportedOperationException(); }
//    { throw new UnsupportedOperationException(); }
    
    
    
}





