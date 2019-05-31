/**
 * This class that helper functions of range policy
 * 
 * @author NubilaPardus
 * @name RangeHelper
 * @class
 */
public class RangeHelper {
	int maxRange;
	int currentRange = 1;

	/**
	 * Constructor
	 * @param int maxRange
	 */
	RangeHelper(int maxRange) {
		this.maxRange = maxRange;
	}

	public void recordEliminationSuccess() {
		//System.out.println("Elimination Success");
		if (currentRange < maxRange) {
			currentRange++;
		}
	}

	public void recordEliminationTimeout() {
		//System.out.println("Elimination Timed-out - Trying normal pop/push");
		if (currentRange > 1) {
			currentRange--;
		}
	}

	public int getRange() {
		return currentRange;
	}
}
