/**
 * This class that contains a Thread implementation
 * 
 * @author NubilaPardus
 * @name MyThread
 * @extends Thread
 * @class
 */
public class MyThread extends Thread {
	double timeToPush = 0;
	double timeToPop = 0;
	final BaseStack<Integer> stack;
	final int numberOfIterations;

	/**
	 * Constructor
	 * @param {BaseStack<Integer>} stack
	 * @param  {int} numberOfIterations
	 */
	public MyThread(final BaseStack<Integer> stack,final int numberOfIterations){
		this.numberOfIterations = numberOfIterations;
		this.stack = stack;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < numberOfIterations; i++) {
			long startTimetoPush = System.nanoTime();
			
			try {
				stack.push(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			timeToPush += System.nanoTime() - startTimetoPush;
			
			long startTimeToPop = System.nanoTime();
			
			try {
				stack.pop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			timeToPop += System.nanoTime() - startTimeToPop;
		}
		
		double avrTimeToPop = ((double) timeToPush) / numberOfIterations;
		double avrTimeToPush = ((double) timeToPop) / numberOfIterations;

		Main.totalTimeToPop += avrTimeToPop;
		Main.totalTimeToPush += avrTimeToPush;
	}

}