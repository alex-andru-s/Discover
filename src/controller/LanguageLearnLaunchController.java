package controller;

import java.util.Locale;
import java.util.ResourceBundle;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class LanguageLearnLaunchController {

@FXML
private Button startButton;	
@FXML
private Label labelTitle;
@FXML
private Label labelSubTitile;
@FXML
private Label labelHowTo;
@FXML
private Label labelRule1;
@FXML
private Label labelRule2;
@FXML
private Label labelRule3;
@FXML
private Label labelRule4;
@FXML
private Label labelRule5;


private ResourceBundle bundle;
	
@FXML
public void initialize() {
	bundle = ResourceBundle.getBundle(
		    "resources.lang.messages",
		    Locale.of(App.getMainLanuage().getCode()));
	startButton.setText(bundle.getString("btn.start_game"));
	labelTitle.setText(bundle.getString("label.language_title"));
	labelSubTitile.setText(bundle.getString("label.language_secondary_title"));
	labelHowTo.setText(bundle.getString("label.how_to_play"));
	labelRule1.setText(bundle.getString("label.rule1"));
	labelRule2.setText(bundle.getString("label.rule2"));
	labelRule3.setText(bundle.getString("label.rule3"));
	labelRule4.setText(bundle.getString("label.rule4"));
	labelRule5.setText(bundle.getString("label.rule5"));

}

@FXML
private void startGame() {
	App.getSceneManager().showLearnLanguage();
}

}
