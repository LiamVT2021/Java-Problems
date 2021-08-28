package eggdrop;

import java.util.ArrayList;

/**
 * A decision tree to select what floors to drop eggs from
 * 
 * recursivly builds up based on worst case and eggs until enough floors are
 * covered, should work with any positive number of floors and eggs
 * 
 * @author Liam
 * @version 8/28/21
 */
public class DropTree {

	/**
	 * stores the ideal starting floor and max covered floors for a given secnerio
	 * 
	 * @author Liam
	 * @version 8/28/21
	 */
	private class DropEntry {
		private int start;
		private int length;

		private DropEntry(int start, int length) {
			this.start = start;
			this.length = length;
		}
	}

	private ArrayList<DropEntry[]> dropMatrix;
	private int maxEggs;
	private int worstCase;
	private int last;

	/**
	 * creates a new DropTree
	 * 
	 * @param eggs   the number of eggs we start with
	 * @param floors the number of floors of the building
	 * @param out    whether to output the DropEntries as strings
	 */
	public DropTree(int eggs, int floors, boolean out) {
		dropMatrix = new ArrayList<DropEntry[]>();
		maxEggs = eggs;
		worstCase = 1;
		while (getEntry(worstCase, eggs).length < floors)
			worstCase++;
		if (out) {
			int maxDrops = 0;
			for (DropEntry[] arr : dropMatrix) {
				int eggCount = 0;
				maxDrops++;
				for (DropEntry drop : arr) {
					eggCount++;
					if (drop != null)
						System.out.println("Max Drops: " + maxDrops + ", eggs: "
								+ eggCount + "    start: "
								+ drop.start + " covers: "
								+ drop.length);
				}
			}
		}
	}

	/**
	 * finds a previously made DropEntry or makes a new one
	 * 
	 * @param maxDrops
	 * @param eggs
	 * @return the coresponding DropEntry
	 */
	private DropEntry getEntry(int maxDrops, int eggs) {
		if (maxDrops <= 0)
			return null;
		if (maxDrops >= dropMatrix.size())
			dropMatrix.add(new DropEntry[maxEggs]);
		DropEntry[] arr = dropMatrix.get(maxDrops - 1);
		DropEntry ret = arr[eggs - 1];
		if (ret == null) {
			ret = makeDrop(maxDrops, eggs);
			arr[eggs - 1] = ret;
		}
		return ret;
	}

	/**
	 * makes a new DropEntry
	 * 
	 * @param maxDrops
	 * @param eggs
	 * @return the new DropEntry
	 */
	private DropEntry makeDrop(int maxDrops, int eggs) {
		if (maxDrops == 1)
			return new DropEntry(1, 1);
		if (eggs == 1)
			return new DropEntry(1, maxDrops);
		int start = getEntry(maxDrops - 1, eggs - 1).length + 1;
		return new DropEntry(start,
				start + getEntry(maxDrops - 1, eggs).length);
	}

	/**
	 * selects the next floor to drop an egg from
	 * 
	 * @param length the range of floors we need to search
	 * @param eggs   the number of eggs we have left
	 * @return the floor we should drop an egg from
	 */
	public int getFloor(int length, int eggs) {
		last -= 3;
		DropEntry drop;
		do {
			last++;
			drop = getEntry(last, eggs);
		} while (drop == null || drop.length < length);
		return drop.start;
	}

	/**
	 * resets the last paramater (used to reduce searching) to the staring value
	 */
	public void reset() {
		last = worstCase + 1;
	}

	/**
	 * @return the worst case number of drops for this tree
	 */
	public int worstCase() {
		return worstCase;
	}
}
