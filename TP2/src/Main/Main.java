package Main;

import babouin.SudokuSparseMatrix;
import babouin.SudokuSparseMatrixNode;
import solver.Babouin;
import solver.SudokuSolver;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		/*args = new String[1];
		args[0] = "-b";
		
		if (args[0] == "-o")
			new solver.SudokuSolverOptimized().solve();
		else if (args[0] == "-b") { // b pour babouin
			Babouin.getInstance().solve();
			new solver.SudokuSolver().solve();
		}
		else
			new solver.SudokuSolver().solve();*/
		
		int[][] grid = new int[9][9];
		
		// Clear all cells
	       for( int row = 0; row < 9; row++ )
	          for( int col = 0; col < 9; col++ )
	        	  grid[row][col] = 0 ;

	       // Create the initial situation
	       
	       //easy puzzle
	       grid[1][5] = 3;
	       grid[1][7] = 8;
	       grid[1][8] = 5;
	       
	       grid[2][2] = 1;
	       grid[2][4] = 2;
	       
	       grid[3][3] = 5;
	       grid[3][5] = 7;
	       
	       grid[4][2] = 4;
	       grid[4][6] = 1;
	       
	       grid[5][1] = 9;
	       
	       grid[6][1] = 5;
	       grid[6][7] = 7;
	       grid[6][8] = 3;
	       
	       grid[7][2] = 2;
	       grid[7][4] = 1;
	       
	       grid[8][4] = 4;
	       grid[8][8] = 9;
		
		SudokuSolver canard = new SudokuSolver();
		canard.solve();
		
		SudokuSparseMatrix babouin = new SudokuSparseMatrix(grid);
		babouin.solve();
	}

}
