import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicStampedReference;
/**
 * This class that contains LockFreeExchanger functions and parameters
 * A Lock-Free Exchanger object allows two threads to exchange values of initial type of Object. 
 * This class has a single AtomicStampedReference field and has three possible states: EMPTY, BUSY, or WAITING.
 * 
 * @author NubilaPardus
 * @name LockFreeExchanger
 * @class
 */
class LockFreeExchanger<T> {
	static final int EMPTY = 0, WAITING = 1, BUSY = 2;
	AtomicStampedReference<T> slot = new AtomicStampedReference<T>(null, 0);

	public T exchange(T item, long timeout, TimeUnit timeUnit) throws TimeoutException {
		long nanos = timeUnit.toNanos(timeout);
		long timeBound = System.nanoTime() + nanos;
		int[] stampHolder = { EMPTY };

		while (true) {
			if (System.nanoTime() > timeBound)
				throw new TimeoutException();
			T yrItem = slot.get(stampHolder);
			int stamp = stampHolder[0];

			switch (stamp) {
			case EMPTY:
				if (slot.compareAndSet(yrItem, item, EMPTY, WAITING)) {
					while (System.nanoTime() > timeBound) {
						yrItem = slot.get(stampHolder);
						if (stampHolder[0] == BUSY) {
							slot.set(null, EMPTY);
							return yrItem;
						}
					}
					if (slot.compareAndSet(item, null, WAITING, EMPTY))
						throw new TimeoutException();
					else {
						yrItem = slot.get(stampHolder);
						slot.set(null, EMPTY);
						return yrItem;
					}
				}
				break;

			case WAITING:
				if (slot.compareAndSet(yrItem, item, WAITING, BUSY))
					return yrItem;
				break;

			case BUSY:
				break;

			default: // impossible;
			}
		}
	}

}
