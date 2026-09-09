package controller;

import app.App;
import cards.*;
import game.GameMode;
import game.GameSession;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import player.PlayerMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import java.util.Random;
import java.util.ResourceBundle;


public class GameController {

@FXML 
private StackPane root;
@FXML 
private Label choseAnswer;
@FXML 
private Label modeLabel;
@FXML
private Button submitBtn;
@FXML
private Button nextButton;
@FXML 
private Label messageLabel;
@FXML 
private HBox advanceMode;
@FXML 
private VBox normalMode;
@FXML
private Label questionLabel;
@FXML
private Label labelQuestion;
@FXML 
private Label scoreLabel;
@FXML 
private Label labelTitle;
@FXML
private TextField answerField;
@FXML
private Button answer1;
@FXML
private Button answer2;
@FXML
private Button answer3;	    
@FXML
private Button modeButton;
@FXML
private Button returnToMenu;
@FXML
private ImageView cardImage;

private GameSession session;
private ResourceBundle bundle;
private GameMode gameMode;
private Random random;

public void initialize() {
	 bundle = ResourceBundle.getBundle(
	    	 "resources.lang.messages",
	    	  Locale.of(App.getMainLanuage().getCode()));
	 
	if(App.getCardService().getAllCards().size() == 0) {
		new Alert(AlertType.INFORMATION, bundle.getString("jo.game_message"))
		                              .showAndWait();
	      throw new IllegalArgumentException(
	        		"No existing cards to start the game");
	}
	
    if(App.getPlayerMode().equals(PlayerMode.REGISTERED))
	  this.returnToMenu.setText(bundle.getString("btn.return_menu"));
    else this.returnToMenu.setText(bundle.getString("btn.return_close"));
    
    modeButton.setText(bundle.getString("btn.advanced"));
   
    this.random = new Random();
    setButtonsText();
    start(GameMode.NORMAL); 
}

@FXML
private void gameModeBtn() { 
	if(gameMode.equals(GameMode.NORMAL))
      start(GameMode.ADVANCED);  
	else
     start(GameMode.NORMAL);
}

private void start(GameMode mode) {

	nextButton.setDisable(false);

	if(mode.equals(GameMode.NORMAL)) {
		  advanceMode.setDisable(true);
		  advanceMode.setVisible(false);
		  normalMode.setDisable(false);
		  normalMode.setVisible(true);
		  modeButton.setText(bundle.getString("btn.advanced"));	
	}
	else{
	     advanceMode.setDisable(false);
		 advanceMode.setVisible(true);
		 normalMode.setDisable(true);
		 normalMode.setVisible(false);
		 advanceMode.setManaged(true);
		 normalMode.setManaged(false);
		 answerField.setDisable(false);
		 answerField.clear();
		 submitBtn.setDisable(false); 
		 modeButton.setText(bundle.getString("btn.normal"));
	}
	
	if(session == null)
	session = new GameSession(
			         App.getGameService()
			         .selectCards(App.getCardService()
			         .getAllCards(), mode)
			         ,mode); 
	
	else { session.setCards(App.getGameService().selectCards(
			App.getCardService().getAllCards(), mode));
	
	       session.setGameMode(mode);
	}
             
	if(session.getCards().isEmpty()) {
		session.setCards(
				App.getCardService().getAllCards());
	}
		
	this.gameMode = mode;
	nextButton.setDisable(true);
	showCurrentCard();	
}  

private void showCurrentCard() {
	
  Card card = session.getCurrentCard(); 
  
  cardImage.setImage(new Image(new File(card
		   .getCardData()
		   .getImagePath())
		   .toURI().toString()));
  
  questionLabel.setText(bundle.getString("question.what")  
		           + " " + bundle.getString(card.getCategory()
		            		         .getQuestionCategory()) 
		           + " " + bundle.getString("question.is") );
	    
  scoreLabel.setText(bundle.getString("label.score") 
		               + " "
	                   + session.getScore());
  createAnswers();	    
}

private void createAnswers() {

   if(App.getCardService().getAllCards().size() < 3) {
	   answer1.setVisible(false);
	   answer3.setVisible(false);
	   answer1.setDisable(true);
	   answer3.setDisable(true);
	   answer2.setText(session.getCurrentCard()
			   .getCardData()
			   .getTranslation(App.getMainLanuage())
			   .getName().toUpperCase());
   
	}else{  
	   answer1.setVisible(true);
       answer3.setVisible(true); 
       answer1.setDisable(false);
       answer3.setDisable(false);
       answer2.setVisible(true);
       answer2.setDisable(false);

     String correctAnswer = session.getCurrentCard()
    		    .getCardData()
                .getTranslation(App.getMainLanuage())
                .getName().toUpperCase();

    List<Card> wrongCards = new ArrayList<>(App.getCardService()
		                        .getAllCards().stream().toList());
    
    wrongCards.remove(session.getCurrentCard());

    Collections.shuffle(wrongCards, random);

    Card wrongCard1 = wrongCards.get(0);
    Card wrongCard2 = wrongCards.get(1);

    List<String> answers = new ArrayList<>();

       answers.add(correctAnswer);

       answers.add(wrongCard1
    		       .getCardData()
                   .getTranslation(App.getMainLanuage())
                   .getName().toUpperCase());

       answers.add(wrongCard2
    		        .getCardData()
                    .getTranslation(App.getMainLanuage())
                    .getName().toUpperCase());

       Collections.shuffle(answers);

       answer1.setText(answers.get(0));
       answer2.setText(answers.get(1));
       answer3.setText(answers.get(2));
    }
 }

private void disableButtons() {
	answer1.setDisable(true);
	answer2.setDisable(true);
	answer3.setDisable(true);	
}
@FXML
private void answerButton1() {
    checkAnswer(answer1.getText());  
}

@FXML
private void answerButton2() {
   checkAnswer(answer2.getText());
}

@FXML
private void answerButton3() {
   checkAnswer(answer3.getText());
}

@FXML
private void submitAdvanced() {
    checkAnswer(answerField.getText());
    answerField.setDisable(true);
    submitBtn.setDisable(true);     
}

private void checkAnswer(String answer) {
	
	if(!answer.isEmpty() && !answer.isBlank()) 
		nextButton.setDisable(false);
	
    Card card = session.getCurrentCard();
    
	boolean correct = App.getGameService()
	                  .checkAnswer(card, answer);
	                                
    App.getGameService().registerAnswer(correct, session);
    
    session.setAnswered(card);
    
    if(correct) {
 	   scoreLabel.setText(bundle.getString("label.correct_score") + " "  
 	                      + session.getScore());
     }
 	else{     	  
 		  scoreLabel.setText(bundle.getString("label.wrong_score") + " "
 	                         + session.getScore());
       }
    
    disableButtons();
}


@FXML
private void nextQuestion() {
	
	if(this.gameMode.equals(GameMode.ADVANCED)) {
		answerField.setDisable(false);
		answerField.clear();
	    submitBtn.setDisable(false); 
	 }
	
	if(!session.isFinished()) {
		
	  session.nextCard();
	  nextButton.setDisable(true);
      showCurrentCard(); 
     }
	else finishGame(); 
		
}

private void finishGame() {
   
   if(session.isFinished() && !App.getGameService().selectCards(
		        App.getCardService().getAllCards(),gameMode).isEmpty()) {
	   
	 session.setCards(App.getGameService().selectCards(
				      App.getCardService().getAllCards(), gameMode));
	 
	 showCurrentCard();	
   }
			
   if(session.isFinished() && App.getGameService().selectCards(
			App.getCardService().getAllCards(), gameMode).isEmpty()){
	
	   labelQuestion.setVisible(false);
	   questionLabel.setText(bundle.getString("end.game_message"));
	   nextButton.setDisable(true); 
	   
	   if(this.gameMode.equals(GameMode.NORMAL)) {  
	   normalMode.setVisible(false);
	   normalMode.setDisable(true);	  	  
	   }
	   else {
		   answerField.setDisable(true);
		   submitBtn.setDisable(true);
	   }
   } 
}

@FXML
private void returnToMenu() {
	
	if(App.getPlayerMode().equals(PlayerMode.REGISTERED)) {
		  
	      App.getPlayerService()
	     .updatePlayerData(App.getCurrentPlayer());
	      App.getSceneManager().showMenu();
	 }	
	else App.getSceneManager().showLogin();        
  }

private void setButtonsText() {
	choseAnswer.setText(bundle.getString("label.chose"));
	labelQuestion.setText(bundle.getString("label.question"));
	nextButton.setText(bundle.getString("btn.next"));
	returnToMenu.setText(bundle.getString("btn.return_menu"));
	submitBtn.setText(bundle.getString("btn.submit"));
	answerField.setPromptText(bundle.getString("answer.prompt"));
	labelTitle.setText(bundle.getString("label.game_title"));
	modeLabel.setText(bundle.getString("label.mode"));
	
}
	    
}
