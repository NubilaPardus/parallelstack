import java.util.ArrayList;

/**
 * Concurrent Stack with EliminationBackOff Strategy
 * 
 * @author NubilaPardus
 * @name Main
 * @class
 */
public class Main {
	public static double totalTimeToPop;
	public static double totalTimeToPush;
	
	private static int EXCHANGER_CAPACITY = 2;
	private static int EXCHANGER_WAIT_DURATION = 10;
	private static int NUMBER_OF_ITERATION = 5000;

	public static void main(String[] args) throws InterruptedException {

		System.out.println(">>>>  # of iteration: " + NUMBER_OF_ITERATION );
	
		doOperations(2);
		
		doOperations(3);
		
		doOperations(5);
		
		doOperations(8);
		
		doOperations(10);
		
		doOperations(12);
		
		doOperations(16);
		
		System.exit(0);
	}

	public static void doOperations(int numberOfThreads) throws InterruptedException {
		
		BaseStack<Integer> stack = new EliminationBackoffStack<Integer>(EXCHANGER_CAPACITY, EXCHANGER_WAIT_DURATION);
		Main.totalTimeToPush = 0;
		Main.totalTimeToPop = 0;
		
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new Thread(new MyThread(stack, NUMBER_OF_ITERATION));
		}
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].start();
		}
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].join();
		}
		/*System.out.println(" # of threads: " + numberOfThreads 
				+ " -- average time (ns) to push  for each iteration:" + Main.totalTimeToPush / numberOfThreads 
				+ " -- average time (ns) to pop for each iteration:" + Main.totalTimeToPop / numberOfThreads);
		*/
		System.out.println(" # of threads: " + numberOfThreads 
				+ " -- average thread working time (ns) for each iteration:" + (Main.totalTimeToPush+ Main.totalTimeToPop)/ numberOfThreads);
	}

}
