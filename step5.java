public class step5 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please input 2 arguments");
            return;
        }

        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);

        int[][] grid = {
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0},
            {0, 0, 0, 0, 1},
            {1, 0, 1, 0, 0},
            {0, 1, 0, 0, 0}
        };

        System.out.println("Initial Grid:");
        printGrid(grid);

        // Example: Flag/Unflag a tile at coordinates provided as command-line arguments
        int[][] updatedGrid = flagTile(grid, row, col);

        System.out.println("\nUpdated Grid:");
        printGrid(updatedGrid);
    }

    public static int[][] flagTile(int[][] grid, int row, int col) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        // Check if the clicked coordinate is within bounds
        if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
            // Update the status of the clicked tile (modify as needed)
            // In this example, we use a flag value (e.g., 2) to represent a flagged tile
            if (grid[row][col] == 0) {
                grid[row][col] = 2; // Flag the tile (0 to 2)
            } else if (grid[row][col] == 2) {
                grid[row][col] = 0; // Unflag the tile (2 to 0)
            }
        }

        return grid; // Return the updated 2D array
    }

    public static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}