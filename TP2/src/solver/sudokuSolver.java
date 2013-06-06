package solver;

/**
 * Cette classe a été inspirée de plusieurs sources différentes. En voici la liste :
 * 
 * http://www.colloquial.com/games/sudoku/java_sudoku.html
 */

public class SudokuSolver
{
	private static final byte NINE = 9;
	private static final byte THREE = 3;
	private static final byte ONE = 1;
	private static final byte ZERO = 0;
    private static int[][] grid = new int[NINE][NINE];

    public void solve(int[][] grid)
    {
    	SudokuSolver.grid = grid;
    	long startTime = System.nanoTime();
    	solve(ZERO,ZERO);
    	long endTime = System.nanoTime();
    	writeMatrix(SudokuSolver.grid);
		System.out.println("Execution time : " + ((double)endTime/1000000 - (double)startTime/1000000) + " milliseconds");
    }
    
    /**
     * itere dans chaque ligne d'une colonne
     * quand une ligne est faite, commencer
     * la prochaine colonne a la premiere ligne
     * @param row ligne du sudoku
     * @param column colonne du sudoku
     * @return
     */
    private static final boolean solve(byte row, byte column)
    {	
    	// check if last row is done, if yes change column
    	if (row == NINE) {
    		row = ZERO;
    		column += ONE;
    		if (column == NINE) // sudoku solved
            {
            	return true;
            }
    	}
    	
    	// if node already done
    	if (grid[row][column] != ZERO)
    	{
    		return solve((byte) (row + ONE), column);
    	}

        // if node not done check for a valid number
    	for (byte number = ONE; number <= NINE; number++) 
    	{
    		if (validateInsert(row, column, number)) 
    		{
    			grid[row][column] = number;
    			if (solve((byte) (row + ONE), column))
    			{
    				return true;
    			}
    		}
    	}
        
    	grid[row][column] = ZERO; // backtracking
    	return false;
    }
    
    /**
     * ecrit la matrice en console
     * par Bob Carpenter
     * @param solution grille du sudoku
     */
    static void writeMatrix(int[][] solution) {
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                System.out.println(" -----------------------");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(solution[i][j] == 0
                                 ? " "
                                 : Integer.toString(solution[i][j]));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(" -----------------------");
    }

    /**
     * Valide une insertion de numero
     * @param row ligne du sudoku
     * @param column colonne du sudoku
     * @param number numero du sudoku
     * @return valide ou non
     */
    private static final boolean validateInsert(final byte row, final byte column, final byte number)
    {
        boolean isValid = false;

        isValid = validateRow(row, number);

        if (!isValid)
        	return isValid;
        
        isValid = validateColumn(column, number);
        
        if (!isValid)
        	return isValid;
        
        isValid = validateSquare(row, column, number);

        return isValid;
    }

    /**
     * Valide une ligne
     * @param row ligne du sudoku
     * @param number numero a valider
     * @return valide ou non
     */
    private static final boolean validateRow(final byte row, final byte number)
    {
        for (byte column = ZERO; column < NINE; column++)
        {
            if (grid[row][column] == number)
                return false;
        }
        return true;
    }

    /**
     * Valide une colonne
     * @param column colonne du sudoku
     * @param number numero a valider
     * @return valide ou non
     */
    private static final boolean validateColumn(final byte column, final byte number)
    {
        for (byte row = ZERO; row < NINE; row++)
        {
            if (grid[row][column] == number)
                return false;
        }
        return true;
    }

    /**
     * Valide un carre d'une grille
     * @param row ligne du sudoku
     * @param column colonne du sudoku
     * @param number numero a valider
     * @return valide ou non
     */
    private static final boolean validateSquare(final byte row, final byte column, final byte number)
    {
        byte r = (byte) ((row / THREE) * THREE);
        byte c = (byte) ((column / THREE) * THREE);

        for (byte i = ZERO; i < THREE; i++)
        {
            for (byte j = ZERO; j < THREE; j++)
            {
                if (grid[r+i][c+j] == number)
                    return false;
            }
        }
        return true;
    }
}
