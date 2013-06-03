package solver;

import babouin.SudokuTile;

public class Babouin {
	
	private static final byte NINE = 9;
    private static SudokuTile[][] grid = new SudokuTile[NINE][NINE];
	private static Babouin instance;
	private int numberCountInGrid;
	
	private Babouin() {
		numberCountInGrid = 0;
		
		for (int row = 0; row != 9; ++row) {
			for (int column = 0; column != 9; ++column) {
				grid[row][column] = new SudokuTile(row, column);
			}
		}
	}
	
	public void incrementNumberCountInGrid() {
		++this.numberCountInGrid;
	}
	
	public void solve()
    {
    	createModel();
    	writeMatrix(grid);
    	long startTime = System.nanoTime();
    	
    	clearPossibilitiesWithStartingNumbers();
    	
    	boolean isFinishedSolving = false;
    	int numberCountInGridBeforeOperation = 0;
    	
    	while (!isFinishedSolving) {
    		numberCountInGridBeforeOperation = this.numberCountInGrid;
    		
    		assignValueToTileWithSingleOccurence();
    		//System.out.println("Number of tiles : " + this.numberCountInGrid);
    		
    		if (numberCountInGridBeforeOperation == this.numberCountInGrid || this.numberCountInGrid == 81) {
    			isFinishedSolving = true;
    		}
    	}
    	
    	/*if (this.numberCountInGrid == 81) {
    		System.out.println("Magie! C'est résolu comme un babouin.");
    	} else if (this.numberCountInGrid > 81) {
    		System.out.println("Wtf y'a un problème dans l'algo.");
    	} else {
    		System.out.println("Meh. Better luck next time.");
    	}
    	System.out.println("Number of tiles : " + this.numberCountInGrid);*/
    	
    	//solve(ZERO,ZERO);
		long endTime = System.nanoTime();
    	writeMatrix(grid);
		System.out.println("Execution time : " + ((double)endTime/1000000 - (double)startTime/1000000) + " milliseconds");
    }
	
	private void clearPossibilitiesWithStartingNumbers() {
		for (int row = 0; row != 9; ++row) {
			for (int column = 0; column != 9; ++column) {
				if (grid[row][column].getTileValue() != 0) {
					clearPossibilitiesByTile(row, column);
				}
			}
		}
	}
	
	public void clearPossibilitiesByTile(int row, int column) {
		for (int i = 0; i != 9; ++i) {
			grid[row][i].removePossibleValue(grid[row][column].getTileValue());
			grid[i][column].removePossibleValue(grid[row][column].getTileValue());
		}
		
		for (int i = 0; i != 3; ++i) {
			for (int j = 0; j != 3; ++j) {
				grid[(row/3)*3+i][(column/3)*3+j].removePossibleValue(grid[row][column].getTileValue());
			}
		}
	}
	
	/**
	 * Tel un babouin, cette méthode assigne à une case un chiffre qui est présent uniquement
	 * dans les possibilités de cette même case à travers sa ligne, sa colonne ou son carré.
	 */
	private void assignValueToTileWithSingleOccurence() {
		int occurenceInRow = 0;
		int occurenceInColumn = 0;
		int occurenceInSquare = 0;
		int posRowOccurence = 0;
		int posColumnOccurence = 0;
		
		for (int value = 1; value <= 9; ++value) {
			for (int i = 0; i != 9; ++i) {
				occurenceInRow = 0;
				occurenceInColumn = 0;
				
				for (int j = 0; j != 9; ++j) {
					if (grid[i][j].isInPossibleValues(value)) {
						++occurenceInRow;
						posRowOccurence = j;
					}
					
					if (grid[j][i].isInPossibleValues(value)) {
						++occurenceInColumn;
						posColumnOccurence = j;
					}
				}
				
				if (occurenceInRow == 1) {
					grid[i][posRowOccurence].setTileValue(value);
				}
				
				if (occurenceInColumn == 1) {
					grid[posColumnOccurence][i].setTileValue(value);
				}
			}
			
			for (int row = 0; row != 3; ++row) {
				for (int column = 0; column != 3; ++column) {
					occurenceInSquare = 0;
					
					for (int i = 0; i != 3; ++i) {
						for (int j = 0; j != 3; ++j) {
							if (grid[(row/3)*3+i][(column/3)*3+j].isInPossibleValues(value)) {
								++occurenceInSquare;
								posRowOccurence = i;
								posColumnOccurence = j;
							}
						}
					}
					
					if (occurenceInSquare == 1) {
						grid[posRowOccurence][posColumnOccurence].setTileValue(value);
					}
				}
			}
		}
		
		
		/*for (int row = 0; row != 9; ++row) {
			for (int column = 0; column != 9; ++column) {
				if (grid[row][column].getPossibleValues().size() == 1) {
					grid[row][column].setTileValue(grid[row][column].getPossibleValues().get(0));
					clearPossibilitiesByTile(row, column);
				}
			}
		}*/
	}
    
    // by Bob Carpenter
    static void writeMatrix(SudokuTile[][] solution) {
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                System.out.println(" -----------------------");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(solution[i][j].getTileValue() == 0
                                 ? " "
                                 : Integer.toString(solution[i][j].getTileValue()));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(" -----------------------");
    }
	
	private static final void createModel()
    {
       // Clear all cells
       for( int row = 0; row < 9; row++ )
          for( int col = 0; col < 9; col++ )
        	  grid[row][col].setTileValue(0);

       // Create the initial situation
       grid[0][0].setTileValue(9);
       grid[0][4].setTileValue(2);
       grid[0][6].setTileValue(7);
       grid[0][7].setTileValue(5);

       grid[1][0].setTileValue(6);
       grid[1][4].setTileValue(5);
       grid[1][7].setTileValue(4);

       grid[2][1].setTileValue(2);
       grid[2][3].setTileValue(4);
       grid[2][7].setTileValue(1);

       grid[3][0].setTileValue(2);
       grid[3][2].setTileValue(8);

       grid[4][1].setTileValue(7);
       grid[4][3].setTileValue(5);
       grid[4][5].setTileValue(9);
       grid[4][7].setTileValue(6);

       grid[5][6].setTileValue(4);
       grid[5][8].setTileValue(1);

       grid[6][1].setTileValue(1);
       grid[6][5].setTileValue(5);
       grid[6][7].setTileValue(8);

       grid[7][1].setTileValue(9);
       grid[7][4].setTileValue(7);
       grid[7][8].setTileValue(4);

       grid[8][1].setTileValue(8);
       grid[8][2].setTileValue(2);
       grid[8][4].setTileValue(4);
       grid[8][8].setTileValue(6);
    }
	
	public static final Babouin getInstance() {
		if (instance == null) {
			instance = new Babouin();
		}
		
		return instance;
	}
}
