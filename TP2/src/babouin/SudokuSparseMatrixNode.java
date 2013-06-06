package babouin;

public class SudokuSparseMatrixNode {
	
	private int column;
	private int row;
	private int childNodeCount;
	private SudokuSparseMatrixNode columnNode;
	private SudokuSparseMatrixNode leftNode;
	private SudokuSparseMatrixNode rightNode;
	private SudokuSparseMatrixNode topNode;
	private SudokuSparseMatrixNode bottomNode;
	
	public SudokuSparseMatrixNode(int column, int row) {
		this.column = column;
		this.row = row;
		this.leftNode = this;
		this.rightNode = this;
		this.topNode = this;
		this.bottomNode = this;
	}
	
	public SudokuSparseMatrixNode getLeftNode() {
		return this.leftNode;
	}
	
	public void setLeftNode(SudokuSparseMatrixNode leftNode) {
		this.leftNode = leftNode;
	}
	
	public SudokuSparseMatrixNode getRightNode() {
		return this.rightNode;
	}
	
	public void setRightNode(SudokuSparseMatrixNode rightNode) {
		this.rightNode = rightNode;
	}
	
	public SudokuSparseMatrixNode getTopNode() {
		return this.topNode;
	}
	
	public void setTopNode(SudokuSparseMatrixNode topNode) {
		this.topNode = topNode;
	}
	
	public SudokuSparseMatrixNode getBottomNode() {
		return this.bottomNode;
	}
	
	public void setBottomNode(SudokuSparseMatrixNode bottomNode) {
		this.bottomNode = bottomNode;
	}
	
	public SudokuSparseMatrixNode getColumnNode() {
		return this.columnNode;
	}
	
	public void setColumnNode(SudokuSparseMatrixNode columnNode) {
		columnNode.incrementChildNodeCount();
		this.columnNode = columnNode;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void incrementChildNodeCount() {
		++this.childNodeCount;
	}
	
	public void decrementChildNodeCount() {
		--this.childNodeCount;
		if (this.childNodeCount < 0) {
			System.out.println("Error detected");
		}
	}
	
	public int getChildNodeCount() {
		return this.childNodeCount;
	}
}
