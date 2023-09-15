import java.util.Scanner;

public class testing3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        double probability = Double.parseDouble(args[2]);
        int[][] grid = createGrid(row, col, probability);
        int[][] seen = createGrid(row, col, 0);
        int[][] flags = createGrid(row, col, 0); // Maintain a separate grid for flags

        // Calculate and update the grid with adjacent mines.
        grid = calculateAdjacentMines(seen);
        displayGrid(seen, flags);

        boolean gameOver = false;
        while (!gameOver) {
            System.out.print("Enter row and column (example: 1 2) or 'flag x y': ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Game quit.");
                break;
            }

            String[] tokens = input.split(" ");
            if (tokens.length == 2) {
                int x = Integer.parseInt(tokens[0]);
                int y = Integer.parseInt(tokens[1]);
                seen = click(x, y, seen, grid);

                // Check for game over
                if (grid[x][y] == -1) {
                    gameOver = true;
                    displayGrid(grid, flags); // Reveal all mines
                    System.out.println("Game over. You hit a mine!");
                }
            } else if (tokens.length == 3 && tokens[0].equalsIgnoreCase("flag")) {
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                flags[x][y] = 1; // Set a flag
            } else {
                System.out.println("Invalid input. Please enter coordinates (example: '1 2') or 'flag x y'.");
            }

            showGame(seen);
            if (isGameComplete(seen, flags, grid)) {
                gameOver = true;
                displayGrid(grid, flags); // Reveal all mines
                System.out.println("Congratulations! You win!");
            }
        }

        scanner.close();
    }

    /*
         * Receives the size of the grid , and the probability of a mine.
         * Creates a matching random grid.
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

        // Receives a grid, and prints it.
        public static void displayGrid(int[][] seen, int[][] flags) {
            for (int i = 0; i < seen.length; i++) {
                for (int j = 0; j < seen[i].length; j++) {
                    if (flags[i][j] == 1) {
                        System.out.print("F "); // Display flag
                    } else if (seen[i][j] == -1) {
                        System.out.print("x "); // Display mine
                    } else {
                        System.out.print(seen[i][j] + " ");
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
        
        // We flag a tile with the value " -9 ".
        public static int[][] flag(int x, int y, int[][] flags) {
            if (flags[x][y] == 0) {
                flags[x][y] = 1; // Set a flag
            } else if (flags[x][y] == 1) {
                flags[x][y] = 0; // Remove the flag
            }
            return flags;
        }
        
    
        // Show the screen.
    public static void showGame(int[][] seen){
        System.out.println("  ");
        displayGrid(seen, flags);
    }

    public static void gameOver(){
        System.out.println("Boom!");
    }
        /*
        When we use the click function:
        -We check if the index is out of bound.
        -We check if we clicked on a mine.
        -We check if we clicked on a number.
        -If not both, then the tile is empty and we mark it as checked (-7) and click on the tiles around it.
         */
        public static int[][] click(int x, int y, int[][] seen, int[][] grid){
            if (x<0||y<0||x>grid[0].length-1||y>grid.length-1){
                return seen;
            }
            if (grid[x][y]==-1){
                seen = createGrid(1,1,1);
                return seen;
            } else if (grid[x][y]!=0 && seen[x][y]!=-7) {
                seen[x][y]=grid[x][y];
                return seen;
            } else if (seen[x][y]==-7) {
                return seen;
            } else{
                seen[x][y]=-7;
                seen = click( x-1, y-1, seen, grid);
                seen = click( x, y-1, seen, grid);
                seen = click( x+1, y-1, seen, grid);
                seen = click( x-1, y, seen, grid);
                seen = click( x+1, y, seen, grid);
                seen = click( x-1, y+1, seen, grid);
                seen = click( x, y+1, seen, grid);
                seen = click( x+1, y+1, seen, grid);
            }
            return seen;
        }

        // Counts the number of adjacent mines for a given cell.
        public static int countAdjacentMines(int[][] grid, int row, int col) {
            int count = 0;
            int numRows = grid.length;
            int numCols = grid[0].length;

        // Define the relative positions of adjacent cells.
            int[][] directions = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1},           {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
            };

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

        // Check if the new coordinates are within the grid boundaries.
                if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols) {
        // Check if the adjacent cell contains a mine.
                    if (grid[newRow][newCol] == -1) {
                        count++;
                    }
                }
            }

            return count;
        }

        // Receives coordinates and returns the value at those coordinates.
        public static int getValueAtCoordinate(int[][] grid, int row, int col) {
            int numRows = grid.length;
            int numCols = grid[0].length;

            if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
                return grid[row][col];
            } else {
                return -1; // Mine
            }
        }

        public static boolean isGameComplete(int[][] seen, int[][] flags, int[][] grid) {
            int numRows = seen.length;
            int numCols = seen[0].length;
        
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (grid[row][col] == -1 && flags[row][col] != 1) {
                        return false; // A mine is not flagged
                    }
                    if (grid[row][col] != -1 && seen[row][col] == 0) {
                        return false; // An empty cell is not revealed
                    }
                }
            }
        
            System.out.println("Congratulations! You win!");
            return true; // All mines are flagged and all non-mine cells are revealed
        }
        

}
