package se.hig.taichi.project.kart_app.control;

import se.hig.taichi.project.kart_app.model.Map;
import se.hig.taichi.project.kart_app.storage.FileMapDAO;
import se.hig.taichi.project.kart_app.storage.MapDAO;
import se.hig.taichi.project.kart_app.view.Observer;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * This class creates Map-object and interface MapDAO.
 */
public class MapManager
{
  /**
   * Stores new FileMapDao.
   */	
  private MapDAO mapDAO;
  
  /**
   * Stores new Map.
   */	
  private Map map;
  
  /**
   * Creates new Map and new FileMapDao
   */
  public MapManager()
  {
    map = new Map();
    mapDAO = new FileMapDAO();
  }
  
  /**
   * It call method createPoint and passes coordinate value to model element.
   * @param x Data of x coordinate
   * @param y Data of y coordinate
   */
  public void addPoint(int x, int y)
  {
    map.createPoint(x,y);
  }
  
  /**
   * It calls method createLinePoint and passes coordinate value to model element.
   * @param x Data of x coordinate
   * @param y Data of y coordinate
   */
  public void addLinePoint(int x, int y)
  {
	map.createLinePoints(x,y);
  }
  
  /**
   * It calls method createLine and passes point number.
   * @param pointId1 Number of created point.
   * @param pointId2 Number of created point.
   */
  public void addLine(int pointId1, int pointId2)
  {
	  map.createLine(pointId1, pointId2);
  }
  
  /**
   * It calls method getBestWay and passes point number for finding
   * the shortest way.
   * @param startId1 Number of created point.
   * @param endId2 Number of created point.
   */
  public void getShortestWay(int startId, int endId)
  {
	  map.getBestWay(startId, endId);
  }
  
  /**
   * It calls method saveMap and passes Map object to print the data to a file.
   */
  public void saveMapValue()
  {
	  mapDAO.saveMap(map);
  }
  
  /**
   * It calls method registerObserver and passes observer object.
   */
  public void activateObserver(Observer observer)
  {
	  map.registerObserver(observer);
  }
}