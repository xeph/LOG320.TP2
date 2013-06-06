package babouin;

import java.util.Stack;

/**
 * Cette classe a �t� inspir�e de plusieurs sources diff�rentes. En voici la liste :
 * 
 * http://en.wikipedia.org/wiki/Exact_cover#Sudoku
 * http://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/0011047.pdf
 * http://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
 * http://cgi.cse.unsw.edu.au/~xche635/dlx_sodoku/
 * http://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/presentationboard.pdf
 * http://www.stolaf.edu/people/hansonr/sudoku/exactcovermatrix.htm
 * http://www.ams.org/samplings/feature-column/fcarc-kanoodle
 * http://stackoverflow.com/questions/1935120/representing-a-100k-x-100k-matrix-in-java
 */
public class SudokuSparseMatrix {
	
	/**
	 * 1ere contrainte : un chiffre par case
	 * 2e contrainte : un chiffre ne se retrouve pas plus qu'une fois par ligne
	 * 3e contrainte : un chiffre ne se retrouve pas plus qu'une fois par colonne
	 * 4e contrainte : un chiffre ne se retrouve pas plus qu'une fois par region
	 */
	private static final int CONSTRAINT_COUNT = 4;
	private static final int GRID_SIZE = 9;
	private static final int DIFFERENT_NUMBER_COUNT = 9;
	private static final int MATRIX_LENGTH = GRID_SIZE*GRID_SIZE*CONSTRAINT_COUNT;
	private SudokuSparseMatrixNode root;
	private boolean hasBeenPrinted;
	private Stack<SudokuSparseMatrixNode> solution;
	private long startTime;
	private long solveTime;
	private long printTime;
	private long endTime;
	private int[][] testGrid = new int[729][324];
	
	public SudokuSparseMatrix(int[][] sudokuGrid) {
		this.startTime = System.nanoTime();
		this.solution = new Stack<SudokuSparseMatrixNode>();
		this.hasBeenPrinted = false;
		initialize(sudokuGrid);
		//test();
	}
	
	private void test() {
		for( int row = 0; row < 729; row++ )
			for( int col = 0; col < 324; col++ )
				this.testGrid[row][col] = 0;
		
		for (SudokuSparseMatrixNode columnNode = this.root.getRightNode(); columnNode != this.root; columnNode = columnNode.getRightNode()) {
			for (SudokuSparseMatrixNode node = columnNode.getBottomNode(); node != columnNode; node = node.getBottomNode()) {
				this.testGrid[node.getRow() - 1][node.getColumn() - 1] = 1;
			}
		}
		
		for( int row = 200; row < 400; row++ ) {
			System.out.println("");
			for( int col = 0; col < 324; col++ ) {
				System.out.print(this.testGrid[row][col] == 0 ? " " : this.testGrid[row][col]);
			}
		}
	}
	
	public void solve() {
		this.solveTime = System.nanoTime();
		System.out.println("Initializing time : " + ((double)this.solveTime/1000000 - (double)this.startTime/1000000) + " milliseconds");
		search();
		this.endTime = System.nanoTime();
		
		if (!this.hasBeenPrinted) {
			this.printTime = this.endTime;
			System.out.println("Il n'y a pas de solution pour cette grille.");
		}
		
		this.endTime = System.nanoTime();
		
		System.out.println("Solving time : " + ((double)this.printTime/1000000 - (double)this.solveTime/1000000) + " milliseconds");
		System.out.println("Printing time : " + ((double)this.endTime/1000000 - (double)this.printTime/1000000) + " milliseconds");
	}
	
	private void initialize(int[][] sudokuGrid) {
		this.root = new SudokuSparseMatrixNode(0, 0);
		SudokuSparseMatrixNode newNode;
		
		for (int i = 0; i != MATRIX_LENGTH; ++i) {
			newNode = new SudokuSparseMatrixNode(i + 1, 0);
			newNode.setLeftNode(this.root.getLeftNode());
			newNode.setRightNode(this.root);
			this.root.getLeftNode().setRightNode(newNode);
			this.root.setLeftNode(newNode);
		}
		
		for (int row = 0; row != GRID_SIZE; ++row) {
			for (int column = 0; column != GRID_SIZE; ++column) {
				if (sudokuGrid[row][column] != 0) {
					insertRow(row, column, sudokuGrid[row][column]);
				} else {
					for (int value = 1; value <= DIFFERENT_NUMBER_COUNT; ++value) {
						insertRow(row, column, value);
					}
				}
			}
		}	
	}
	
	
	/**
	 * Find an unfilled column C - if theres no such column, we have a solution.
	 * For every row R that fills this column C:
	 *     Try using row R in our solution - which means:
	 *         1. Removing R; so we don't consider it again
	 *         2. Removing every column that R fills; we don't need to fill those again
	 *         3. For every column that R fills, remove the other rows that also fill the column; we don't need any other row that fill what this row has already filled.
	 *     Recurse with the new state.
	 * "Undo" all the things removed while trying row R; restore to previous state.
	 */
	private void search() {
		SudokuSparseMatrixNode currentColumnNode = this.root.getRightNode();
		
		if (currentColumnNode == this.root) {
			printCurrentSolution();
		} else {
			boolean hasEmptyColumn = false;
			
			for (SudokuSparseMatrixNode columnNode = currentColumnNode; columnNode != this.root && !hasEmptyColumn; columnNode = columnNode.getRightNode()) {
				if (columnNode.getBottomNode() == columnNode) {
					hasEmptyColumn = true;
				}
			}
			
			if (!hasEmptyColumn) {
				cover(currentColumnNode);
				
				for (SudokuSparseMatrixNode rowNode = currentColumnNode.getBottomNode(); rowNode != currentColumnNode && !this.hasBeenPrinted; rowNode = rowNode.getBottomNode()) {
					this.solution.add(rowNode);
					for (SudokuSparseMatrixNode rightNode = rowNode.getRightNode(); rightNode != rowNode; rightNode = rightNode.getRightNode()) {
						cover(rightNode.getColumnNode());
					}
					
					search();
					
					if (!this.hasBeenPrinted) {
						
						for (SudokuSparseMatrixNode leftNode = rowNode.getLeftNode(); leftNode != rowNode; leftNode = leftNode.getLeftNode()) {
							uncover(leftNode.getColumnNode());
						}
						
						this.solution.pop();
					}
				}
				
				if (!this.hasBeenPrinted) {
					uncover(currentColumnNode);
				}
			}
		}
	}
	
	private void printCurrentSolution() {
		this.printTime = System.nanoTime();
		
		@SuppressWarnings("unchecked")
		Stack<SudokuSparseMatrixNode> solution = (Stack<SudokuSparseMatrixNode>)this.solution.clone();
		int[][] sudokuGrid = new int[GRID_SIZE][GRID_SIZE];
		
		while (!solution.isEmpty()) {
			SudokuSparseMatrixNode rowNode = solution.pop();
			
			int row = ((rowNode.getRow() - 1) / GRID_SIZE) / DIFFERENT_NUMBER_COUNT;
			int column = (rowNode.getRow() - 1 - row * DIFFERENT_NUMBER_COUNT * GRID_SIZE) / GRID_SIZE;
			int value = rowNode.getRow() - row * DIFFERENT_NUMBER_COUNT * GRID_SIZE - column * DIFFERENT_NUMBER_COUNT;
			
			sudokuGrid[row][column] = value;
		}
		
		// by Bob Carpenter
		for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0)
                System.out.println(" -----------------------");
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(sudokuGrid[i][j] == 0
                                 ? " "
                                 : Integer.toString(sudokuGrid[i][j]));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(" -----------------------");
        
        this.hasBeenPrinted = true;
	}
	
	private void insertRow(int row, int column, int value) {
		SudokuSparseMatrixNode columnNode = this.root.getRightNode();
		SudokuSparseMatrixNode newNode = null;
		
		// 1er noeud : 1ere contrainte
		while (columnNode.getColumn() != row * GRID_SIZE + column + 1) {
			columnNode = columnNode.getRightNode();
		}
		
		newNode = insertNode(row, column, value, columnNode, newNode);
		
		// 2e noeud : 2e contrainte
		while (columnNode.getColumn() != GRID_SIZE * GRID_SIZE + row * DIFFERENT_NUMBER_COUNT + value) {
			columnNode = columnNode.getRightNode();
		}
		
		newNode = insertNode(row, column, value, columnNode, newNode);
		
		// 3e noeud : 3e contrainte
		while (columnNode.getColumn() != GRID_SIZE * GRID_SIZE + GRID_SIZE * DIFFERENT_NUMBER_COUNT + column * DIFFERENT_NUMBER_COUNT + value) {
			columnNode = columnNode.getRightNode();
		}
		
		newNode = insertNode(row, column, value, columnNode, newNode);
		
		// 4e noeud : 4e contrainte
		while (columnNode.getColumn() != GRID_SIZE * GRID_SIZE + GRID_SIZE * DIFFERENT_NUMBER_COUNT * 2 + ((row / 3) * 3 + column / 3) * DIFFERENT_NUMBER_COUNT + value) {
			columnNode = columnNode.getRightNode();
		}
		
		newNode = insertNode(row, column, value, columnNode, newNode);
	}
	
	private SudokuSparseMatrixNode insertNode(int row, int column, int value,
			SudokuSparseMatrixNode columnNode,
			SudokuSparseMatrixNode lastInsertedNode) {
		SudokuSparseMatrixNode newNode = new SudokuSparseMatrixNode(
				columnNode.getColumn(),
				row * GRID_SIZE * DIFFERENT_NUMBER_COUNT + column * DIFFERENT_NUMBER_COUNT + value);
		
		newNode.setColumnNode(columnNode);
		
		newNode.setTopNode(columnNode.getTopNode());
		newNode.setBottomNode(columnNode);
		
		if (lastInsertedNode != null) {
			newNode.setLeftNode(lastInsertedNode);
			newNode.setRightNode(lastInsertedNode.getRightNode());
		}
		
		newNode.getLeftNode().setRightNode(newNode);
		newNode.getRightNode().setLeftNode(newNode);
		newNode.getTopNode().setBottomNode(newNode);
		newNode.getBottomNode().setTopNode(newNode);
		
		return newNode;
	}
	
	private final void cover(SudokuSparseMatrixNode columnNode) {
		columnNode.getRightNode().setLeftNode(columnNode.getLeftNode());
		columnNode.getLeftNode().setRightNode(columnNode.getRightNode());

		for (SudokuSparseMatrixNode rowNode = columnNode.getBottomNode(); rowNode != columnNode; rowNode = rowNode.getBottomNode()) {
			for (SudokuSparseMatrixNode rightNode = rowNode.getRightNode(); rightNode != rowNode; rightNode = rightNode.getRightNode()) {
				rightNode.getTopNode().setBottomNode(rightNode.getBottomNode());
				rightNode.getBottomNode().setTopNode(rightNode.getTopNode());
			}
		}
	}

	private final void uncover(SudokuSparseMatrixNode columnNode) {
		for (SudokuSparseMatrixNode rowNode = columnNode.getTopNode(); rowNode != columnNode; rowNode = rowNode.getTopNode()) {
			for (SudokuSparseMatrixNode leftNode = rowNode.getLeftNode(); leftNode != rowNode; leftNode = leftNode.getLeftNode()) {
				leftNode.getTopNode().setBottomNode(leftNode);
				leftNode.getBottomNode().setTopNode(leftNode);
			}
		}
		
		columnNode.getRightNode().setLeftNode(columnNode);
		columnNode.getLeftNode().setRightNode(columnNode);
	}
}