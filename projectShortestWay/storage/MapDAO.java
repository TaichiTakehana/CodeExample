package se.hig.taichi.project.kart_app.storage;

import se.hig.taichi.project.kart_app.model.Map;
/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * Interface for managing the data. To make this program as MVC structure with DAO.
 * FileMapDAO has to implement this interface to work with the data.
 */
public interface MapDAO
{
  public void saveMap(Map map);
}
