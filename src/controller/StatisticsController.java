package controller;

import java.util.Locale;
import java.util.ResourceBundle;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import player.Player;
import player.PlayerCardProgress;

public class StatisticsController {
	
@FXML
private Label studyProgressLabel;
@FXML
private Label labelTitle;
@FXML
private Label labelStudyProgress;
@FXML
private Label labelLearnProgress;
@FXML
private Label labelWrong;
@FXML
private Label labelStudy;
@FXML
private Label labelCards;
@FXML
private Label labelAccuracy;
@FXML
private Label labelCorrect;
@FXML
private Label labelLearned;
@FXML
private Label labelAttempts;
@FXML
private Label labelLevelCurrent;
@FXML
private ProgressBar	studyProgress;
@FXML
private Label learningProgressLabel;
@FXML
private ProgressBar	accuracyProgress;
@FXML
private ProgressBar learningProgress;
@FXML
private Label attemptsLabel;
@FXML
private Label playerLabel;
@FXML
private StackPane root;
@FXML
private Label  cardsLearnedLabel;
@FXML
private ProgressIndicator accuracyIndicator;
@FXML
private Label pointsLabel;
@FXML
private Label levelLabel;
@FXML
private Label correctLabel;
@FXML
private Label wrongLabel;
@FXML
private Label accuracyLabel;

private ResourceBundle bundle;

@FXML
public void initialize() {
	bundle = ResourceBundle.getBundle(
	    	 "resources.lang.messages",
	    	  Locale.of(App.getMainLanuage().getCode()));
    setText();
	
    Player player = App.getCurrentPlayer();

    if(player != null) {
	
	 playerLabel.setText(bundle.getString("label.statistics_player") 
			 + " " + player.getName());
 
	 levelLabel.setText(String.valueOf(
			       bundle.getString("label.level") + " " 
	                           + player.getStatistics().getLevel()));

	 correctLabel.setText(
	          String.valueOf(player.getStatistics()
	                        .getCorrect()));

	wrongLabel.setText(
			String.valueOf(player.getStatistics()
	                        .getWrong()));

	double accuracy = player.getStatistics().getCardsAccuracy();
	
	accuracyLabel.setText(String.valueOf((int)(accuracy*100) + "%" ));
	
	accuracyIndicator.setProgress(player.getStatistics()
			                          .getCardsAccuracy());
	
	double learned = player.getProgress().values().stream()
		    .filter(card->card.isLearned() == true ).count();
	
	cardsLearnedLabel.setText(String.valueOf((int)learned));
	
	attemptsLabel.setText(
			String.valueOf(player.getStatistics()
					         .getAnswered()));
	
	learningProgressLabel.setText(
			String.valueOf(
	    (int)(learned/App.getCardService()
	    		.getAllCards().size()*100) + "%" ));
	
	learningProgress.setProgress(
			learned/App.getCardService().getAllCards().size());

	double viewed = player.getProgress().values().stream()
		    .filter(card->card.isViewed() == true ).count();
	
	studyProgress.setProgress(
			viewed/App.getCardService().getAllCards().size());
	
	studyProgressLabel.setText(
			String.valueOf(
			 (int)(viewed/App.getCardService()
					 .getAllCards().size()*100) + "%"));
   }
  }
private void setText() {
	labelTitle.setText(bundle.getString("label.statistics_title"));
	labelLearned.setText(bundle.getString("label.statisstics_learned"));
	labelAttempts.setText(bundle.getString("label.statisstics_attempts"));
	labelCorrect.setText(bundle.getString("label.statisstics_correct"));
	labelWrong.setText(bundle.getString("label.statisstics_wrong"));
	labelAccuracy.setText(bundle.getString("label.collection_accuracy"));
	labelLevelCurrent.setText(bundle.getString("label.level_current"));
	labelLearnProgress.setText(bundle.getString("label.learn_progress"));
	labelStudyProgress.setText(bundle.getString("label.study_progress"));
	labelCards.setText(bundle.getString("label.progress_cards"));
	labelStudy.setText(bundle.getString("label.progress_study"));
  }
}
