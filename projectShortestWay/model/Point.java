package se.hig.taichi.project.kart_app.model;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * Point class creates point-object with X and Y coordinate value
 * and also index of a list as an ID. 
 * Method getX, getY and getId returns the value that stored in this class.
 * Method toString returns a String value for printing out point values.
 */
public class Point
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
	 * Stores X and Y coordinate value and also index of a list as an ID.
	 * @param x X coordinate value.
	 * @param y Y coordinate value.
	 * @param id Index of a list.
	 */
	public Point(int x, int y, int id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
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
	 * Returns text string with x, y and id.
	 * @return String value of a point data.
	 */
	@Override
    public String toString ()
	{
        return "Point " + id + " @(" + x + ":" + y + ")";
    }
}
