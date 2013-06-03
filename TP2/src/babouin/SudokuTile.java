package babouin;

import java.util.ArrayList;

import solver.Babouin;

public class SudokuTile {
	
	private ArrayList<Integer> possibleValues;
	private int tileValue;
	private final int row;
	private final int column;
	
	public SudokuTile(int row, int column) {
		this.row = row;
		this.column = column;
		this.tileValue = 0;
		this.possibleValues = new ArrayList<Integer>();
		
		for (int i = 1; i <= 9; ++i) {
			this.possibleValues.add(i);
		}
	}
	
	public final void removePossibleValue(int value) {
		if (this.possibleValues.indexOf(value) != -1) {
			this.possibleValues.remove(this.possibleValues.indexOf(value));
			
			if (getPossibleValuesCount() == 1) {
				setTileValue(this.possibleValues.get(0));
			}
		}
	}
	
	public boolean isInPossibleValues(int value) {
		return this.possibleValues.indexOf(value) != -1;
	}
	
	public int getPossibleValuesCount() {
		return this.possibleValues.size();
	}
	
	public int getTileValue() {
		return this.tileValue;
	}
	
	public void setTileValue(int value) {
		this.tileValue = value;
		
		if (value != 0) {
			this.possibleValues.clear();
			Babouin.getInstance().clearPossibilitiesByTile(this.row, this.column);
			Babouin.getInstance().incrementNumberCountInGrid();
		}
	}
}
