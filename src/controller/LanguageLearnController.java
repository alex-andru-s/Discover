package controller;

import app.App;
import cards.Card;
import cards.Language;
import cards.Translation;
import game.GameMode;
import game.GameSession;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class LanguageLearnController {

@FXML
private ImageView cardImage;
@FXML
private Label mainNameLabel;
@FXML
private Label labelTitle;
@FXML
private Label secondaryNameLabel;
@FXML
private TextField answerField;
@FXML
private Button returnToMenu;
@FXML
private Button confirmButton;
@FXML
private Button nextButton;
@FXML
private Label resultLabel;
@FXML
private Label scoreLabel;

private Language mainLanguage;
private Language secondaryLanguage;

private PauseTransition timer;
private ResourceBundle bundle;
private GameSession session;

@FXML
public void initialize() {
	
	bundle = ResourceBundle.getBundle(
		    "resources.lang.messages",
		    Locale.of(App.getMainLanuage().getCode()));
	
	if(App.getCardService().getAllCards().size() == 0) {
		new Alert(AlertType.INFORMATION, 
				bundle.getString("jo.game_message")).showAndWait();
		
	      throw new IllegalArgumentException(
	        		"No existing cards to learn");
	}
	
	session = new GameSession(
	         App.getGameService()
	         .selectCards(App.getCardService()
	         .getAllCards(),GameMode.LEARN)
	         ,GameMode.LEARN);
	
	if(session.getCards().isEmpty())
		session.setCards(App.getCardService().getAllCards());
	
   mainLanguage = App.getMainLanuage();
   secondaryLanguage = (mainLanguage == Language.ENGLISH) ?
			                    Language.ITALIAN : Language.ENGLISH;
	answerField.setVisible(false);
	answerField.setManaged(false);

	confirmButton.setVisible(false);
	confirmButton.setManaged(false);

    nextButton.setVisible(false);
	nextButton.setManaged(false);

	resultLabel.setText("");
	
	setText();
	showCard();
}

private void showCard() {
	
	Card card = session.getCurrentCard();

	Translation mainTranslation = card.getCardData()
	                        .getTranslation(mainLanguage);

	Translation secondaryTranslation = card.getCardData()
	                        .getTranslation(secondaryLanguage);

	cardImage.setImage(new Image(new File(card
			   .getCardData()
			   .getImagePath())
			   .toURI().toString()));

	mainNameLabel.setText(mainTranslation.getName().toUpperCase());
	secondaryNameLabel.setText(secondaryTranslation.getName().toUpperCase());

	secondaryNameLabel.setVisible(true);
	secondaryNameLabel.setManaged(true);

	answerField.clear();
    answerField.setVisible(false);
	answerField.setManaged(false);

	confirmButton.setVisible(false);
	confirmButton.setManaged(false);

	nextButton.setVisible(false);
	nextButton.setManaged(false);

	resultLabel.setText("");
	
	scoreLabel.setText(bundle.getString("label.score") + " "
	                                     + session.getScore());

	if(timer != null) {
	     timer.stop();
	}

	 timer = new PauseTransition(Duration.seconds(5));

	 timer.setOnFinished(event -> hideTranslation());
	 timer.play();
}

private void hideTranslation() {

	secondaryNameLabel.setVisible(false);
	secondaryNameLabel.setManaged(false);

    answerField.setVisible(true);
	answerField.setManaged(true);

	confirmButton.setVisible(true);
	confirmButton.setManaged(true);
	
	answerField.setDisable(false);
    confirmButton.setDisable(false);

	answerField.requestFocus();
}

@FXML
private void confirmAnswer() {

	Card card = session.getCurrentCard();
	
	String answer = answerField.getText().trim();

	if(answer.isBlank()) {  
	   resultLabel.setText(bundle.getString("label.insert_answer"));   
	   answerField.requestFocus();	  
	   return;
	} 
   
   boolean isCorrect = App.getGameService().checkAnswer(
		      card, answer, secondaryLanguage);
   
   session.setAnswered(card);
   
   App.getGameService().registerAnswer(isCorrect, session);
   
   if(isCorrect){  
	   resultLabel.setText(bundle.getString("label.correct"));
	   
  }else {
         resultLabel.setText(bundle.getString("label.wrong"));
         answerField.setText(card.getCardData()
        		 .getTranslation(secondaryLanguage)
        		 .getName().toUpperCase());   
	     }
   
  scoreLabel.setText(bundle.getString("label.score") + " " 
                                 + session.getScore());
  
  answerField.setDisable(true);
  confirmButton.setDisable(true);

  nextButton.setVisible(true);
  nextButton.setManaged(true);
  
}

@FXML
private void nextCard(){
	
   if(!session.isFinished()) {		
	   if(timer != null){
	    timer.stop();
        }    
	answerField.setDisable(false);
    confirmButton.setDisable(false);
    session.nextCard();
    showCard();   
   } else finishGame();  	    
}

private void finishGame() {
	List<Card> list =  App.getGameService().selectCards(
	        App.getCardService().getAllCards(), GameMode.LEARN);
	
	if(session.isFinished() && !list.isEmpty()) {  
             session.setCards(list); 
                showCard();   
     
	}else {
	resultLabel.setText(bundle.getString("label.game_complete"));
	
	scoreLabel.setText(bundle.getString("label.score") + " " 
	                                       + session.getScore());

	answerField.setVisible(false);
	answerField.setManaged(false);

	confirmButton.setVisible(false);
	confirmButton.setManaged(false);

    nextButton.setVisible(false);
	nextButton.setManaged(false);	
	}
}

@FXML
private void returnToMenu() {
	if(timer != null) {
	    timer.stop();
    }
	 App.getPlayerService()
         .updatePlayerData(App.getCurrentPlayer());
    App.getSceneManager().showMenu();
 }

private void setText() {
	nextButton.setText(bundle.getString("btn.next"));
	confirmButton.setText(bundle.getString("btn.submit"));
	returnToMenu.setText(bundle.getString("btn.return_menu"));
	answerField.setPromptText(bundle.getString("answer.prompt"));
	labelTitle.setText(bundle.getString("label.language_title"));
	
}
	
}

