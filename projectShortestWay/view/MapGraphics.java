package se.hig.taichi.project.kart_app.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * This class extends JPanel and draw points, line and the shortest way on the image
 * through overriding the paintComponent method.
 */
@SuppressWarnings("serial")
public class MapGraphics extends JPanel
{
  /**
   * Stores new Graphics2D.
   */
  private Graphics2D graphics2D;
  
  /**
   * Stores new Image.
   */
  private Image image;
  
  /**
   * Stores new Line2D.
   */
  private List<Line2D> geoLineList;
  
  /**
   * Stores new Point2D.
   */
  private List<Point2D> geoPointList;
  
  /**
   * Stores index of a list.
   */
  private List<Integer> pointIdList;
  
  /**
   * Stores new Line2D.
   */
  private List<Integer> bestWayList;
  
  /**
   * Checks if point exist.
   */
  private boolean pointExist = false;
  
  /**
   * Checks if line exist.
   */
  private boolean lineExist = false;
  
  /**
   * Checks if shortest way exist.
   */
  private boolean bestWayExist = false;
  
  /**
   * It creates arrays.
   */
  public MapGraphics()
  {
	geoPointList = new ArrayList<>();
	geoLineList = new ArrayList<>();
	pointIdList = new ArrayList<>();
	bestWayList = new ArrayList<>();
  }
  
  /**
   * It draws point, lines and shortest way only if those data exist.
   * @param g　Stores Graphics-object
   */
  @Override
  public void paintComponent(Graphics g)
  {
	super.paintComponent(g);
	
	if(pointExist == true)
    {
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
      graphics2D = (Graphics2D) g;
      drawPoint();
      drawLine(); 
      drawBestWay();
      pointExist = false;
    }else if(lineExist == true)
	{
	  g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	  graphics2D = (Graphics2D) g;
	  drawPoint();
      drawLine();
      drawBestWay();
	  lineExist = false;
    }else if(bestWayExist == true)
    {
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
      graphics2D = (Graphics2D) g;
      drawPoint();
      drawLine();
      drawBestWay();
      bestWayExist = false;
    }else
    {
     g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
  }
  
  /**
   * It stores an image and draws the image on the JPanel.
   * @param image It receives image object from MapAppGUI-class.
   */
  public void getImage(Image image)
  {
	this.image = image;
	repaint();
  }
  
  /**
   * It gets data from MapAppGUI-class and store the point X and Y value
   * in the created Point2D-list and JComponent method repaint draws the updated point.
   * @param pointData List of point which contains X and Y coordinate values.
   */
  public void addPoint(List<Integer> pointData)
  {
	geoPointList.add(new Point2D.Double(pointData.get(0), pointData.get(1)));
  	pointIdList.add(pointData.get(2));
  	pointExist = true;
  	repaint();
  }
  
  /**
   * It gets data from MapAppGUI-class and store the two point's X and Y values in the
   * valuable and creates line2D-list and JComponent method repaint draws the updated line.
   * @param lineData List of line which contains X and Y coordinate values of the line.
   * @throw NullPointerException if the list contains null value.
   */
  public void addLine(List<Integer> lineData)
  {
	try
	{
	  int x1, y1, x2, y2;
	  x1 = lineData.get(0);
	  y1 = lineData.get(1);
	  x2 = lineData.get(2);
	  y2 = lineData.get(3);
	  geoLineList.add(new Line2D.Double(x1, y1, x2, y2));
	  lineExist = true;
	  repaint();
	}
	catch(Exception e)
	{
      System.out.println("Make sure the number of the points are correct! ");	
	}
  }
  
  /**
   * It stores data to a shortest way-list and draw the lines.
   * JComponent method repaint draws the updated lines.
   * @param wayData List of shortest way which contains X and Y coordinate values of lines.
   */
  public void addBestWay(List<Integer> wayData)
  {
  	for(Integer i : wayData)
  	  this.bestWayList.add(i);
  	
  	bestWayExist = true;
  	repaint();
  }
  
  /**
   * It gets data from a list draws points and point-ID for the point.
   */
  public void drawPoint()
  {
	graphics2D.setColor(Color.BLACK);
	
   	for(int i = 0; i < geoPointList.size(); i++)
   	{
	  graphics2D.drawString(Integer.toString(pointIdList.get(i)),  (int) geoPointList.get(i).getX(), 
   	  (int) geoPointList.get(i).getY());
      graphics2D.drawOval((int) geoPointList.get(i).getX(), 
      (int) geoPointList.get(i).getY(), 7, 7);
      graphics2D.fillOval((int) geoPointList.get(i).getX(), 
      (int) geoPointList.get(i).getY(), 7, 7);
    }
  }
  
  /**
   * It gets data from a list and draws lines.
   */
  public void drawLine()
  {
	graphics2D.setStroke(new BasicStroke(5));
	graphics2D.setColor(Color.GRAY);
	
  	for(int i = 0; i < geoLineList.size(); i++)
  	{
  	  graphics2D.draw(geoLineList.get(i));
    }
  }
  
  /**
   * It gets data from a list and creates the shortest ways and draw them.
   */
  public void drawBestWay()
  {
	int counter = 0;
	int x1, y1, x2, y2;

	graphics2D.setStroke(new BasicStroke(5));
	graphics2D.setColor(Color.BLUE);
    
  	while(counter < bestWayList.size())
  	{
  	  x1 = bestWayList.get(counter);
      counter++;
  	  y1 = bestWayList.get(counter);
  	  counter++;
  	  x2 = bestWayList.get(counter);
  	  counter++;
  	  y2 = bestWayList.get(counter);
  	  counter++;
  	  graphics2D.draw(new Line2D.Double(x1, y1, x2, y2));
  	}
  }
}
