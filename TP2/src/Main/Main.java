package Main;

import solver.Babouin;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		args = new String[1];
		args[0] = "-b";
		
		if (args[0] == "-o")
			new solver.SudokuSolverOptimized().solve();
		else if (args[0] == "-b") { // b pour babouin
			Babouin.getInstance().solve();
			new solver.SudokuSolver().solve();
		}
		else
			new solver.SudokuSolver().solve();
	}

}
