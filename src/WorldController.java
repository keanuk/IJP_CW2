
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

/**
 * The controller for Location Viewer application.
 *
 * @author  Danyang Yu;
 * @version 2.0
 */

public class WorldController {
	
	private World world;

	@FXML
	private ImageView locationView; // used to show the picture of the view.

	@FXML
	private ArrayList<ImageView> articleView = new ArrayList<ImageView>(); // used to show the items put in the present view
	
	@FXML
	private AnchorPane page;
	
	@FXML
	private Label tip; 

	/**
	 * Initialize the controller.
	 */
	public void Initialise() {
		world = new World();
        locationView.setImage(world.getDirection("default")); //The image is default image.
		//locationView.setImage(new Image("./locations/flat_0.jpg")); //The image is default image.
        
	}
	
	/**
	 * Handle the turn left action from the interface.
	 *
	 * @param event the event from the interface
	 */
	public void turnLeft(ActionEvent event) {
		clearItems();
		locationView.setImage(world.getDirection("left"));
		tip.setText("");
		showItems();
    }
    
	/**
	 * Handle the turn right action from the interface.
	 *
	 * @param event the event from the interface
	 */
	public void turnRight(ActionEvent event) {
		clearItems();
		locationView.setImage(world.getDirection("right"));
		tip.setText("");
		showItems();
    }
    
	/**
	 * Handle the move forward action from the interface.
	 *
	 * @param event the event from the interface
	 */
    public void goFoward(ActionEvent event) {
    	clearItems();
    	Image forward = world.forwardLocation();
    	
    	// when the go forward is unavailable, the image will not change.
    	if(forward.equals(locationView.getImage())) {
    		tip.setText("Go forward is unavailable!"); // show tips on the interface.
    		tip.setTextFill(Color.RED);
    	}
    	
    	locationView.setImage(forward);
		showItems();
    }
    
    /**
	 * Handle the back to origin location action from the interface.
	 *
	 * @param event the event from the interface
	 */
    public void backToOrigin(ActionEvent event) {
    	clearItems();
    	locationView.setImage(world.getDirection("default"));//The image is default image.  
    	tip.setText("");
		showItems();
	}
   
    /**
	 * Handle the put down item action from the interface.
	 *
	 * @param event the event from the interface
	 */
    public void putDownItem(ActionEvent event) {
    	MenuItem article = (MenuItem)event.getSource(); // the selected menu item.
    	
    	ImageView itemView = new ImageView();
    	itemView.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override 
        	public void handle(MouseEvent m) { // act when ImageView is clicked by the user.
        		
        		pickUpItem(itemView); 
        		
        	}
             });   
    	
        itemView.setImage(world.setArticle(article.getId())); // show the image of the selected item.
        itemView.setId(article.getId());
        itemView.setLayoutX(44+articleView.size()*70.0);  //set location of the item.
	    itemView.setLayoutY(350);
       page.getChildren().add(itemView);
        articleView.add(itemView);
    }
    
    /**
	 * Remove the specific item from the interface.
	 *
	 * @param itemView the itemView clicked by user.
	 */
    public void pickUpItem(ImageView itemView) {
        page.getChildren().remove(itemView);  //remove the itemView from the interface.
        world.disappearArticle(articleView.indexOf(itemView)); //remove the item record from the location
        articleView.remove(itemView); //remove the itemView from the record.
        
     }
    
    /**
	 * Load the items exists in the present view.
	 * Create ImageViews for them.
	 * Show the ImageView on the interface.
	 *
	 */
    public void showItems() {
    	
    	 ArrayList<String> nameList = world.showArticle(); // get the name of the items in the present view
    	 
    	 if(nameList!=null) {
    		 for(int i=0;i<nameList.size();i++) {
    			 
    			 //create ImageView to each of the items
        		 ImageView itemView = new ImageView();
        		 itemView.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	        	@Override 
        	        	public void handle(MouseEvent m) {  
        	        		pickUpItem(itemView);
        	        	}
        	             });   	 
        	     itemView.setImage(world.getArticle(nameList.get(i)));
        	     itemView.setId(nameList.get(i));
        	     itemView.setLayoutX(44+articleView.size()*70.0);
        		 itemView.setLayoutY(350);
        		 //add each ImageView to the interface
        	     page.getChildren().add(itemView);
        	     //add each ImageView to the array list as record
        	     articleView.add(itemView);
        	 }
    	 }
    	 
    	
    }
    
    /**
	 * Remove the items shown in the present view.
	 *
	 */
    public void clearItems() {
   	 for(int i=0;i<articleView.size();i++) {
   	     page.getChildren().remove(articleView.get(i));   	     
   	 }
   	 articleView.clear();
   }
    
    
}
