package controller;

import java.util.Locale;
import java.util.ResourceBundle;

import app.App;
import cards.Language;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import player.Player;
import player.PlayerStatistics;

public class SettingsController {

@FXML
private	Label messageLabel;
@FXML
private	Label labelTitle;
@FXML
private	Label labelDelete;
@FXML
private	Label labelPlayerName;
@FXML
private	Label labelPlayerDescription;
@FXML
private TextField nameField;
@FXML
private	Label labelSecondaryTitle;
@FXML
private	Label labelPlayerProgress;
@FXML
private	Label labelProgressDescription;
@FXML
private	Label labelDeleteDescription;
@FXML
private Button btnRename;
@FXML
private Button btnReset;
@FXML
private Button btnDeletePlayer;
@FXML
private VBox boxDelete;
@FXML
private VBox boxRename;

private Player player;
private ResourceBundle bundle;

@FXML
public void initialize() {
	bundle = ResourceBundle.getBundle(
	    	 "resources.lang.messages",
	    	  Locale.of(App.getMainLanuage().getCode()));
   setText();
    player = App.getCurrentPlayer();
    
    if(player.getName().equals("ADMIN")) {
    boxDelete.setDisable(true);
    boxRename.setDisable(true); 
    }
}

@FXML
private void renamePlayer() {
   if(this.player != null) {
      String newName = nameField.getText().trim();
         
           if(!newName.isBlank()) {
        	 try {
                App.getPlayerService()
	                    .renamePlayer(player, newName);
                App.getPlayerService().updatePlayerData(player);
                nameField.clear();
                messageLabel.setText(bundle.getString(
                		"label.settings_name_success"));
            }catch(Exception e ){
            	e.printStackTrace();
                messageLabel.setText(bundle.getString(
                		"label.settings_player_exists"));
            	
        	   }
           }
    }   
 }

@FXML
private void  resetPlayerData() {
	player.getProgress().clear();
	player.setStatistics(new PlayerStatistics());
	App.getPlayerService().updatePlayerData(player);
	messageLabel.setText(bundle.getString("label.settings_reset_success"));
}

	 
private void logout() {
    App.getSceneManager().showLogin();
}

@FXML
private void deletePlayer() {

	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    alert.setTitle(bundle.getString("alert.remove_title"));
    alert.setHeaderText(bundle.getString("alert.remove") + 
    		                        " " + player.getName() + "?");
    alert.setContentText(bundle.getString("alert.inform"));

    ((Button) alert.getDialogPane().lookupButton(
    		ButtonType.OK)).setText(bundle.getString("btn.remove_card"));
    ((Button) alert.getDialogPane().lookupButton(
    		ButtonType.CANCEL)).setText(bundle.getString("btn.cancel"));
    
if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {

    try {  
    	App.getPlayerService().removePlayer(player);
       logout();

        } catch(Exception e) {

            e.printStackTrace();
            messageLabel.setText(bundle.getString("label.settings_remove_fail"));
         }
    }	
  }
private void setText() {
	labelTitle.setText(bundle.getString("label.settings_title"));
	labelSecondaryTitle.setText(bundle.getString("label.settings_scnd_title"));
	labelPlayerName.setText(bundle.getString("label.settings_player_name"));
	labelPlayerDescription.setText(bundle.getString("label.settings_player_description"));
	nameField.setPromptText(bundle.getString("label.settings_player_prompt"));
	btnRename.setText(bundle.getString("btn.rename"));
	labelPlayerProgress.setText(bundle.getString("label.settings_player_progress"));
	labelProgressDescription.setText(bundle.getString("label.settings_progress_description"));
	btnReset.setText(bundle.getString("btn.reset"));
	labelDelete.setText(bundle.getString("label.settings_delete"));
	labelDeleteDescription.setText(bundle.getString("label.delete_description"));
	btnDeletePlayer.setText(bundle.getString("btn.delete"));
	
}
	
}
