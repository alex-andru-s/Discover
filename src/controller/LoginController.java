package controller;

import app.App;
import app.BackgroundScene;
import cards.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import player.Player;
import player.PlayerMode;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController {

@FXML
private Label labelLoad;
@FXML
private Button register;
@FXML
private Button enter;
@FXML
private Button cancel;
@FXML
private Label labelCreate;
@FXML
private ImageView languageImage;
@FXML
private Button selectLanguage;
@FXML
private Button guest;
@FXML
private Button quit;
@FXML
private Button loadPlayer;
@FXML
private Button createPlayer;
@FXML
private TextField textField;
@FXML
private Label labelMessage;
@FXML
private VBox vBox;
@FXML
private StackPane root;

private BackgroundScene videoBackground;
private ResourceBundle bundle;


@FXML
public void initialize() {
    if(this.root != null) {
      videoBackground = new BackgroundScene();
      root.getChildren().add(0, videoBackground); 
     }
    nextLanguage(App.getMainLanuage().getCode());  
}

@FXML
private void languageSelect() {
	
   Map<Integer,Language> languages = new HashMap<>(); 
   for(Language language : Language.values()) {
	    languages.put(language.ordinal(), language); 
	}
   
      if(languages.containsKey(App.getMainLanuage().ordinal() + 1)) {
    	 App.setMainLanuage(languages.get(App.getMainLanuage().ordinal() + 1));
         nextLanguage(App.getMainLanuage().getCode());
       }
      else 
    	  App.setMainLanuage(languages.get(0));
          nextLanguage(App.getMainLanuage().getCode());
 }
  
private void  nextLanguage(String language){
    if(languageImage != null)
	languageImage.setImage(new Image(getClass()
	             .getResourceAsStream("/resources/image/" + language +"-icon.png")));
  
     bundle = ResourceBundle.getBundle("resources.lang.messages", 
                                                      Locale.of(language));
    if(guest != null) {
      guest.setText(bundle.getString("btn.guest"));
      loadPlayer.setText(bundle.getString("btn.load"));
      createPlayer.setText(bundle.getString("btn.create"));
      quit.setText(bundle.getString("btn.quit"));
    }
    
    if(labelCreate != null)
    	labelCreate.setText(bundle.getString("label.create"));

    if(textField != null) 
        textField.setPromptText(bundle.getString("tf.name"));
        
    if(cancel != null)
        cancel.setText(bundle.getString("btn.cancel")); 
     

    if(register != null)
        register.setText(bundle.getString("btn.register"));
    
    if(enter != null)
        enter.setText(bundle.getString("btn.enter"));
    
    if(labelLoad != null)
    	labelLoad.setText(bundle.getString("label.load"));
   
}

@FXML
private void loginGuest(ActionEvent e){
	App.setPlayerMode(PlayerMode.GUEST);
	App.getSceneManager().showGame();
}

@FXML
private void loadPlayer(ActionEvent e) {
    openWindow("/fxml/LoadPlayer.fxml", 
    		   "Load Player",
    		   "loadPlayer.css"); 
}

@FXML
private void createPlayer(ActionEvent e) {
   openWindow("/fxml/CreatePlayer.fxml", 
		      "Create Player", 
		      "createPlayer.css");
  
}

private void openWindow(String fxml, String title, String css) {

   try {
	    FXMLLoader loader = new FXMLLoader
	         (getClass().getResource(fxml));

	    Parent root = loader.load();
	    Stage stage = new Stage();
	    Scene scene = new Scene(root);
	    Image icon = new Image("/resources/image/discover.png");
	    
	    stage.setScene(scene);
	    scene.getStylesheets().add(getClass().getResource(
	   	         "/resources/css/" + css).toExternalForm());
	    
	    stage.getIcons().add(icon);  
	    stage.setTitle(title);    
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.setResizable(false);
	    stage.showAndWait();
	    
	 }catch (Exception e) {
	   e.printStackTrace();
	}  
}

@FXML
private void login(ActionEvent e) { 
	
  String name = textField.getText().trim();

  if(name.isEmpty() || name.isBlank()) {
    textField.requestFocus();
    labelMessage.setText(bundle.getString("login.prompt"));
      return;
  }

  Player player = App.getPlayerService().loadPlayer(name);
    
  if(player != null) {
    App.setCurrentPlayer(player);
    App.setPlayerMode(PlayerMode.REGISTERED);
    
    closeWindow();
    App.getSceneManager().showMenu();

  }else {
	  
        labelMessage.setText(bundle.getString("label.player_not_found"));
    }
}

@FXML
private void create() {
	
   String name = textField.getText().trim();
   if(name.isEmpty() || name.isBlank() ) {
	   labelMessage.setText(bundle.getString("login.prompt"));
	   textField.requestFocus(); 
	    return;
    }

   if(App.getPlayerService().playerExists(name)) {
	labelMessage.setText(bundle.getString("label.player_exists"));
	textField.clear();
	textField.requestFocus();
   }

   Player player = App.getPlayerService().createPlayer(name);
 
   if(player != null) {
     App.setCurrentPlayer(player);
     App.setPlayerMode(PlayerMode.REGISTERED);
     closeWindow();
     App.getSceneManager().showMenu();
   } 
}

private void closeWindow() {
    Stage stage = (Stage) textField.getScene().getWindow();
    stage.close();
 }

@FXML
private void back(ActionEvent e) {
    closeWindow();
}

@FXML
private void closeGame() {
	 Stage stage = (Stage) root.getScene().getWindow();
	 stage.close();
  }
}