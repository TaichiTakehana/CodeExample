package se.hig.taichi.project.kart_app.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import se.hig.taichi.project.kart_app.model.Line;
import se.hig.taichi.project.kart_app.model.Map;
import se.hig.taichi.project.kart_app.model.Point;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * This class implements MapDAO and has a roll to manage the data.
 * It gets data from Map-object and print them out to a file.
 */
public class FileMapDAO implements MapDAO
{
  private File dataFile;
  private PrintWriter printData;
  List<Point> pointList;
  List<Line> lineList;
  List<Line> shortestPath;
  
  /**
   * It implements saveMap. It gets data from Model-element and stores to a list.
   * Also run method makeFile and writeFile.
   * @param map Contains Map-object.
   */
  @Override
  public void saveMap(Map map)
  {
	this.pointList = map.getPointList();
	this.lineList = map.getLineList();
	this.shortestPath = map.getShortestPath();
	makeFile();
	writeFile();
  }
  
  /**
   * It creates new File-object and PrintWriter-object.
   * @Throw FileNotFoundException if file doesn't exist.
   */
  public void makeFile()
  {
    try
    {
      dataFile = new File("Map data.txt");
      printData = new PrintWriter(dataFile);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * It prints the data.
   * @Throw Exception if it couldn't print.
   */
  public void writeFile()
  {
    try
    {	  
	  printData.println("Printing all points:");
	    for(Point p : pointList)
	      printData.println(p);
	    
	  printData.println("Printing all lines:");
	    for(Line l : lineList)
	      printData.println(l);
	  
	  printData.println("Printing the best way:");
	    for(Line l: shortestPath)
	      printData.println(l); 

	  printData.close(); 
      
    }
    catch (Exception ex)
    {
      System.err.println("Could not save!" + ex);
    }
  }
}