package solver;

import java.util.Arrays;

public class SudokuSolverOptimized {
	private static byte[] grid = new byte[81];
	
	public static final void solve()
	{
		initializeGrid();

		System.out.println("Original sudoku");
		writeGrid();
		
    	long startTime = System.nanoTime();
    	solve((byte) 0, (byte) 0);
		long endTime = System.nanoTime();
		
		System.out.println("");
		System.out.println("Solved sudoku");
		writeGrid();
		
		System.out.println("Execution time : " + (endTime - startTime) + " nanoseconds");
	}
	
	private static final void solve(byte row, byte column)
	{
		
	}
	
	/**
	 * Check if a value can be set or not
	 * @param row from 0 to 8
	 * @param column from 0 to 8
	 * @param number from 1 to 9
	 * @return number can be set or not
	 */
	private static final boolean checkLegalValue(final byte row, final byte column, final byte number)
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
	 * Check if the number is valid in the row
	 * @param row from 0 to 8
	 * @param number from 0 to 8
	 * @return number is valid or not
	 */
	private static final boolean validateRow(final byte row, final byte number)
	{
		for (byte column = 0; column < 9; column++)
        {
            if (getItem(row, column) == number)
                return false;
        }
        return true;
	}
	
	/**
	 * Check if the number is valid in the column
	 * @param column from 0 to 8
	 * @param number from 0 to 8
	 * @return number is valid or not
	 */
	private static final boolean validateColumn(final byte column, final byte number)
	{
		for (byte row = 0; row < 9; row++)
        {
            if (getItem(row, column) == number)
                return false;
        }
        return true;	
	}
	
	/**
	 * Check if the number is valid in the square
	 * @param row from 0 to 8
	 * @param column from 0 to 8
	 * @param number from 1 to 9
	 * @return number is valid or not
	 */
	private static final boolean validateSquare(final byte row, final byte column, final byte number)
	{
		byte r = (byte) ((row / 3) * 3);
        byte c = (byte) ((column / 3) * 3);

        for (byte i = 0; i < 3; i++)
        {
            for (byte j = 0; j < 3; j++)
            {
                if (getItem((byte) (r+i), (byte) (c+j)) == number)
                    return false;
            }
        }
        return true;
	}
	
	/**
	 * Prints the grid to console
	 */
	private static final void writeGrid()
	{	
		for (byte i = 0; i < 9; i++)
		{
			if (i % 3 == 0)
				System.out.println(" -----------------------");
			
			for (byte j = 0; j < 9; j++)
			{
				if (j % 3 == 0)
					System.out.print("| ");
				System.out.print(getItem(i, j));
				System.out.print(' ');
			}
			System.out.println("|");
		}
		System.out.println(" -----------------------");
	}
	
	/**
	 * Return item at specific position
	 * @param row from 0 to 8
	 * @param column from 0 to 8
	 * @return item
	 */
	private static final byte getItem(final byte row, final byte column)
	{
		return grid[(row*9)+column];
	}
	
	/**
	 * Set item at specific position
	 * @param row from 0 to 8
	 * @param column from 0 to 8
	 * @param number from 0 to 9
	 */
	private static final void setItem(final byte row, final byte column, final byte number)
	{
		grid[(row*9)+column] = number;
	}
	
	private static final void initializeGrid()
	{
		Arrays.fill(grid, (byte) 0);
		createGrid(); //TODO replace with filereader
	}
	
	/**
	 * Create a temporary sudoku problem
	 */
    private static final void createGrid()
    {
       setItem((byte) 0, (byte) 0, (byte) 9);
       setItem((byte) 0, (byte) 4, (byte) 2);
       setItem((byte) 0, (byte) 6, (byte) 7);
       setItem((byte) 0, (byte) 7, (byte) 5);
       
       setItem((byte) 1, (byte) 0, (byte) 6);
       setItem((byte) 1, (byte) 4, (byte) 5);
       setItem((byte) 1, (byte) 7, (byte) 4);
       
       setItem((byte) 2, (byte) 1, (byte) 2);
       setItem((byte) 2, (byte) 3, (byte) 4);
       setItem((byte) 2, (byte) 7, (byte) 1);
       
       setItem((byte) 3, (byte) 0, (byte) 2);
       setItem((byte) 3, (byte) 2, (byte) 8);
       
       setItem((byte) 4, (byte) 1, (byte) 7);
       setItem((byte) 4, (byte) 3, (byte) 5);
       setItem((byte) 4, (byte) 5, (byte) 9);
       setItem((byte) 4, (byte) 7, (byte) 6);

       setItem((byte) 5, (byte) 6, (byte) 4);
       setItem((byte) 5, (byte) 8, (byte) 1);

       setItem((byte) 6, (byte) 1, (byte) 1);
       setItem((byte) 6, (byte) 5, (byte) 5);
       setItem((byte) 6, (byte) 7, (byte) 8);

       setItem((byte) 7, (byte) 1, (byte) 9);
       setItem((byte) 7, (byte) 4, (byte) 7);
       setItem((byte) 7, (byte) 8, (byte) 4);
       
       setItem((byte) 8, (byte) 1, (byte) 8);
       setItem((byte) 8, (byte) 2, (byte) 2);
       setItem((byte) 8, (byte) 4, (byte) 4);
       setItem((byte) 8, (byte) 8, (byte) 6);
    }
}
