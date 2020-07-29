package se.hig.taichi.project.kart_app.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import se.hig.taichi.project.kart_app.view.Observer;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * Map class extends Observable-object so that it can notify MapAppGUI-object
 * when the data has updated by using five overriding method. 
 */
public class Map extends Observable
{ 
  /**
   * Stores point coordinate.
   */
  private List<Point> pointList;
  
  /**
   * Stores point coordinate.
   */
  private List<Line> linePointsList;
  
  /**
   * Stores start and end point coordinate's of a line.
   */
  private List<Line> lineList;
  
  /**
   * Stores start and end point coordinate's of a shortest way.
   */
  private List<Integer> bestWayList;
  
  /**
   * Stores calculated shortest path.
   */
  private List<Line> shortestPath;
  
  /**
   * Stores observer object.
   */
  private List<Observer> observers;
  
  /**
   * It creates arrays.
   */
  public Map()
  {
    pointList = new ArrayList<>();
    linePointsList = new ArrayList<>();
    lineList = new ArrayList<>();
    bestWayList = new ArrayList<>();
    observers = new ArrayList<Observer>();
  }
  
  /**
   * It stores observer-valuable to a list.
   * @param observer New Observer.
   */
  @Override
  public void registerObserver(Observer observer)
  {
    observers.add(observer);
  }
  
  /**
   * It sends message to observer-object when new data has stored.
   */
  @Override
  public void notifyObserver()
  {
    for (Observer observer : observers)
      observer.notify(this);  	
  }
  
  /**
   * It returns point data which called by getPointCoordinate.
   * @return List of point coordinate.
   */
  @Override
  public List<Integer> getPointData()
  {
	List<Integer> pointData = getPointCoordinate(pointList.size() - 1);
	return pointData;
  }
  
  /**
   * It returns list of X, Y and ID values stored in Point-object.
   * @param id Index of point list.
   * @return List of point coordinate.
   */
  public List<Integer> getPointCoordinate(int id)
  {
	List<Integer> pointCoordinate = new ArrayList<>();
	pointCoordinate.add(pointList.get(id).getX());
	pointCoordinate.add(pointList.get(id).getY());
	pointCoordinate.add(pointList.get(id).getId());
	return pointCoordinate;
  }
  
  /**
   * It returns line data which called by getLineCoordinate.
   * @return List of start and end point's coordinate.
   */
  @Override
  public List<Integer> getLineData()
  {
	List<Integer> lineData = getLineCoordinate(lineList.size() - 1);
    return lineData;
  }
  
  /**
   * It returns list of X and Y values stored in Line-object.
   * @param id Index of line list.
   * @throws NullPointerException if the list contains null value.
   * @return List of start and end point's coordinate.
   * @return null.
   */
  public List<Integer> getLineCoordinate(int id)
  {
	try
	{
	  List<Integer> lineCoordinate = new ArrayList<>();
	  lineCoordinate.add(lineList.get(id).getStartPoint().getX());
	  lineCoordinate.add(lineList.get(id).getStartPoint().getY());
	  lineCoordinate.add(lineList.get(id).getEndPoint().getX());
	  lineCoordinate.add(lineList.get(id).getEndPoint().getY());
	  return lineCoordinate;
	}
	catch(Exception e)
	{
	  System.out.println("Point does not exist!: " + e);	
	}
	  return null;
  }

  /**
   * It returns data of a shortest way that was calculated in getBestWay method.
   * @return List of shortest way which contains point's coordinate of line.
   */@Override
  public List<Integer> getWayData()
  {
  	return bestWayList;
  } 
  
  /**
   * It creates Point-object and store the X, Y and index of the list as an ID to a list.
   * Also calls method notifyObserver.
   * @param x Data of x coordinate
   * @param y Data of y coordinate
   */
  public void createPoint(int x, int y)
  {
  	Point point = new Point(x, y, pointList.size());
  	pointList.add(point);
  	notifyObserver();
  }
  
  /**
   * It creates Line-object and store the X, Y and index of the list as an ID to a list.
   * Also calls method notifyObserver.
   * @param x Data of x coordinate
   * @param y Data of y coordinate
   */
  public void createLinePoints(int x, int y)
  {
	Line linePoint = new Line(x, y, linePointsList.size());
	linePointsList.add(linePoint);
	notifyObserver();
  }

  /**
   * It creates Line-object and store the X, Y coordinate and length of the line to a list.
   * Also calls method notifyObserver.
   * @param lineId1 Index of a list.
   * @param lineId2 Index of a list.
   * @param x1 X value of the start point.
   * @param x2 X value of the end point.
   * @param y1 Y value of the start point.
   * @param y1 Y value of the end point.
   * @param length Length between the points.
   * @throws NullPointerException if the list contains null value.
   */
  public void createLine(int lineId1, int lineId2)
  {
	try
	{
	  int x1, y1, x2, y2;
	  int length;
	  x1 = linePointsList.get(lineId1).getX();
	  y1 = linePointsList.get(lineId1).getY();
	  x2 = linePointsList.get(lineId2).getX();
	  y2 = linePointsList.get(lineId2).getY();
	  if(x1 != x2 && x1 != x2)
	  {
	    int xdistance = (Math.max(x1, x2) - Math.min(x1, x2));
	    int ydistance = (Math.max(y1, y2) - Math.min(y1, y2));
	    length =(int) Math.sqrt((Math.pow(xdistance, 2)) + (Math.pow(ydistance, 2)));
	    lineList.add(new Line(linePointsList.get(lineId1), linePointsList.get(lineId2), length));
	  }
	  notifyObserver(); 
	}
	catch(Exception e)
	{
      System.out.println("Point is missing!: " + e);	
	}
  }

  /**
   * It calculate the shortest way of the existed points and lines and stores to a list.
   * Also calls method notifyObserver.
   * @throws NullPointerException if the list contains null value.
   */
  public void getBestWay(int startId, int endId)
  {
    SimpleWeightedGraph<Line, Line> line = new SimpleWeightedGraph<>(Line.class);
    
    for(Line linePoint : linePointsList)
	  line.addVertex(linePoint);
    
    for(Line we : lineList)
    {	
	  line.addEdge(we.getStartPoint(), we.getEndPoint(), we);
	  line.setEdgeWeight(we, we.getLength());
	}
    List<Line> points = new ArrayList<>();
	Iterator<Line> pointIt = line.vertexSet ().iterator ();
	
    while(pointIt.hasNext ())
      points.add(pointIt.next ());
    
    List<Line> lines = new ArrayList<>();
	Iterator<Line> lineIt = line.edgeSet().iterator();
	
	while(lineIt.hasNext())
	  lines.add(lineIt.next());
    
	try
	{
      DijkstraShortestPath<Line, Line> sp = 
    		new DijkstraShortestPath<Line, Line> (line,
    				linePointsList.get(startId), linePointsList.get(endId));
      GraphPath<Line, Line> gp = sp.getPath();
      List<Line> shortestPath = gp.getEdgeList ();
      this.shortestPath = shortestPath;
      
      for(Line l : shortestPath)
      {
    	bestWayList.add((int) l.getStartPoint().getX());
    	bestWayList.add((int) l.getStartPoint().getY());
    	bestWayList.add((int) l.getEndPoint().getX());
    	bestWayList.add((int) l.getEndPoint().getY());
	  }
	}
	catch(Exception e)
	{
	  System.out.println("Line is missing!: " + e);	
	}
    notifyObserver();
  }
  
  /**
   * It returns list of point's coordinate for printing them out.
   * @return All the point coordinates with ID as a list.
   */
  public List<Point> getPointList()
  {
    return pointList;
  }
	
  /**
   * It returns list of the line for printing them out.
   * @return All the line with length as a list.
   */
  public List<Line> getLineList()
  {
	return lineList;
  }
  
  /**
   * It returns list of the shortest way for printing them out.
   * @return All the shortest way with length as a list.
   */
  public List<Line> getShortestPath()
  {
	return shortestPath;
  }
}
