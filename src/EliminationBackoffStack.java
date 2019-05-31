import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicStampedReference;
/**
 * This class that contains EliminationBackoffStack functionality
 * 
 * @author NubilaPardus
 * @name EliminationBackoffStack
 * @extends LockFreeStack
 * @implements BaseStack
 * @class
 */
public class EliminationBackoffStack<T> extends LockFreeStack<T> implements BaseStack<T> {
	private EliminationArray<T> eliminationArray;
	private static ThreadLocal<RangeHelper> rangeHelperPolicy;

	/**
	 * Constructor
	 * 
	 * @param int exchangerCapacity
	 * @param int exchangerWaitDuration
	 */
	EliminationBackoffStack(final int exchangerCapacity, int exchangerWaitDuration) {
		super(0, 0);
		eliminationArray = new EliminationArray<T>(exchangerCapacity, exchangerWaitDuration);
		rangeHelperPolicy = new ThreadLocal<RangeHelper>() {
			protected synchronized RangeHelper initialValue() {
				return new RangeHelper(exchangerCapacity);
			}
		};
	}

	/**
	 * Push method of stack
	 * 
	 * @param <T> value
	 */
	public void push(T value) {
		RangeHelper rangePolicy = rangeHelperPolicy.get();
		Node<T> node = new Node<T>(value);

		while (true) {
			if (this.tryPush(node))
				return;
			else {
				try {
					T visitedValue = eliminationArray.visit(value, rangePolicy.getRange());
					if (visitedValue == null) {
						rangePolicy.recordEliminationSuccess();
						return;
					}
				} catch (TimeoutException e) {
					rangePolicy.recordEliminationTimeout();
				}
			}
		}
	}

	/**
	 * Pop method of stack
	 * 
	 * @returns <T>
	 */
	public T pop() throws Exception {
		RangeHelper rangePolicy = rangeHelperPolicy.get();

		while (true) {
			Node<T> returnNode = tryPop();
			if (returnNode != null)
				return returnNode.value;
			else {
				try {
					T visitedValue = eliminationArray.visit(null, rangePolicy.getRange());
					if (visitedValue != null) {
						rangePolicy.recordEliminationSuccess();
						return visitedValue;
					}
				} catch (TimeoutException e) {
					rangePolicy.recordEliminationTimeout();
				}
			}
		}
	}
}
