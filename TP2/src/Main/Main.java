package Main;

public class Main {

	public static void main(String[] args) {
		if (args.length == 2) {
			if (args[0] != "" || args[1] != "") {
				int[][] grid = new int[9][9];
				grid = reader.SudokuReader.readFile(args[0]);
				
				if (args[1] == "-o") { // version optimise
					matrix.SudokuSparseMatrix ssm = new matrix.SudokuSparseMatrix(grid);
					ssm.solve();
				} else if (args[1] == "-n") // version non optimise
					new solver.SudokuSolver().solve(grid);
				else { // help
					System.out.println("Sudoku solver v1.0");
					System.out.println("par Samuel Beauchemin");
					System.out.println("Marc-Andre Destrempes");
					System.out.println("\nUsage:");
					System.out.println("\t<path>\tChemin du fichier");
					System.out.println("\t-o\tSolver optimise");
					System.out.println("\t-n\tSolver non optimise");
					System.out.println("\nSudokuSolver.jar <path> <-o|-n>");
				}
			}
		} else { // help
			System.out.println("Sudoku solver v1.0");
			System.out.println("Samuel Beauchemin");
			System.out.println("Marc-Andre Destrempes");
			System.out.println("\nUsage:");
			System.out.println("\t<path>\tChemin du fichier");
			System.out.println("\t-o\tSolver optimise");
			System.out.println("\t-n\tSolver non optimise");
			System.out.println("\njava -jar SudokuSolver.jar <path> <-o|-n>");
		}
	}

}