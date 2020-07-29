package se.hig.taichi.project.kart_app;

import se.hig.taichi.project.kart_app.control.MapManager;
import se.hig.taichi.project.kart_app.view.MapAppGUI;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * Main class. It creates controller element and View element.
 * Also it activates observer.
 */
public class MapApp
{
	/**
	 * Method creates controller element and View element.
	 * Also it activates observer.
	 */	
	public MapApp()
    {
	    MapManager mapManager = new MapManager();
	    MapAppGUI mapAppGUI= new MapAppGUI(mapManager);
	    mapManager.activateObserver(mapAppGUI);
	}
	
	/**
	 * Main method. It calls method MapApp.
	 */	
    public static void main(String[] args)
	{
		new MapApp();
	}
}
