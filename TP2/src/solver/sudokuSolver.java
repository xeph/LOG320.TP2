package solver;

public class SudokuSolver
{
	private static final byte NINE = 9;
	private static final byte THREE = 3;
	private static final byte ONE = 1;
	private static final byte ZERO = 0;
    private static byte[][] grid = new byte[NINE][NINE];

    public void solve()
    {
    	createModel();
    	writeMatrix(grid);
    	long startTime = System.currentTimeMillis();
    	solve(ZERO,ZERO);
		long endTime = System.currentTimeMillis();
    	writeMatrix(grid);
		System.out.println("Execution time : " + (endTime - startTime) + " milliseconds");
    }
    
    // iterate through each row of a column
    // after a column is done, begin the next
    // column at the first row
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
    
    // by Bob Carpenter
    static void writeMatrix(byte[][] solution) {
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

    private static final boolean validateRow(final byte row, final byte number)
    {
        for (byte column = ZERO; column < NINE; column++)
        {
            if (grid[row][column] == number)
                return false;
        }
        return true;
    }

    private static final boolean validateColumn(final byte column, final byte number)
    {
        for (byte row = ZERO; row < NINE; row++)
        {
            if (grid[row][column] == number)
                return false;
        }
        return true;
    }

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
    
    //http://www.heimetli.ch/ffh/simplifiedsudoku.html
    private static final void createModel()
    {
       // Clear all cells
       for( int row = 0; row < 9; row++ )
          for( int col = 0; col < 9; col++ )
        	  grid[row][col] = 0 ;

       // Create the initial situation
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
    }
}
