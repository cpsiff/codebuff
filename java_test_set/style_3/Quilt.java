public class Quilt {

	public static void main(String[] args) {

		char[][] myBlock;
		char[][] myQuilt;

		myBlock =
				new char[][] {
					{'x', '.', '.', '.', '.'},
					{'x', '+', '.', '.', '.'},
					{'x', '+', '+', '.', '.'},
					{'x', 'x', 'x', 'x', 'x'}
				};

		displayPattern(myBlock);

		int quiltWidth = myBlock[0].length * 4;
		int quiltHeight = myBlock.length * 3;
		myQuilt = new char[quiltHeight][quiltWidth];
		for (char[] row : myQuilt) {
			for (int i = 0; i < row.length; i++) {
				row[i] = '?';
			}
		}

		displayPattern(myQuilt);

		createQuilt(myQuilt, myBlock);

		displayPattern(myQuilt);
	}

	public static void displayPattern(char[][] myArray) {
		for (char[] row : myArray) {
			for (char item : row) {
				System.out.print(item);
			}
			System.out.println();
		}
	}

	public static void createQuilt(char[][] quilt, char[][] block) {
		char[][] newBlock = createFlipped(block);
		for (int i = 0; i < quilt.length; i += block.length) {
			for (int j = 0; j < quilt[0].length; j += block[0].length) {
				placeBlock(quilt, newBlock, i, j);
			}
		}
	}

	/**
	 * Places the 2-D character array block into the 2-D character array quilt starting with the upper
	 * left hand corner of the block placed into position in the quilt at startRow, startCol
	 *
	 * <p>Note: This is implemented for you, DO NOT CHANGE THIS METHOD.
	 */
	public static void placeBlock(char[][] quilt, char[][] block, int startRow, int startCol) {
		for (int r = 0; r < block.length; r++) {
			for (int c = 0; c < block[r].length; c++) {
				quilt[r + startRow][c + startCol] = block[r][c];
			}
		}
	}

	/** Returns a 2-D array which is the horizontally flipped version of the block parameter. */
	public static char[][] createFlipped(char[][] block) {
		char[][] flippedBlock = new char[block.length][block[0].length];
		for (int i = block.length - 1; i >= 0; i--) {
			flippedBlock[i] = block[block.length - 1 - i];
		}
		return flippedBlock;
	}
}
