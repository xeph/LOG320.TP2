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
	       grid[0][0] = 9 ;
	       grid[0][4] = 2 ;
	       grid[0][6] = 7 ;
	       grid[0][7] = 5 ;

	       grid[1][0] = 6 ;
	       grid[1][4] = 5 ;
	       grid[1][7] = 4 ;

	       grid[2][1] = 2 ;
	       grid[2][3] = 4 ;
	       grid[2][7] = 1 ;

	       grid[3][0] = 2 ;
	       grid[3][2] = 8 ;

	       grid[4][1] = 7 ;
	       grid[4][3] = 5 ;
	       grid[4][5] = 9 ;
	       grid[4][7] = 6 ;

	       grid[5][6] = 4 ;
	       grid[5][8] = 1 ;

	       grid[6][1] = 1 ;
	       grid[6][5] = 5 ;
	       grid[6][7] = 8 ;

	       grid[7][1] = 9 ;
	       grid[7][4] = 7 ;
	       grid[7][8] = 4 ;

	       grid[8][1] = 8 ;
	       grid[8][2] = 2 ;
	       grid[8][4] = 4 ;
	       grid[8][8] = 6 ;
		
		SudokuSolver canard = new SudokuSolver();
		canard.solve();
		
		SudokuSparseMatrix babouin = new SudokuSparseMatrix(grid);
		babouin.solve();
	}

}
