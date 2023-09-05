public class step1{
	public static void main(String[] args){
	int row = Integer.parseInt(args[0]);
	int col = Integer.parseInt(args[1]);
	double probability = Double.parseDouble(args[2]);
	int[][] grid = createGrid(row, col, probability);
	displayGrid(grid);
}
	/*
	* receives the size of the grid nxm, and the probabiility of a mine.
	* creates a matching random grid
	*/
public static int[][] createGrid(int row, int col, double probabiility){
  	int [][] grid = new int[row][col];
	for (int i=0;i<row;i++){
		for (int j=0;j<col;j++){
			if (Math.random() < probabiility){
				grid[i][j] = -1;
			} else {
				grid[i][j] = 0;
			}
		}
	}
	return grid;
}
/*
*Receives a grid, and prints it
*/
public static void displayGrid(int[][] mineGrid){
	for (int i=0;i<mineGrid.length;i++){
		for (int j=0;j<mineGrid[i].length;j++){
			if (mineGrid[i][j] == -1){
				System.out.print("x ");
			} else{
				System.out.print(mineGrid[i][j] + " ");
			}
		}
		System.out.println();
	}
}
/*
* Recieves an array of integers from the user
* and prints its maximum element
*/
}