package se.hig.taichi.project.kart_app.model;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * This class extends DefaultWeightedEdge class for calculating the shortest way.
 * There are two constructors. First one uses for storing X and Y coordinate value
 * and index of a list as an ID. 
 * Second one uses for storing X and Y coordinate values of starting and ending point for
 * calculation with length between them.
 * Method getStartPoint, getEndPoint and getLength returns the stored data in this class.
 * Method toString returns String value for printing out the stored data.
 */
@SuppressWarnings("serial")
public class Line extends DefaultWeightedEdge
{
  /**
   * Stores X coordinate of a point.
   */
  private int x;
  
  /**
   * Stores Y coordinate of a point.
   */
  private int y;
  
  /**
   * Stores Index of a list.
   */
  private int id;
  
  /**
   * Stores X, Y coordinate of a start point for a line.
   */
  private Line startPoint;
  
  /**
   * Stores X, Y coordinate of a end point for a line.
   */
  private Line endPoint;
  
  /**
   * Stores length of a line.
   */
  private int length;
  
  /**
   * Stores X and Y coordinate value and also index of a list as an ID.
   * @param x X coordinate value.
   * @param y Y coordinate value.
   * @param id Index of a list.
   */
  public Line(int x, int y, int id)
  {
	this.x = x;
	this.y = y;
	this.id = id;
  }

  /**
   * Stores X and Y coordinate value and also length.
   * @param startPoint X and Y coordinate value.
   * @param endPoint X and Y coordinate value.
   * @param length Length of a line.
   */
  public Line(Line startPoint, Line endPoint, int length)
  {
	this.startPoint = startPoint;
	this.endPoint = endPoint;
	this.length = length;
  }

  /**
   * Returns X coordinate value of a point.
   * @return X coordinate value.
   */
  public int getX()
  {
    return x;
  }

  /**
   * Returns Y coordinate value of a point.
   * @return Y coordinate value.
   */
  public int getY()
  {
	return y;
  }
	
  /**
   * Returns index of a list.
   * @return id Index of a list.
   */
  public int getId()
  {
	return id;
  }
  
  /**
   * Returns X and Y coordinate value of a start point.
   * @return startPoint X and Y coordinate value.
   */
  public Line getStartPoint()
  {
    return startPoint;
  }
  
  /**
   * Returns X and Y coordinate value of a end point.
   * @return endPoint X and Y coordinate value.
   */
  public Line getEndPoint()
  {
    return endPoint;
  }
  
  /**
   * Returns length of a line.
   * @return length Length of a line.
   */
  public int getLength()
  {
    return length;
  }
  
  /**
   * Returns text string with start point, end point and length.
   * @return String value of a line data.
   */
  public String toString ()
  {
	return "Length: " + length + ": " + startPoint.getId () + " -> " + endPoint.getId ();
  }
}