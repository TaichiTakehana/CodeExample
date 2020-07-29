package se.hig.taichi.project.kart_app.view;

import se.hig.taichi.project.kart_app.model.Observable;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * This interface has a method notify. View element has to implement this interface
 * sothat the Observable-object can notify View element.
 */
public interface Observer
{
  public void notify(Observable observable);
}
