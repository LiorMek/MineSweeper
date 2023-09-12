/*
-9 == flag
-7 == checked and empty
-1 == mine
0 == empty and unchecked
number == next to a mine
 */
import java.util.Scanner;

public class testing2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        double probability = Double.parseDouble(args[2]);
        int[][] grid = createGrid(row, col, probability);
        char[][] seen = createSeenGrid(row, col);

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

            String[] tokens = input.split(" ");
            if (tokens.length == 2) {
                int x = Integer.parseInt(tokens[0]);
                int y = Integer.parseInt(tokens[1]);

                if (isValidCoordinate(x, y, row, col)) {
                    if (grid[x][y] == -1) {
                        gameOver = true;
                    } else if (grid[x][y] == 0 && seen[x][y] == ' ') {
                        click(x, y, seen, grid);
                    } else if (Character.isDigit(seen[x][y])) {
                        System.out.println("Tile already revealed.");
                    }
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                }
            } else if (tokens.length == 3 && tokens[0].equalsIgnoreCase("flag")) {
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);

                if (isValidCoordinate(x, y, row, col) && seen[x][y] == ' ') {
                    seen[x][y] = 'F';
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
                    grid[i][j] = 0; // Empty
                }
            }
        }
        return grid;
    }

    public static char[][] createSeenGrid(int row, int col) {
        char[][] seen = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                seen[i][j] = ' '; // Not revealed
            }
        }
        return seen;
    }

    public static void displayGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isValidCoordinate(int x, int y, int row, int col) {
        return x >= 0 && x < row && y >= 0 && y < col;
    }

    public static void click(int x, int y, char[][] seen, int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;
    
        // Check if the coordinates are out of bounds or if the tile is already revealed.
        if (x < 0 || x >= numRows || y < 0 || y >= numCols || seen[x][y] != ' ') {
            return;
        }
    
        // Reveal the tile.
        seen[x][y] = (char) ('0' + grid[x][y]); // Convert the integer to a character
    
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

    public static boolean isGameWon(char[][] seen, int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;
    
        // Iterate through the grid to check for win conditions.
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                // If there is a mine that is not flagged, the game is not won.
                if (grid[x][y] == -1 && seen[x][y] != 'F') {
                    return false;
                }
                // If there is an empty tile that is not revealed, the game is not won.
                if (grid[x][y] == 0 && seen[x][y] == ' ') {
                    return false;
                }
            }
        }
    
        // If no mines are left and all tiles are revealed or flagged, the game is won.
        return true;
    }
}
