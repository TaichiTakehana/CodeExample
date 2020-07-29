package se.hig.taichi.project.kart_app.model;


import java.util.List;

import se.hig.taichi.project.kart_app.view.Observer;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * This klass has five abstract method.
 * Model element has to extend this class, so that it can notify View element.
 */
public abstract class Observable
{
  public abstract void registerObserver(Observer observer);
  public abstract void notifyObserver();
  public abstract List<Integer> getPointData();
  public abstract List<Integer> getLineData();
  public abstract List<Integer> getWayData();
}
