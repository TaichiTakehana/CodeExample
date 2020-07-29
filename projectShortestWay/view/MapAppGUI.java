package se.hig.taichi.project.kart_app.view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.hig.taichi.project.kart_app.control.MapManager;
import se.hig.taichi.project.kart_app.model.Observable;

/**
 * @auther Taichi Takehana
 * @version 1.0
 * @Since 2018-10-31
 * This class implements Observer-interface so that Model-element can send notification through
 * the overriding method notify() to this class.
 * It creates GUI with text label, input field and button for the user and shows file chooser Dialog
 * for user to choose an image.
 * Also this class creates event listener to handle events for mouse clicking,
 * reading the inputs and clicking the button.
 */
public class MapAppGUI extends JFrame implements Observer
{
  private static final long serialVersionUID = 1L;
  
  /**
   * Stores new MapManeger.
   */
  private MapManager mapManager;
  
  /**
   * Stores new new ActionListener.
   */
  private ActionListener buttonListener;
  
  /**
   * Stores new new MauseListener.
   */
  private MouseListener mouseListener;
  
  /**
   * Stores new JFileChooser.
   */
  private JFileChooser fileChooser;
  
  /**
   * Stores new Image
   */
  private Image image;
  
  /**
   * Stores new JPanels.
   */
  private JPanel mainPanel, subPanel;
  
  /**
   * Stores new JButtons.
   */
  private List<JButton> jbuttonList;
  
  /**
   * Stores new JTextFields.
   */
  private List<JTextField> jtextFieldList;
  
  /**
   * Stores new JLabel.
   */
  private List<JLabel> jlabelList;
  
  /**
   * Stores new MapGraphics.
   */
  private MapGraphics mapGraphics;
  
  /**
   * Checks button click.
   */
  private boolean mauseClicked = false;
  
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
   * It stores mapManager and creates Arrays and new JFileChooser.
   * Also runs method makeGUI, makeListener and showImage.
   * @param mapManager
   */
  public MapAppGUI(MapManager mapManager)
  {
    this.mapManager = mapManager;
    jbuttonList = new ArrayList<>();
    jtextFieldList = new ArrayList<>();
    jlabelList = new ArrayList<>();
    makeGUI();
    fileChooser = new JFileChooser("Choose file");
    makeListener();
    showImage();
  }
  
  /**
   * It gets updated data from Model-element and sends the data to MapGraphics-class.  
   * @param observable receives Observable object from Model-element.
   */
  @Override
  public void notify(Observable observable)
  {
	if(pointExist == true)
	{
	  List<Integer> pointData = observable.getPointData();
	  mapGraphics.addPoint(pointData);
	  pointExist = false;
	}else if (lineExist == true)
	{
	  List<Integer> lineData = observable.getLineData();
	  mapGraphics.addLine(lineData);
	  lineExist = false;
	}else if (bestWayExist == true)
	{
	  List<Integer> wayData = observable.getWayData();
	  mapGraphics.addBestWay(wayData);
	  bestWayExist = false;
	}
  }
  
  /**
   * It creates GUI and displays a graphical window to the user. 
   */
  public void makeGUI()
  {  
    setSize(670,1020);
    setTitle("Find the best way map");
    
    mapGraphics = new MapGraphics();
    mainPanel = new JPanel();
    subPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(0,1));
    subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.PAGE_AXIS));
    
	jlabelList.add(new JLabel("Choose a start point of the line"));
	jlabelList.add(new JLabel("Choose a end point of the line"));
	jlabelList.add(new JLabel("Choose a start point to find the shortest way"));
	jlabelList.add(new JLabel("Choose a end point to find the shortest way"));
	
	jtextFieldList.add(new JTextField());
	jtextFieldList.add(new JTextField());
	jtextFieldList.add(new JTextField());
	jtextFieldList.add(new JTextField());
	
	jbuttonList.add(new JButton("Create a point"));
	jbuttonList.add(new JButton("Create a line"));
	jbuttonList.add(new JButton("Find the best way"));
	jbuttonList.add(new JButton("Save"));
	
	subPanel.add(jbuttonList.get(0));
	subPanel.add(jlabelList.get(0));
	subPanel.add(jtextFieldList.get(0));
	subPanel.add(jlabelList.get(1));
	subPanel.add(jtextFieldList.get(1));
	subPanel.add(jbuttonList.get(1));
	subPanel.add(jlabelList.get(2));
	subPanel.add(jtextFieldList.get(2));
	subPanel.add(jlabelList.get(3));
	subPanel.add(jtextFieldList.get(3));
	subPanel.add(jbuttonList.get(2));
	subPanel.add(jbuttonList.get(3));
	
	mainPanel.add(mapGraphics);
	mainPanel.add(subPanel);
    add(mainPanel);
    
    setVisible(true);
    //setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  /**
   * Method makeListener creates and implements MouseListener and ActionListener.
   * ActionListener responds to button events and through MouseListener,
   * it calls method addPoint and addLinePoint for storing the value.
   * Also it converts JTextField value to integer and sends value to MapManager-class.
   * Another button event calls saveMapValue method to pass the data to Map-class for
   * printing out the data and save to the file. 
   */
  private void makeListener()
  {  
    buttonListener = new ActionListener()
    {
	  @Override
	  public void actionPerformed(ActionEvent e)
	  {
		if(e.getSource() == jbuttonList.get(0))
		{
	      if(mauseClicked == true)
	    	mauseClicked = false;
		  else
			mauseClicked = true;
		}else if(e.getSource() == jbuttonList.get(1))
		{
		  String number1 = jtextFieldList.get(0).getText();
		  String number2 = jtextFieldList.get(1).getText();
		  lineExist = true;
		  
		  try 
		  {
		    int validate1 = Integer.parseInt(number1);
		    int validate2 = Integer.parseInt(number2);
		    mapManager.addLine(validate1, validate2);
		  }
		  catch(NumberFormatException ex)
		  {
		    System.out.println("Put a number! " + ex);
		  }
		}else if(e.getSource() == jbuttonList.get(2))
		{
		  String number1 = jtextFieldList.get(2).getText();
	      String number2 = jtextFieldList.get(3).getText();
	      bestWayExist = true;
	      
	      try 
		  {
		    int validate1 = Integer.parseInt(number1);
		    int validate2 = Integer.parseInt(number2);
		    mapManager.getShortestWay(validate1, validate2);
		  }
		  catch(NumberFormatException ex)
		  {
		    System.out.println("Put a number! " + ex);
		  } 
		}else if(e.getSource() == jbuttonList.get(3))
		{
		  mapManager.saveMapValue();
		}
	  }
    };
    
    mouseListener = new MouseListener()
	  {
	    @Override
	    public void mouseClicked(MouseEvent e)
	    {
		  if(mauseClicked == true)
		  {
		    pointExist = true;
		    mapManager.addPoint(e.getX(), e.getY());
		    mapManager.addLinePoint(e.getX(), e.getY());
	      }
	    }
		@Override
		public void mousePressed(MouseEvent e)
		{
	    }
	    @Override
	    public void mouseReleased(MouseEvent e)
      {
	    }
		@Override
		public void mouseEntered(MouseEvent e)
		{	
		}
		@Override
		public void mouseExited(MouseEvent e)
		{	
		}
    };	
    
    jbuttonList.get(0).addActionListener(buttonListener);
	jbuttonList.get(1).addActionListener(buttonListener);
	jbuttonList.get(2).addActionListener(buttonListener);
	jbuttonList.get(3).addActionListener(buttonListener);
	mapGraphics.addMouseListener(mouseListener);
  }
  
  /**
   * Method showImage creates fileChooser instants and show the dialog for user to choose
   * an image. This image sends to mapGraphics-class method getImage and it appears on the panel.
   */
  public void showImage()
  {
	  try
	  { 
		fileChooser.showOpenDialog(mapGraphics);
	    File f = fileChooser.getSelectedFile();
		image = ImageIO.read(f);
		mapGraphics.getImage(image);
	  }
	  catch(Exception ex)
	  {
	    System.out.println("Choose a image!: " + ex);
      }
  }
}