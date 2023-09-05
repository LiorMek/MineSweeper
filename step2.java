public class step2 {
    public static void main(String[] args) {
        int[][] mineGrid = {
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0},
            {0, 0, 0, 0, 1},
            {1, 0, 1, 0, 0},
            {0, 1, 0, 0, 0}
        };

        int[][] updatedGrid = calculateAdjacentMines(mineGrid);
        displayGrid(updatedGrid);
    }

    public static int[][] calculateAdjacentMines(int[][] mineGrid) {
        int numRows = mineGrid.length;
        int numCols = mineGrid[0].length;
        int[][] updatedGrid = new int[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (mineGrid[row][col] == 0) {
                    // Calculate the number of adjacent mines
                    int adjacentMines = countAdjacentMines(mineGrid, row, col);
                    updatedGrid[row][col] = adjacentMines;
                } else {
                    updatedGrid[row][col] = mineGrid[row][col];
                }
            }
        }

        return updatedGrid;
    }

    public static int countAdjacentMines(int[][] mineGrid, int row, int col) {
        int count = 0;
        int numRows = mineGrid.length;
        int numCols = mineGrid[0].length;

        // Define the relative positions of adjacent cells
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Check if the new coordinates are within the grid boundaries
            if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols) {
                // Check if the adjacent cell contains a mine
                if (mineGrid[newRow][newCol] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void displayGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
