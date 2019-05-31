/**
 * This class that contains Node class
 * 
 * @author NubilaPardus
 * @name Node
 * @class
 */
public class Node<T> {
	public T value;
	public Node<T> next;

	public Node(T value) {
		this.value = value;
		this.next = null;
	}
}