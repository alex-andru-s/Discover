package app;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.IOException;

public class SceneManager {
	
private final Stage stage;

Rectangle2D visualBounds;

public SceneManager(Stage stage) {
	
	this.visualBounds = Screen.getPrimary().getVisualBounds();
	this.stage = stage;
	this.stage.setX(visualBounds.getMinX());
	this.stage.setY(visualBounds.getMinY());
	this.stage.setWidth(visualBounds.getWidth());
	this.stage.setHeight(visualBounds.getHeight());
 }
	    
public void showLogin() {
     show("Login.fxml" , "login.css" );
}

public void showMenu() {
	 show("Menu.fxml", "menu.css" );
}

public void showLearn() {
	show("Learn.fxml", "learn.css");
}

public void showLearnLanguage() {
	show("LanguageGame.fxml", "languageGame.css");
}

public void showGame() {
	show("Game.fxml", "game.css");
}

private void show(String fxml, String css) {
	 try {
		 FXMLLoader loader = new FXMLLoader
		    		    (getClass().getResource("/fxml/"+ fxml));

		 Parent root =  loader.load();
	     Scene scene = new Scene(root);
	         
	     Image icon = new Image("/resources/image/discover.png");
	     stage.getIcons().add(icon);
	         
	     stage.setTitle("Discover");
		 stage.setScene(scene);
		     
		 if(css != null) {
			 
		    scene.getStylesheets().add(getClass().getResource(
		    		 "/resources/css/" + css).toExternalForm()); 
		   }  
		 stage.show();
		     
	  }catch (IOException e) {
		 e.printStackTrace();
	    }
   }
}
