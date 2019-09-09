/*Jesualdo Cristovao
Game Of Life Project
January 17, 2018*/

public class EnvironmentDriver
{

	public static void main(String[] args) throws Exception
	{
		String fileName = "C:\\Users\\jesuy\\eclipse-workspace\\GameOfLife4.txt";  // Path of the txt file that contains inputString fileName = "GameOfLife1.txt";
		Environment e = new Environment(fileName);
        e.runSimulation();


	}

}
