package controller;

import app.App;
import cards.Card;
import cards.CardData;
import cards.Language;
import cards.Translation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.ResourceBundle;

public class CardManagementController {

@FXML
private StackPane root;
@FXML
private ComboBox<Card> cardComboBox;
@FXML
private ComboBox<Language> languageComboBox;

@FXML
private TextField titleField;
@FXML
private TextArea descriptionField;
 @FXML
private ImageView imagePreview;
@FXML
private Label imagePlaceholder;
@FXML
private Label soundLabel;
@FXML
private Label messageLabel;
@FXML
private Label labelTitle;
@FXML
private Label labelCardPreview;
@FXML
private Label labelSecondaryTitle;
@FXML
private Label labelSelectCard;
@FXML
private Label labelDescription;
@FXML
private Label labelCardTitle;
@FXML
private Button btnChangeImage;
@FXML
private Button btnChangeSound;
@FXML
private Button btnCancel;
@FXML
private Button btnRemoveCard;
@FXML
private Button btnSaveChanges;
 

private File selectedImage;
private File selectedSound;

private Card selectedCard;
private Language selectedLanguage;

private ResourceBundle bundle;

@FXML
public void initialize() {

   bundle = ResourceBundle.getBundle("resources.lang.messages",
               Locale.of(App.getMainLanuage().getCode()));
   
  setText();
  loadCards();
  
  cardComboBox.setOnAction(event -> cardSelected()); 
  languageComboBox.setOnAction(event -> languageSelected());  
}

private void loadCards() {

  cardComboBox.getItems().setAll(App.getCardService().getAllCards());
   
  cardComboBox.setCellFactory(list -> new ListCell<>() {
	  @Override
       protected void updateItem(Card card, boolean empty) {
           super.updateItem(card, empty);
              if(empty || card == null) { 
                    setText(null);
              }else{

                     Translation translation =
                            card.getCardData()
                                .getTranslation(App.getMainLanuage());

                    if(translation != null) {
                        setText(translation.getName());
                    }else {
                        setText(bundle.getString("label.manage_card_unknown"));
                    }
                }
           }
    });

  cardComboBox.setButtonCell(new ListCell<>() {
      @Override
      protected void updateItem(Card card, boolean empty) {
          super.updateItem(card, empty);

          if (empty || card == null) {
              setText(null);
          } else {

              Translation translation =
                      card.getCardData()
                          .getTranslation(App.getMainLanuage());

              if (translation != null) {
                  setText(translation.getName().toUpperCase());
              } else {
                  setText(bundle.getString("label.manage_card_unknown"));
              }
          }
      } }); 
}

private void cardSelected() {

   selectedCard = cardComboBox.getValue();

   if(selectedCard == null) {
        return;
    }

   loadCardPreview();
   loadLanguages();

   languageComboBox.getSelectionModel()
                   .select(App.getMainLanuage());
}

private void loadLanguages() {

    languageComboBox.getItems().clear();

    if(selectedCard == null) {
            return;
     }

    CardData data = selectedCard.getCardData();

 
    for(Language language : Language.values()) {

       if(data.getTranslation(language) != null) {

         languageComboBox.getItems().add(language);
       }
     }
}

private void languageSelected() {

  if(selectedCard == null) {
            return;
   }

   selectedLanguage = languageComboBox.getValue();

   if(selectedLanguage == null) {
            return;
    }

   Translation translation = selectedCard.getCardData()
                            .getTranslation(selectedLanguage);

   if(translation == null) {
            titleField.clear();
            descriptionField.clear();
            return;
   }

  titleField.setText(translation.getName());

  descriptionField.setText(translation.getDescription());

  messageLabel.setText("");
}
    
private void loadCardPreview() {

   CardData data = selectedCard.getCardData();

    if(data.getImagePath() != null) {

        File imageFile = new File(data.getImagePath());

        if(imageFile.exists()) {

        imagePreview.setImage(new Image(
    		               imageFile.toURI().toString()));

        imagePlaceholder.setVisible(false);

        } else { 
    	         imagePreview.setImage(null);
                 imagePlaceholder.setVisible(true);
               }

  }else {
          imagePreview.setImage(null);
          imagePlaceholder.setVisible(true);
        }

   if(data.getSoundPath() != null) {

       File soundFile = new File(data.getSoundPath());

       if(soundFile.exists()) {
           soundLabel.setText(
              soundFile.getName());
       }else{
              soundLabel.setText(bundle.getString("label.manage_no_sound"));
            }

   }else {
            soundLabel.setText(bundle.getString("label.manage_no_sound"));
        }
 }

@FXML
private void selectImage() {

	Stage stage =(Stage)imagePreview
                        .getScene()
                        .getWindow();

	selectedImage = new LoadExternalFile()
                        .selectImage()
                        .showOpenDialog(stage);

   if(selectedImage != null) {
       imagePreview.setImage(new Image(selectedImage
                                    .toURI()
                                    .toString()));

       imagePlaceholder.setVisible(false);
    }
}

@FXML
private void selectSound() {

  Stage stage = (Stage)soundLabel
                    .getScene()
                    .getWindow();

  selectedSound = new LoadExternalFile()
                    .selectSound()
                    .showOpenDialog(stage);
 
 if(selectedSound != null) {
     soundLabel.setText(
     selectedSound.getName());
   }
}

@FXML
private void updateCard() {
   if(selectedCard == null) {
       messageLabel.setText(bundle.getString("labe.manage_select_card_msg"));
            return;
    }

   if(selectedLanguage == null) {
     messageLabel.setText(bundle.getString("combo.select_language"));
         return;
    }

   String name = titleField.getText().trim();

   String description = descriptionField.getText().trim();

   if(name.isBlank()) {
       messageLabel.setText(bundle.getString("label.add_insert_title"));
            return;
    }

   if(description.isBlank()) {
       messageLabel.setText(bundle.getString("label.add_insert_description"));
            return;
    }

    selectedCard.getCardData().updateTranslation(
                        selectedLanguage,
                        name,
                        description);

    if(selectedImage != null) {
        String path = copyFile(selectedImage,"image");

       if(path != null) {
           selectedCard.getCardData()
                       .setImagePath(path);
        }
     }

    if(selectedSound != null) {
         String path = copyFile(selectedSound,  "sound");
         
       if(path != null) {
           selectedCard.getCardData()
                       .setSoundPath(path);
        }
      }

    try {
          App.getCardService()
                  .updateCardData(selectedCard, selectedCard.getCardData());
          cancel();
          messageLabel.setText(bundle.getString("label.manage_update_success"));

          loadCards();


     } catch (Exception e) {
               e.printStackTrace();

            messageLabel.setText(bundle.getString("label.manage_update_fail"));
      }
 }

@FXML
private void removeCard() { 

   if(selectedCard == null) {
        messageLabel.setText(bundle.getString("labe.manage_select_card_msg"));
    }

  Translation translation = selectedCard.getCardData()
                            .getTranslation(App.getMainLanuage());

  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(bundle.getString("alert.title"));
        alert.setHeaderText(bundle.getString("alert.text") + " "
                                    + translation.getName() + "?");
        alert.setContentText(bundle.getString("alert.inform"));
     
        ((Button) alert.getDialogPane().lookupButton(
        		ButtonType.OK)).setText(bundle.getString("btn.remove_card"));
        ((Button) alert.getDialogPane().lookupButton(
        		ButtonType.CANCEL)).setText(bundle.getString("btn.cancel"));
    
  if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) { 

        try {  
    	   App.getCardService().removeCard(selectedCard);
           cardComboBox.getItems().remove(selectedCard);
           
           cancel();    
           
           messageLabel.setText(bundle.getString("label.manage_remove_success"));

            } catch(Exception e) {

                e.printStackTrace();
                messageLabel.setText(bundle.getString("label.manage_remove_fail"));
             }
      }
}

 private String copyFile(File source, String finalPath) {

   File target = new File("src/resources/" + finalPath + "/"
                                + source.getName());
    try {
          Files.copy(
        		     source.toPath(),
                     target.toPath(),
                     StandardCopyOption.REPLACE_EXISTING);

          return target.getPath();

    }catch (IOException e) {
       e.printStackTrace();
           return null;
        }
}

@FXML
private void cancel() {
	
	cardComboBox.getSelectionModel().clearSelection();
	selectedCard = null;
    selectedLanguage = null;
    languageComboBox.getItems().clear();
    titleField.clear();
    descriptionField.clear();
    imagePreview.setImage(null);
    imagePlaceholder.setVisible(true);
    soundLabel.setText(bundle.getString("label.sound_not_selected"));
    messageLabel.setText("");
    
   }
private void setText() {
	labelCardPreview.setText(bundle.getString("label.add_card_preview"));
	imagePlaceholder.setText(bundle.getString("label.manage_image"));
	btnChangeImage.setText(bundle.getString("btn.change_image"));
	btnChangeSound.setText(bundle.getString("btn.change_sound"));
	soundLabel.setText(bundle.getString("label.sound_not_selected"));
	labelTitle.setText(bundle.getString("label.manage_title"));
	labelSecondaryTitle.setText(bundle.getString("label.manage_scnd_title"));
	labelSelectCard.setText(bundle.getString("label.manage_selec_card"));
	cardComboBox.setPromptText(bundle.getString("combo.select_card"));
	languageComboBox.setPromptText(bundle.getString("combo.select_language"));
	labelCardTitle.setText(bundle.getString("label.box_card_title"));
	labelDescription.setText(bundle.getString("label.box_card_descriptio"));
	titleField.setPromptText(bundle.getString("tf.input_lang_prompt"));
	descriptionField.setPromptText(bundle.getString("tf.input_description_prompt"));
	btnCancel.setText(bundle.getString("btn.cancel"));
	btnRemoveCard.setText(bundle.getString("btn.remove_card"));
	btnSaveChanges.setText(bundle.getString("btn.save_changes"));
	
}
}