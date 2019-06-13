import javafx.scene.image.Image;


import java.util.ArrayList;
import java.util.HashMap;


/**
 * A world for use in the Location Viewer application. 
 * A world contains locations, items and the connection relationship between the locations.
 *
 * @author  Danyang Yu;
 * @version 2.0
 */
public class World {
	
	// key is location name + direction, value is the connected location name + direction.
	private HashMap<String,String > connections = new HashMap<String, String>(); 
	// key is the name of the location, value is the location
	private HashMap<String, Location> locations = new HashMap<String, Location>(); 
	//key is the name of the article, value is the item.
	private HashMap<String,Article > articles = new HashMap<String, Article>(); 
	//represent the present view, it is combination of location name and the number representation of direction
	private String present; 
	
	/**
	 * Create a world.
	 */
	public World() {
		present = "reception_0"; // default value
		loadLocations();
		loadArticles();
		loadConnections();
	}

	/**
	 * Store alternative locations into the world.
	 * Get the images of the locations from a local directory.
	 * Each location contains up to 4 images of different views.
	 *
	 */
	public void loadLocations() {
		
		ArrayList<String> names = new ArrayList<String>();
		names.add("reception");
		names.add("hall");
		names.add("outside");
		names.add("stairway");
		names.add("flat");
		
        
        for (int i = 0; i < names.size(); i++) {
        	String name = names.get(i);

        	//add new location to the world
        	if(!locations.containsKey(name)) {
        		locations.put(name, new Location(name)); // location is new
        		locations.get(name).setDirection("0");
        		locations.get(name).setDirection("90");
        		locations.get(name).setDirection("180");
        		locations.get(name).setDirection("270");
        	}else {
        		
        		// location is already exists in the world.
        		locations.get(name).setDirection("0");
        		locations.get(name).setDirection("90");
        		locations.get(name).setDirection("180");
        		locations.get(name).setDirection("270");
        	}
        	
        }	
        	
	}
	
	/**
	 * Store alternative items into the world.
	 * Get the images of the items from a local directory.
	 * Each items contains 1 image.
	 *
	 */
	public void loadArticles()  {
		ArrayList<String> names = new ArrayList<String>();
		names.add("apple");
		names.add("lemon");
		names.add("orange");
		
        for (int i = 0; i < names.size(); i++) {
        	Image image = new Image(names.get(i)+".png",65.0,65.0,false,false); 
        	
        	if(!articles.containsKey(names.get(i))){ //item is not exists in the world
        		articles.put(names.get(i), new Article(names.get(i),image));
        	}
        }
	}	
	
	/**
	 * Store connection relationship among locations into the world.
	 * 
	 */
	public void loadConnections() {		
		connections.put("reception_0", "hall_0");
		connections.put("hall_180", "reception_180");
		connections.put("hall_270", "stairway_90");
		connections.put("hall_0", "outside_0");
		connections.put("outside_180", "hall_180");
		connections.put("stairway_270", "hall_90");
		connections.put("stairway_0", "flat_270");
		connections.put("flat_90", "stairway_180");
		
	}	
	
	
	/**
	 * Return a image object from a location.
	 * If there is no matching image, return the image of  present view.
	 * 
	 *@param next the direction the user want to turn to.
	 *@return the image of request.
	 */
	public Image getDirection(String next) {
		
		String[] strs = present.split("_");
		int index = Integer.parseInt(strs[1]); // the number representation of a direction
		
		// set the value of present to the value of required view, and return the corresponding image.
		switch(next) {
			case "right":{
				
				if(index ==270) {
					index = 0;
				}else {
					index += 90;
				}
				
				present = strs[0]+"_"+index;
				return locations.get(strs[0]).getDirection(index);}
			
			case "left" :{ 
				
				if(index ==0) {
					index = 270;
				}else {
					index -= 90;
				}
				
				present = strs[0]+"_"+index;
				return locations.get(strs[0]).getDirection(index);}
			case "default" :{
				//get the first location's first view.
				present = "reception_0"; // the file name of the default view
				return locations.get("reception").getDirection(0);}
		}
		
		// the same image as the present image shown on the interface.
		return locations.get(strs[0]).getDirection(0);
	}
	
	/**
	 * Return a image object from next location.
	 * If there is a matching image, return it.
	 * If there is no matching image, return the image of present view.
	 * 
	 *@return the image of the request.
	 */
	public Image forwardLocation() {
		//check whether the present view have connection with others.
		if(connections.containsKey(present)) {
			//change to the connected view
			present = connections.get(present);	
		}
		String[] strs = present.split("_");
		return locations.get(strs[0]).getDirection(Integer.parseInt(strs[1]));
	}
	
	/**
	 * Return a image object from an item, and add the item to the specific location's view.
	 * If there is no matching image, return null.
	 * 
	 *@param name the name of the selected item.
	 *@return the image of the request.
	 */
	public Image setArticle(String name) {
		//check whether the item is exist in the world.
		if(articles.containsKey(name)) {
			String[] strs = present.split("_");
			//add item to the present view.
			locations.get(strs[0]).addArticle(strs[1]+"_"+name);
			return articles.get(name).getImage();
		}
		return null;
	}
	
	/**
	 * Return a image object from an item.
	 * 
	 *@param name the name of the selected item.
	 *@return the image of the request.
	 */
	public Image getArticle(String name) {
		return articles.get(name).getImage();
	}
	
	/**
	 * Disappear the image object.
	 * 
	 *@param index the index of the selected item.
	 */
	public void disappearArticle(int index) {
		//remove the item from the present view.
		String[] strs = present.split("_");
		locations.get(strs[0]).removeArticle(strs[1],index);
	}
	
	/**
	 * Return name of items exist in the present view.
	 * 
	 *@return the array List of the items' names.
	 */
	public ArrayList<String> showArticle() {
		ArrayList<String> nameList = new ArrayList<String>();
		// get the name list of the present views
		String[] strs = present.split("_");
		nameList = locations.get(strs[0]).getArticle(strs[1]);
		return nameList;
	}
}
