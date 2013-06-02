package solver;

import java.util.Arrays;

public class SudokuSolverOptimized {
	private static byte[] grid = new byte[81];
	private static byte[][] legalValues = new byte[81][];
	
	public static final void solve()
	{
		initializeGrid();

		System.out.println("Original sudoku");
		writeGrid();
		
    	long startTime = System.currentTimeMillis();
    	solve((byte) 0, (byte) 0);
		long endTime = System.currentTimeMillis();
		
		System.out.println("");
		System.out.println("Solved sudoku");
		writeGrid();
		
		System.out.println("Execution time : " + (endTime - startTime) + " milliseconds");
	}
	
	/**
	 * Solve the sudoku
	 * @param row current row
	 * @param column current column
	 * @return success or not
	 */
	private static final boolean solve(byte row, byte column)
	{
		// check if last row is done, if yes change column
    	if (row == 9) {
    		row = 0;
    		column += 1;
    		if (column == 9) // sudoku solved
            {
            	return true;
            }
    	}
    	
    	// if node already done
    	if (getItem(row, column) != 0)
    	{
    		return solve((byte) (row + 1), column);
    	}

        // if node not done check for a valid number
    	getLegalValuesForCell(row, column);
    	byte[] values = getLegalValues(row, column);
    	
    	for (byte number = 0; number < values.length; number++)
    	{
		    setItem(row, column, values[number]);
		    if (solve((byte) (row + 1), column))
		    {
		    	return true;
		    }
    	}
        
    	setItem(row, column, (byte) 0); // backtracking
    	return false;
	}
	
	/**
	 * Get the possible values for a cell
	 * @param row from 0 to 8
	 * @param column from 0 to 8
	 */
	private static final void getLegalValuesForCell(final byte row, final byte column)
	{
		java.util.Stack<Byte> possibleValues = new java.util.Stack<Byte>();
		java.util.ArrayList<Byte> val = new java.util.ArrayList<Byte>();
		
		// check row
		for (byte c = 0; c < 9; c++)
		{
			if (getItem(row, c) != 0)
				possibleValues.add(getItem(row, c));
		}
		
		// check column
		for (byte r = 0; r < 9; r++)
		{
			if (getItem(r, column) != 0)
				possibleValues.add(getItem(r, column));
		}
		
		// check square
		byte r = (byte) ((row / 3) * 3);
        byte c = (byte) ((column / 3) * 3);

        for (byte i = 0; i < 3; i++)
        {
            for (byte j = 0; j < 3; j++)
            {
                if (getItem((byte) (r+i), (byte) (c+j)) != 0)
                    possibleValues.add(getItem((byte) (r+i), (byte) (c+j)));
            }
        }
        
        // get non present value
        for (byte i = 1; i <= 9; i++)
        {
        	if(!possibleValues.contains(i))
        		val.add(i);
        }
        
        // insert new array of possibilities
        byte[] values = new byte[val.size()];
        
        for (int i = 0; i < val.size(); i++)
        {
        	  values[i] = val.get(i);
        }
        
        setLegalValues(row, column, values);
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
	
	/**
	 * Return item at specific position
	 * @param row from 0 to 8
	 * @param column from 0 to 8
	 * @return item
	 */
	private static final byte[] getLegalValues(final byte row, final byte column)
	{
		return legalValues[(row*9)+column];
	}
	
	/**
	 * Set item at specific position
	 * @param row from 0 to 8
	 * @param column from 0 to 8
	 * @param number from 0 to 9
	 */
	private static final void setLegalValues(final byte row, final byte column, final byte[] values)
	{
		legalValues[(row*9)+column] = values;
	}
	
	/**
	 * Initialize the sudoku
	 */
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
