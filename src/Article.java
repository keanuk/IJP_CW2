
import javafx.scene.image.Image;

/**
 * A item for use in the Location Viewer application. 
 * A item contains an images, and name.
 *
 * @author  Danyang Yu;
 * @version 2.0
 */
public class Article {
	private String name;
	private Image image;
	
	/**
	 * Create a location with the name and image.
	 */
	public Article(String name, Image image) {
		this.setName(name);
		this.image = image;
		
	}

	/**
	 * Get the image of the item.
	 *
	 * @return image of the item
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Set the image of the item from a image.
	 *
	 * @param iamge the image.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Get the name of the item.
	 *
	 * @return name of the item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the item from a image.
	 *
	 * @param name the name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
	

}
	
