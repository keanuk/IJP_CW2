import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Location for use in the Location Viewer application. 
 * A location contains up to 4 images, name, and the items put in each image.
 *
 * @author  Danyang Yu;
 * @version 2.0
 */
public class Location {
	private String name;
	
	//key is the number: 0 represents the north, 90 represents the east, 180 represent south, 270 represents west.
	private HashMap<Integer, Image> directions = new HashMap<Integer, Image>();
	
	//key is the number representation of the direction
	//the value is the the array list used to store the name of items put in the direction image view
	private HashMap<String, ArrayList<String>> contain = new HashMap<String, ArrayList<String>>(); 
	
	/**
	 * Create a location with a name.
	 */
	public Location(String name) {
		this.name = name;
	}
	
	/**
	 * Set the image of the specific direction of the location from local file.
	 *
	 * @param direction the number representation of the direction
	 */
	public void setDirection(String direction) {		
		//load the image by using the file path, and set size of it.
		String name = this.name+"_"+direction+".JPG";
		Image image = new Image(name,512,384,false,false); 
		int index = Integer.parseInt(direction); // get the number to represent the direction
		this.directions.put(index, image);
		
	}

	/**
	 * Get the image of the specific direction.
	 *
	 * @param direction the number representation of the direction.
	 * @return the image of the direction
	 */
	public Image getDirection(int direction) {
		return this.directions.get(direction);
	}
	
	/**
	 * Get the name of the location.
	 *
	 * @return the name of the location
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Add the item to the specific direction.
	 *
	 * @param name the combination of the number representation of the direction and the name of the item.
	 */
	public void addArticle(String name) {
		String[] strs = name.split("_"); // split the number and the name of the item
		if(contain.containsKey(strs[0])) { // whether the direction have any items in it
			contain.get(strs[0]).add(strs[1]);		
		}else {
			ArrayList<String> nameList = new ArrayList<String>();
			nameList.add(strs[1]);
			contain.put(strs[0],nameList);
		}
	}
	
	/**
	 * Remove the item from the specific direction.
	 *
	 * @param name the number representation of the direction.
	 * @param index the index of the item.
	 */
	public void removeArticle(String name, int index) {
		contain.get(name).remove(index);
	}
	
	/**
	 * Get the list of name of items in the specific direction.
	 *
	 * @param name the number representation of the direction.
	 * @return the array list of the names of items in the direction.
	 */
	public ArrayList<String> getArticle(String name) {
		
		//check whether the direction contain items. 
		if(contain.containsKey(name)) {
			return contain.get(name);		
		}else {
			return null;
		}
	}
	

}
