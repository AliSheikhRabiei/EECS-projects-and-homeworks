package Lab9;
import java.util.List;
import java.util.ArrayList;

/**
 * This class contains a number of methods that 
 * works on a wide range of data types. 
 */

/* This is a parameterized class. You should decide 
* what the parameter should look like in order to 
* pass all the test cases. 
*/

// you might want to change the parameter of this class to something more appropriate. 
public class Utility<T extends Comparable<T>> {
    public List<T> list;

    public Utility() {
        this.list = new ArrayList<>();
    }

    public Utility(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public List<T> getList() {
        return new ArrayList<>(list); 
    }
    
    public int linearSearch(T item) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (item.compareTo(list.get(i)) == 0) {
                position = i;
                break;
            }
        }
        return position;
    }
    
    public void mergeList(List<? super T> list) {
        for (T obj : this.list) {
            list.add(obj); 
        }
    }
    
    public boolean containList(List<T> list) {
        if (this.list.containsAll(list)) {
            this.list = new ArrayList<T>();
            for (T obj: list) {
                this.list.add(obj);
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    public static <E extends MyInteger> void removeZero(List<E> myList) {
        for (int i = 0; i < myList.size(); i++) {
            if (MyInteger.isZero(myList.get(i))) {
                myList.remove(i);
                i--;
            }
        }
    }
}





/**
 * This class implements a 2D points. 
 */
class Point implements Comparable<Point>{
	double x; 
	double y; 
	public Point(double x, double y) {
		this.x = x; 
		this.y = y;
	}
	@Override 
	public int compareTo(Point p) {
		if (this.x == p.x && this.y == p.y) return 0;
		else if (this.x == p.x) return 1;
		else return -1;	
	}
}

/**
 * This class represents an Integer number. 
 */
class MyInteger implements Comparable<MyInteger>{
    int x; 
    public MyInteger() {
        this.x = 0; 
    }
    public MyInteger(int x) {
        this.x = x; 
    }
    public static boolean isZero(MyInteger input) {
        return input.x == 0; 
    }
    @Override
    public int compareTo(MyInteger input) {
        if (this.x == input.x ) return 0;
        else if (this.x < input.x) return -1;
        else return 1;    
    }
}
/**
 * This class represents a subset of integer numbers that are natural numbers. 
 */
class NaturalNumber extends MyInteger{
    public NaturalNumber(int x) {
        super(x);
    }
}
/**
 * This class represents the odd natural numbers. 
 */

class OddNumber extends NaturalNumber{
    public OddNumber(int x) {
        super(x);
    }
	
}
/**
 * This class represents the even natural numbers. 
 */
class EvenNumber extends NaturalNumber{
    public EvenNumber(int x) {
        super(x);
    }
}

/**
 * This class implements a single connected list:
 * node1 -> node2 -> ...nodeLast-> null.
 * Each node of the list is of type {@code Node}.
 * The first node is reference by the instance variable {@code head}.
 * The second node in the list is {@code head.next} etc.
 * The list can have an arbitrary number of elements including being empty.
 */

class LinkedList {
	
	public Node head ;
	public LinkedList () {
		head = null ;
	}
	
	/**
	 * This method adds  <code> obj </code> to the end of the list.
	 * 
	 * @param obj
	 * @return the object added.
	 */
	
    public Node add(Object obj) {
        Node newNode = new Node(obj);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        return newNode;
    }
	
	/**
	 * This method removes the first occurrence of <code> obj </code> from the
	 * <code> linked list </code> if the object is in the list.
	 * 
	 */
	public void remove(Object obj) {
        if (head == null) return;
        
        if (head.obj.equals(obj)) {
            head = head.next;
            return;
        }
        
        Node current = head;
        while (current.next != null && !current.next.obj.equals(obj)) {
            current = current.next;
        }
        
        if (current.next != null) {
            current.next = current.next.next;
        }
	}
	
	/**
	 * This method returns a string representing the list as follows
	 * "[head.obj.toString(), ...]"
	 * or "[]" if it is an empty list
	 **/
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            sb.append(current.obj.toString());
            if (current.next != null) {
                sb.append(",");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
	
	/**
	 * This class implements a node of singled connected list.
	 */
	class Node {
		public Node(Object obj) {
			next = null ;
			this.obj = obj ;
		}
		Node next ;
		Object obj ;
	}

}
