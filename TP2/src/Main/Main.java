package Main;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		args = new String[1];
		args[0] = "-o";
		
		if (args[0] == "-o")
			new solver.SudokuSolverOptimized().solve();
		else
			new solver.SudokuSolver().solve();
	}

}
