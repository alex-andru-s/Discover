package controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import app.App;
import app.BackgroundScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import player.Player;
import player.PlayerMode;
import javafx.scene.control.ProgressIndicator;


public class MenuController {

@FXML
private Label welcomeLabel;
@FXML
private ProgressIndicator completedProgress;
@FXML
private ProgressIndicator pointsProgress;
@FXML
private ProgressIndicator accuracyProgress;
@FXML
private Label completedValueLabel;
@FXML
private Label pointsValueLabel;
@FXML
private Label accuracyValueLabel;
@FXML
private Label playerNameLabel;
@FXML
private Label levelLabel;
@FXML
private Label labelLearned;
@FXML
private Label labelPoints;
@FXML
private Label labelAccuracy;
@FXML
private StackPane contentArea;	    
@FXML
private Button learnButton;	
@FXML
private Button createButton;
@FXML
private Button manageButton;
@FXML
private StackPane root;
@FXML
private Button playButton;
@FXML
private Button languageButton;
@FXML
private Button collectionButton;
@FXML
private Button statisticsButton;
@FXML
private Button settingsButton;
@FXML
private Button logoutButton;

private BackgroundScene videoBackground;
private Player player;

private int maxPoints;
private  int cardsTotalSum;
private ResourceBundle bundle;

@FXML
public void initialize() {
	
	bundle = ResourceBundle.getBundle(
	    	 "resources.lang.messages",
	    	  Locale.of(App.getMainLanuage().getCode()));
	
   if(!App.getCurrentPlayer().getName()
		               .equals(PlayerMode.ADMIN.name())) {
	   
	   activateAdminMode();
   }
   
   
   videoBackground = new BackgroundScene();
   root.getChildren().add(0, videoBackground);
   videoBackground.toBack();
   player = App.getCurrentPlayer();
   setText();
   loadPlayerData();
   initializeDashboard();

   }

private void loadPlayerData() {
   
   playerNameLabel.setText(this.player.getName());

   int level = calculatePlayerLevel();
   
   player.getStatistics().addLevel(level);

   levelLabel.setText(bundle.getString("label.level") + " " + level);

   if (welcomeLabel != null) {
	welcomeLabel.setText(bundle.getString("login.success") 
			         + " " + this.player.getName() + "!");
	}
  }

private int calculatePlayerLevel() {
	
  int playerPoints = this.player.getStatistics().getPoints();

  cardsTotalSum =  App.getCardService().getAllCards().size();
  
  maxPoints = cardsTotalSum * 5;
  
  int maxLevel = Math.max(100, cardsTotalSum / 2);

  if (maxPoints <= 0) {
      return 1;
  }
  
  int level = 1 + (playerPoints * (maxLevel - 1) / maxPoints);

  return Math.min(level, maxLevel);
}

private void initializeDashboard() {
	    
    player.getStatistics().addWrong(
    			player.getProgress().values().stream()
    			.mapToInt(card->card.getWrongAnswers()).sum());
    	
    player.getStatistics().addCorrect(
    			player.getProgress().values().stream()
    			.mapToInt(card->card.getCorrectAnswers()).sum());
    
    double learned = player.getProgress().values().stream()
    .filter(card->card.isLearned() == true ).count();
   
    
    double points = player.getStatistics().getPoints();
    double accuracy = player.getStatistics().getCardsAccuracy();

    
    updateProgress(
            completedProgress,
            completedValueLabel,
            learned/cardsTotalSum,
            String.valueOf((int)learned));
           
    
   updateProgress(
        pointsProgress,
        pointsValueLabel,
        points/(double)this.maxPoints,
        String.valueOf((int)points));
      

    updateProgress(
        accuracyProgress,
        accuracyValueLabel,
         accuracy,
         String.valueOf((int)(accuracy*100) + "%" ));
     
}

private void updateProgress(ProgressIndicator indicator,Label valueLabel,
                                         double value, String displayValue) {  
	indicator.setProgress(value);
	valueLabel.setText(displayValue);
}

@FXML
private void openPlay() {
    App.getSceneManager().showGame();
}

@FXML
private void openLearn() {
    App.getSceneManager().showLearn();
}

@FXML
private void openLearnLanguages() {
	this.contentArea.getChildren().clear();
	this.contentArea.getChildren().addAll(
			openFXML("LaunchLanguageGame.fxml", "launchLanguageGame.css"));
}

@FXML
private void openAddCard() {
	  
	this.contentArea.getChildren().clear();
	this.contentArea.getChildren().addAll(openFXML("AddCard.fxml", "addCard.css"));
}
@FXML
private void openRemoveEditCard() {
	
	this.contentArea.getChildren().clear();
	this.contentArea.getChildren().addAll(openFXML("editRemoveCard.fxml", "editRemove.css"));
}

@FXML
private void openCollection() {
	
	this.contentArea.getChildren().clear();
	this.contentArea.getChildren().addAll(openFXML("CardCollection.fxml", "CardCollection.css"));
}

@FXML
private void openStatistics() {
	
	this.contentArea.getChildren().clear();
	this.contentArea.getChildren().addAll(openFXML("Statistics.fxml", "statistics.css"));	
}

@FXML
private void openSettings() {
	
	this.contentArea.getChildren().clear();
	this.contentArea.getChildren().addAll(openFXML("Settings.fxml", "settings.css"));
}

private Parent openFXML(String fxml, String css){
	
	Parent root = null;
	try {
	      FXMLLoader loader = new FXMLLoader
	                 (getClass().getResource("/fxml/"+ fxml));
           
          root = loader.load();
           
         if (css != null) {
        	      
           root.getStylesheets().add(getClass()
                .getResource("/resources/css/" + css)
                 .toExternalForm());
           }
              
	}catch (IOException | NullPointerException e) {	
		e.printStackTrace();
		
	}
   return root;
}

@FXML
private void logout() {
    App.setCurrentPlayer(null);
    App.setPlayerMode(PlayerMode.GUEST);
    App.getSceneManager().showLogin();
}

private void activateAdminMode() {
	
	createButton.setDisable(true);
	createButton.setVisible(false);
	createButton.setManaged(false);
	manageButton.setDisable(true);
	manageButton.setVisible(false);
	manageButton.setManaged(false);
    
}
private void setText() {
	learnButton.setText(bundle.getString("btn.learn"));
	playButton.setText(bundle.getString("btn.play_game"));
	languageButton.setText(bundle.getString("btn.learn_language"));
	collectionButton.setText(bundle.getString("btn.collection"));
	statisticsButton.setText(bundle.getString("btn.statistics"));
	createButton.setText(bundle.getString("btn.create_card"));
	manageButton.setText(bundle.getString("btn.manage_card"));
	settingsButton.setText(bundle.getString("btn.settings"));
	logoutButton.setText(bundle.getString("btn.logout"));
	labelLearned.setText(bundle.getString("label.statisstics_learned"));
	labelPoints.setText(bundle.getString("label.points"));
	labelAccuracy.setText(bundle.getString("label.collection_accuracy"));
}
	}