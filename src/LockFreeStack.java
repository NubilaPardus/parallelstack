import java.util.concurrent.atomic.AtomicReference;
/**
 * This class that contains LockFreeStack functionality
 * The lock-free stack is a linked list, where the top field points to the first node (or null if the stack is empty.) 
 * For simplicity, we usually assume it is illegal to add a null value to a stack.
 * 
 * @author NubilaPardus
 * @name LockFreeStack
 * @implements BaseStack
 * @class
 */
public class LockFreeStack<T> implements BaseStack<T> {
	AtomicReference<Node<T>> top = new AtomicReference<Node<T>>(null);
	public static int MIN_DELAY; 
	public static int MAX_DELAY;
	public Backoff backoff;

	/**
	 * Constructor
	 * 
	 * @param int min
	 * @param int max
	 */
	LockFreeStack(int min, int max) {
		MIN_DELAY = min;
		MAX_DELAY = max;
		backoff = new Backoff(MIN_DELAY, MAX_DELAY);
	}

	/**
 	 * A tryPush() method calls putting operation (add an item to top of the stack). 
 	 * 	- If not succeed, this method is repeated after backing off.
 	 * 	- If it succeeds, push() returns
	 * 
	 * @param Node<T>
	 */
	protected boolean tryPush(Node<T> node) {
		Node<T> oldTop = top.get();
		node.next = oldTop;
		return (top.compareAndSet(oldTop,  node));
	}

	/**
	 * Push method of stack
	 * 
	 * @param <T> value
	 */
	@Override
	public void push(T value) throws InterruptedException {
		Node<T> node = new Node<T>((T)value);

		while(true) {
			if(tryPush(node)) {
				return;
			} else {
				backoff.backoff();
			}
		}
	}

	/**
	 * A tryPop() method calls remove operation (uses compareAndSet() to remove an item,throws an exception from an empty stack). 
 	 * 	- If not succeed, this method is repeated.
 	 * 	- If it succeeds, this method returns the node and pop() returns the value from the removed node.
	 * 
	 * @returns Node<T>
	 */
	protected Node<T> tryPop() throws Exception {
		Node<T> oldTop = top.get();
		if(oldTop == null)
			throw new Exception("Its empty!");

		Node<T> newTop = oldTop.next;
		if(top.compareAndSet(oldTop,  newTop))
			return oldTop;
		else
			return null;
	}

	/**
	 * Pop method of stack
	 * 
	 * @returns <T>
	 */
	@Override
	public T pop() throws Exception, InterruptedException {
		while(true) {
			Node<T> returnNode = tryPop();
			if(returnNode != null)
				return returnNode.value;
			else
				backoff.backoff();
		}
	}

}
