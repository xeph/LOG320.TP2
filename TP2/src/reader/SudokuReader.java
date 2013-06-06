package reader;

public final class SudokuReader {
	
	private static java.io.BufferedReader br = null;
	
	/**
	 * Lis un fichier et retourne une grille de sudoku.
	 * @param path Chemin du fichier
	 * @return grille de sudoku en array
	 */
	public static int[][] readFile(String path) {
		int[][] sudoku = new int[9][9];
		
		try {
			br = new java.io.BufferedReader(new java.io.FileReader(path));
			String str = "";
			int i = 0;
			
			while((str = br.readLine()) != null) {
				for(int b = 0; b < 9; ++b)
					sudoku[i][b] = Integer.valueOf(String.valueOf(str.charAt(b)));
				i++;
			}
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch(java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch(java.io.IOException e) {
				e.printStackTrace();
			}
		}
		return sudoku;
	}

}
