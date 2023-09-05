public class step3 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please input 2 arguments");
            return;
        }

        int[][] grid = {
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0},
            {0, 0, 0, 0, 1},
            {1, 0, 1, 0, 0},
            {0, 1, 0, 0, 0}
        };

        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);

        int value = getValueAtCoordinate(grid, row, col);

        if (value != -1) {
            System.out.println("Value at coordinate (" + row + ", " + col + ") is: " + value);
        } else {
            System.out.println("Coordinate (" + row + ", " + col + ") is out of bounds.");
        }
    }

    public static int getValueAtCoordinate(int[][] grid, int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            return grid[row][col];
        } else {
            return -1;
        }
    }
}
