package eggdrop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * tests the EggProblem and DropTree classes
 * 
 * @author Liam
 * @version 8/28/21
 */
public class EggTest {

	/**
	 * performs a test on all floors of 100 story building with 3 eggs
	 */
	@Test
	public void eggTest_3_100() {
		DropTree tree = new DropTree(3, 100, true);
		int worstCase = tree.worstCase();
		System.out.println("\nWorst Case: " + worstCase);
		EggProblem prob;
		for (int i = 0; i <= 100; i++) {
			prob = new EggProblem(3, 100, i, tree);
			assertTrue(prob.solve(true));
			assertTrue(prob.drops() <= worstCase);
		}
	}
}
