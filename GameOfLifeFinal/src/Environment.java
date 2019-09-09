/*Jesualdo Cristovao
Game Of Life Project
January 17, 2018*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import edu.princeton.cs.introcs.StdDraw;

public class Environment
{
    private int rowNum,colNum,changes;
    private Cell[][] grid;

   
    public Environment(String testFile) throws Exception
    {
        File file = new File(testFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        this.rowNum = Integer.parseInt(br.readLine());// Stores number of Rows from File
        this.colNum = Integer.parseInt(br.readLine());// Stores number of Columns from File
        this.grid = new Cell[rowNum][colNum]; //Sets the size of the 2D-array
        this.changes = 1;  // Keep track of number of changes for every generation
             
        String st;
        int i = 0;
        while((st = br.readLine()) != null && i<rowNum) //Initializing the 2d grid from the given input file
        {
            String[] temp = st.split(" ");
            
            for(int j=0;j<temp.length;j++) {
            	if (Integer.parseInt(temp[j]) == 1)
            	{
                this.grid[i][j]= new Cell(true);
            	}
            	if (Integer.parseInt(temp[j]) == 0)
            	{
                    this.grid[i][j]= new Cell(false);
                } 
            }
            	i++; 
        }
    }
  //  Displays the new generation grid creates new generation using specific rules

    public void runSimulation()
    {
        while(changes!=0) 
        {
            grid = newGen();
        }
        System.out.println("Population that lasts forever");
    }

// Creates new Generation grid using specified rules
    private Cell[][] newGen()
    {
        int nChanges = 0;
        Cell[][] newGrid = new Cell[rowNum][colNum]; // new grid
        for(int i=0; i<rowNum; i++)
        {
            for(int j=0; j<colNum; j++)
            {
                int countNeighbors = countN(i,j); // gets number of neighbours
                
                if((countNeighbors < 2 || countNeighbors > 3) && grid[i][j].getOccupied()== true) // Loneliness and Overcrowding(Kills creatures)
                { 
                    newGrid[i][j] = new Cell(false);                           
                    nChanges++;
                }
                else if ((countNeighbors == 3 && grid[i][j].getOccupied()== false)) // Empty cell with 3 neighbors (New creature is born) 
                {
                    newGrid[i][j]= new Cell(true);                            // Birth of new creature
                    nChanges++;
                }
                else newGrid[i][j] = new Cell(grid[i][j].getOccupied());
            }
  
        }
        changes = nChanges;
        drawGrid();
        return newGrid;
    }

    private int countN(int i, int j){ // Counts the neighbours for every living creature
        int count = 0;
      //Array NEIGHBOR used to check every cell around(max 8 possible neihgbors for each cell) (concatenated coordinates)
        final int[][] NEIGHBORS = {{-1, -1}, {-1, 0}, {-1, +1}, { 0, -1},{ 0, +1},{+1, -1}, {+1, 0}, {+1, +1}}; 
        for (int[] offset : NEIGHBORS) {
            if (validCell(i + offset[1], j + offset[0]))
            	{
            	count++;
            	}
        }
        return count;
    }
// Validate the cell 
    private boolean validCell(int i, int j)
    {
        return i>=0 && i<rowNum && j>=0 && j<colNum && grid[i][j].getOccupied() == true;
    }

// Draws the grid using StdDraw class 
    private void drawGrid()
    {
        StdDraw.setXscale(0,colNum);// Set dimensions of the grid based on input file
        StdDraw.setYscale(0,rowNum);//

        for(int i=0; i<colNum; i++)
        {
            for(int j=rowNum-1; j>=0; j--) 
            {
                if(grid[j][i].getOccupied() == false)
                {
                    StdDraw.setPenColor(StdDraw.WHITE);
                }
                else 
                {
                    StdDraw.setPenColor(StdDraw.RED);//Living Creatures (Red square)
                }
                StdDraw.filledSquare(i+0.5,j+0.5,0.5); // Dimensions of creatures
                StdDraw.setPenColor(StdDraw.BLACK);//Grid Lines
                StdDraw.setPenRadius(0.001);
                StdDraw.square(i+0.5,j+0.5,0.5);// Dimensions of each cell
            }
        }
        StdDraw.show();
       StdDraw.pause(500);// Waiting Time to create new generation
    }
}
