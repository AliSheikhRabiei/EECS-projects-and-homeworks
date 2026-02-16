/***************************************
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
* Done all the work alone, no group.
****************************************/

import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 Treap is a partial implementation of linked list. 
 Elements are ordered solely by their order position.
 Each node stores a subtree size, enabling expected O(log n) 
 add(index), remove(index), and get(index). Iteration and toString are O(n).
 
 At first i used the treap example that was in pdf and done some edits and changed and implemented my own 
 But the professor said any copying even with citation will cost point so i abandoned that code and started from 0.
 In this version I did not copy any code from the book or any other sources, used only my own knowledge and past works so there is no citation. 
 However there are still some similarities to codes i have seen and read about before sorry i cannot unseen thing i have seen!
  
 @param <E> the type of elements maintained by this list
 */

public class TreapList<E> implements List<E> {

    /**
     Internal node class for the treap structure.
     Each node stores an element, priority, subtree size, and child references.
     @param <E> the type of element stored in the node
     */
    private static final class Node<E> {
        E item;
        int prio;            // heap priority
        int sz;              // subtree size
        Node<E> left, right;

        Node(E item, int prio) {
            this.item = item;
            this.prio = prio;
            this.sz   = 1;
        }
    }

    //fields
    private Node<E> root;
    private int size;                   // mirrors root.sz for O(1) size()
    private final Random rng = new Random(); // giving random priorities

    //constructors

    //for constructing empty list
    public TreapList() {
        root = null;
        size = 0;
    }

    //helpers
    
    /**
     Returns the size of the subtree rooted at the given node.
     @param n the node to get size for (may be null)
     @return the size of the subtree, or 0 if node is null
     */
    private static int sz(Node<?> n) { 
    	return (n == null) ? 0 : n.sz; 
    	}
    
    /**
     Updates the subtree size of a node based on its children's sizes.
     @param n the node to update (may be null)
     */
    private static <E> void pull(Node<E> n) {
        if (n != null) n.sz = 1 + sz(n.left) + sz(n.right);
    }

    /**
    Internal class holding the result of a split operation.
    Holder for split result. a has first k elements; b has the rest
    */
    private static final class Split<E> {
        final Node<E> a, b;
        Split(Node<E> a, Node<E> b) { this.a = a; this.b = b; }
    }

    /**
     Splits the treap into two treaps at the given index.
     The first treap contains elements [0, k-1], the second contains [k, end].
 	 @param t the root of the treap to split
     @param k the index at which to split
     @return a Split object containing the two resulting treaps
     */
    private static <E> Split<E> split(Node<E> t, int k) {
        if (t == null) return new Split<>(null, null);
        int lsz = sz(t.left);
        if (k <= lsz) {
            Split<E> sp = split(t.left, k);
            t.left = sp.b;
            pull(t);
            return new Split<>(sp.a, t);
        } else {
            Split<E> sp = split(t.right, k - lsz - 1);
            t.right = sp.a;
            pull(t);
            return new Split<>(t, sp.b);
        }
    }

    /**
     Merge two treaps a and b where all elements of a come before all elements of b
     All elements in the left treap must come before all elements in the right treap. 
     @param a the left treap (all elements less than right treap)
     @param b the right treap (all elements greater than left treap)
     @return the merged treap
     */
    private static <E> Node<E> merge(Node<E> a, Node<E> b) {
        if (a == null) return b;
        if (b == null) return a;
        if (a.prio < b.prio) {              
            a.right = merge(a.right, b);
            pull(a);
            return a;
        } else {
            b.left = merge(a, b.left);
            pull(b);
            return b;
        }
    }

    /**
     Finds the node at the specified index in the treap. 
     @param t the root of the treap to search
     @param i the index of the node to find
     @return the node at the specified index, or null if not found
     */
    private static <E> Node<E> nodeAt(Node<E> t, int i) {
        while (t != null) {
            int l = sz(t.left);
            if (i < l) {
                t = t.left;
            } else if (i == l) {
                return t;
            } else {
                i -= (l + 1);
                t = t.right;
            }
        }
        return null; //just in case
    }

    private void checkIndexExclusive(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
    }

    private void checkIndexInclusive(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
    }

    //Overrided List methods 
    
    //Appends e to the end in O(log n)
    @Override
    public boolean add(E e) {
        Node<E> n = new Node<>(e, rng.nextInt());
        Split<E> sp = split(root, size);       
        root = merge(merge(sp.a, n), sp.b);
        size++;
        return true;
    }

    //Inserts element at index, 0..size, in O(log n)
    @Override
    public void add(int index, E element) {
        checkIndexInclusive(index);
        Node<E> n = new Node<>(element, rng.nextInt());
        Split<E> sp = split(root, index);
        root = merge(merge(sp.a, n), sp.b);
        size++;
    }

    //Removes and returns the element at index, in O(log n)
    @Override
    public E remove(int index) {
        checkIndexExclusive(index);
        Split<E> ab = split(root, index);      //0..index-1 or index..end
        Split<E> mc = split(ab.b, 1);          //index or index+1..end
        E val = (mc.a == null) ? null : mc.a.item;
        root = merge(ab.a, mc.b);              // stitch back without the middle
        size--;
        return val;
    }

    //Returns the element at index, in O(log n).
    @Override
    public E get(int index) {
        checkIndexExclusive(index);
        return nodeAt(root, index).item;
    }

    //Number of elements, (O(1))
    @Override
    public int size() { return size; }

    //Removes all elements, (O(1)).
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    //In order iterator over the list, (O(n) total)
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            // manual stack for in-order traversal
            @SuppressWarnings("unchecked")
            private Node<E>[] stack = (Node<E>[]) new Node[32];
            private int sp = 0;
            private Node<E> cur = root;

            private void pushLeft(Node<E> x) {
                while (x != null) {
                    if (sp == stack.length) {
                        @SuppressWarnings("unchecked")
                        Node<E>[] bigger = (Node<E>[]) new Node[stack.length << 1];
                        System.arraycopy(stack, 0, bigger, 0, sp);
                        stack = bigger;
                    }
                    stack[sp++] = x;
                    x = x.left;
                }
            }

            @Override public boolean hasNext() {
                return cur != null || sp > 0;
            }

            @Override public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                pushLeft(cur);
                Node<E> x = stack[--sp];
                E v = x.item;
                cur = x.right;
                return v;
            }

            @Override public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    //Bracketed, comma separated format like AbstractCollection, (O(n))
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean first = true;

        //iterative in order without extra collections
        @SuppressWarnings("unchecked")
        Node<E>[] st = (Node<E>[]) new Node[32];
        int sp = 0;
        Node<E> x = root;
        while (x != null || sp > 0) {
            while (x != null) {
                if (sp == st.length) {
                    @SuppressWarnings("unchecked")
                    Node<E>[] bigger = (Node<E>[]) new Node[st.length << 1];
                    System.arraycopy(st, 0, bigger, 0, sp);
                    st = bigger;
                }
                st[sp++] = x;
                x = x.left;
            }
            x = st[--sp];
            if (!first) sb.append(", ");
            first = false;
            sb.append(String.valueOf(x.item));
            x = x.right;
        }

        sb.append(']');
        return sb.toString();
    }
    
    //all other List methods unsupported and throw exception
    
    //i originally wanted to implement these or just leave them be, 
    //but since it was not asked i assume they are meant to be like the rest.
    @Override public boolean equals(Object o) { throw new UnsupportedOperationException();}
    @Override public int hashCode() {throw new UnsupportedOperationException();}
    
    @Override public boolean isEmpty() { return size == 0; }
    @Override public E set(int index, E element) { throw new UnsupportedOperationException(); }
    @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(java.util.Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(int index, java.util.Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public int indexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public java.util.ListIterator<E> listIterator() { throw new UnsupportedOperationException(); }
    @Override public java.util.ListIterator<E> listIterator(int index) { throw new UnsupportedOperationException(); }
    @Override public List<E> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
}
