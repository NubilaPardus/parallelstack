import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * This class that contains EliminationArray functions and parameters
 * In Elimination Array class, a thread can dynamically select the lower range of the array from a random selected slot.
 * 
 * @author NubilaPardus
 * @name EliminationArray
 * @class
 */
class EliminationArray<T> {
	private int duration;
	private Random random;
	private LockFreeExchanger<T>[] lockFreeExchanger;

	@SuppressWarnings("unchecked")
	EliminationArray(int capacity, int duration) {
		this.duration = duration;
		lockFreeExchanger = (LockFreeExchanger<T>[]) new LockFreeExchanger[capacity];
		for (int i = 0; i < capacity; i++) {
			lockFreeExchanger[i] = new LockFreeExchanger<T>();
		}
		random = new Random();
	}

	public T visit(T value, int range) throws TimeoutException {
		int slot = random.nextInt(range);
		return (lockFreeExchanger[slot].exchange(value, duration, TimeUnit.MILLISECONDS));
	}
}