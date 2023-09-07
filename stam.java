public class stam {
    public static void main(String[] args) {

        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        double probability = Double.parseDouble(args[2]);
        int[][] grid = createGrid(row, col, probability);

        // Calculate and update the grid with adjacent mines
        grid = calculateAdjacentMines(grid);
        displayGrid(grid);

        // Check if there are two additional arguments for coordinates
        if (args.length == 5) {
            int x = Integer.parseInt(args[3]);
            int y = Integer.parseInt(args[4]);
            int value = getValueAtCoordinate(grid, x, y);
            System.out.println("Value at coordinate (" + x + ", " + y + "): " + value);
        }
    }

    /*
     * Receives the size of the grid nxm, and the probability of a mine.
     * Creates a matching random grid
     */
    public static int[][] createGrid(int row, int col, double probability) {
        int[][] grid = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (Math.random() < probability) {
                    grid[i][j] = -1;
                } else {
                    grid[i][j] = 0;
                }
            }
        }
        return grid;
    }

    /*
     * Receives a grid, and prints it
     */
    public static void displayGrid(int[][] mineGrid) {
        for (int i = 0; i < mineGrid.length; i++) {
            for (int j = 0; j < mineGrid[i].length; j++) {
                if (mineGrid[i][j] == -1) {
                    System.out.print("x ");
                } else {
                    System.out.print(mineGrid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /*
     * Calculates and updates the grid with the number of adjacent mines for each '0'
     */
    public static int[][] calculateAdjacentMines(int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;
        int[][] updatedGrid = new int[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid[row][col] == 0) {
                    // Calculate the number of adjacent mines
                    int adjacentMines = countAdjacentMines(grid, row, col);
                    updatedGrid[row][col] = adjacentMines;
                } else {
                    updatedGrid[row][col] = grid[row][col];
                }
            }
        }

        return updatedGrid;
    }

    /*
     * Counts the number of adjacent mines for a given cell
     */
    public static int countAdjacentMines(int[][] grid, int row, int col) {
        int count = 0;
        int numRows = grid.length;
        int numCols = grid[0].length;

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
                if (grid[newRow][newCol] == -1) {
                    count++;
                }
            }
        }

        return count;
    }

    /*
     * Receives coordinates and returns the value at those coordinates
     */
    public static int getValueAtCoordinate(int[][] grid, int row, int col) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
            return grid[row][col];
        } else {
            return -1; // Mine
        }
    }
}
