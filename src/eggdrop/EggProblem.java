package eggdrop;

/**
 * a representation of the egg problem, to be solved using the DropTree
 * 
 * @author Liam
 * @version 8/28/21
 */
public class EggProblem {

	private DropTree dropTree;
	private int floorN;
	private int min;
	private int max;
	private int drops;
	private int remEggs;

	/**
	 * creates a new EggProblem and coresponding DropTree
	 * 
	 * @param eggs         the number of eggs we start with
	 * @param floors       the number of floors in the building
	 * @param correctFloor the tallest floor we can drop from without breaking the
	 *                     egg
	 */
	public EggProblem(int eggs, int floors, int correctFloor) {
		this(eggs, floors, correctFloor,
				new DropTree(eggs, floors, false));
	}

	/**
	 * creates a new EggProblem given a premade DropTree
	 * 
	 * @param eggs         the number of eggs we start with
	 * @param floors       the number of floors in the building
	 * @param correctFloor the tallest floor we can drop from without breaking the
	 *                     egg
	 * @param tree         the premade dropTree
	 */
	public EggProblem(int eggs, int floors, int correctFloor,
			DropTree tree) {
		tree.reset();
		dropTree = tree;
		floorN = correctFloor;
		min = 0;
		max = floors + 1;
		drops = 0;
		remEggs = eggs;
	}

	/**
	 * @param floor the floor we drop the egg from
	 * @return True if the egg breaks
	 */
	public boolean drop(int floor) {
		return floor > floorN;
	}

	/**
	 * @return the number of drops so far
	 */
	public int drops() {
		return drops;
	}

	/**
	 * attempts to solve the problem using the DropTree
	 * 
	 * @param out whether to output drops as strings
	 * @return True if solved
	 */
	public boolean solve(boolean out) {
		String str = null;
		if (out)
			System.out.println("\nTarget Floor: " + floorN);
		while (remEggs > 0 && max - min > 1) {
			int floor = min
					+ dropTree.getFloor(max - 1 - min, remEggs);
			if (out)
				str = "Drop on floor " + floor;
			if (drop(floor)) {
				max = floor;
				remEggs--;
				if (out)
					str += ": egg breaks";
			} else {
				min = floor;
				if (out)
					str += ": egg does not break";
			}
			drops++;
			if (out)
				System.out.println(str);
		}
		if (min == floorN && max == floorN + 1) {
			if (out)
				System.out.println("Solved in " + drops + " drops");
			return true;
		} else {
			if (out)
				System.out.println("Did not Solve");
			return false;
		}
	}

}
