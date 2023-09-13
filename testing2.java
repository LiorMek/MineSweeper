import java.util.Scanner;

public class testing2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        double probability = Double.parseDouble(args[2]);
        int[][] grid = createGrid(row, col, probability);
        int[][] seen = createSeenGrid(row, col);

        boolean gameOver = false;
        boolean gameWon = false;

        while (!gameOver) {
            displayGrid(seen);
            System.out.print("Enter row and column (example: 1 2) or 'flag x y': ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Game quit.");
                break;
            }

            String[] tokens = input.split(" "); // Splits the user's input into an array of strings using space as the delimiter.
            if (tokens.length == 2) {
                int x = Integer.parseInt(tokens[0]);
                int y = Integer.parseInt(tokens[1]);

                if (isValidCoordinate(x, y, row, col)) {
                    if (grid[x][y] == -1) {
                        gameOver = true; // If the selected tile contains a mine (value is -1), the game is over, and gameOver is set to true.
                    } else if (grid[x][y] == 0 && seen[x][y] == 0) {
                        click(x, y, seen, grid); // If the selected tile is empty (value is 0) and has not been previously revealed, the click method is called to reveal this and neighboring tiles.
                    } else if (grid[x][y] > 0 && seen[x][y] == 0) {
                        seen[x][y] = grid[x][y]; // If the selected tile contains a number (greater than 0) and has not been previously revealed, it is revealed with the same number.
                    } else {
                        System.out.println("Tile already revealed.");
                    }
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                }
            } else if (tokens.length == 3 && tokens[0].equalsIgnoreCase("flag")) {
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);

                if (isValidCoordinate(x, y, row, col) && seen[x][y] == 0) {
                    seen[x][y] = -9; // Flag
                } else {
                    System.out.println("Invalid coordinates or tile already revealed. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter coordinates (example: '1 2') or 'flag x y'.");
            }

            gameWon = isGameWon(seen, grid);
            if (gameWon) {
                System.out.println("Congratulations! You win!");
                displayGrid(grid);
                break;
            } else if (gameOver) {
                System.out.println("Game over. You hit a mine!");
                displayGrid(grid);
            }
        }

        scanner.close();
    }

    public static int[][] createGrid(int row, int col, double probability) {
        int[][] grid = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (Math.random() < probability) {
                    grid[i][j] = -1; // Mine
                } else {
                    grid[i][j] = 0; // Non-Mine
                }
            }
        }
        return grid;
    }

    public static int[][] createSeenGrid(int row, int col) {
        int[][] seen = new int[row][col];
        return seen;
    }

    public static void displayGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    // Check whether a given pair of coordinates falls within the valid range of a grid with rows and columns.
    public static boolean isValidCoordinate(int x, int y, int row, int col) {
        return x >= 0 && x < row && y >= 0 && y < col;
    }

    public static void click(int x, int y, int[][] seen, int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        // Check if the coordinates are out of bounds or if the tile is already revealed.
        if (x < 0 || x >= numRows || y < 0 || y >= numCols || seen[x][y] != 0) {
            return;
        }

        // Reveal the tile.
        seen[x][y] = grid[x][y];

        // If the revealed tile is empty (0), recursively reveal neighboring tiles.
        if (grid[x][y] == 0) {
            // Define the relative positions of adjacent cells.
            int[][] directions = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1},           {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
            };

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                // Recursively call click on adjacent tiles.
                click(newX, newY, seen, grid);
            }
        }
    }

    public static boolean isGameWon(int[][] seen, int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        // Iterate through the grid to check for win conditions.
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                // If there is a mine that is not flagged, the game is not won.
                if (grid[x][y] == -1 && seen[x][y] != -9) {
                    return false;
                }
                // If there is an empty tile that is not revealed, the game is not won.
                if (grid[x][y] == 0 && seen[x][y] == 0) {
                    return false;
                }
            }
        }

        // If no mines are left and all tiles are revealed or flagged, the game is won.
        return true;
    }
}