package controller;

import app.App;
import app.BackgroundScene;
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
import planet.PlanetContentCategory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import Exceptions.CreateCardException;


public class CardCreateController {
	
@FXML
private StackPane root;
@FXML
private ComboBox<Language> languageComboBox;
@FXML
private ComboBox<String> categoryComboBox;
@FXML
private TextField languageField;
@FXML
private TextField titleField;
@FXML
private TextArea descriptionField;
@FXML
private TextArea tDescriptionField;
@FXML
private ImageView imagePreview;
@FXML
private Label imagePlaceholder;
@FXML
private Label labelTitle;
@FXML
private Label labelCardPreview;
@FXML
private Label labelSecondaryTitle;
@FXML
private Label soundLabel;
@FXML
private Label messageLabel;
@FXML
private Label boxLableCard;
@FXML
private Label boxLabelCategory;
@FXML
private Label boxLabelDescription;
@FXML
private Label boxLabelDescription2;
@FXML
private Label boxLabelTranslation;
@FXML
private Button btnSelectImage;
@FXML
private Button btnSelectSound;
@FXML
private Button btnCancel;
@FXML
private Label boxLabelLang;
@FXML
private Button btnCreateCard;

private File selectedImage;
private File selectedSound;

@FXML
private BackgroundScene videoBackground;
private ResourceBundle bundle;

public void initialize() {
	bundle = ResourceBundle.getBundle(
	    	 "resources.lang.messages",
	    	  Locale.of(App.getMainLanuage().getCode()));
	
	setText();
	loadPlanetContent();
}

private void loadPlanetContent() {

  categoryComboBox.getItems().setAll(getCategoriesName()); 
  for( Language lang : Language.values())
	 if(lang != App.getMainLanuage())
      languageComboBox.getItems().addAll(lang);
}

@FXML
private  void selectImage() {

	Stage stage = (Stage)imagePreview.getScene().getWindow();

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

	 Stage stage = (Stage)soundLabel.getScene().getWindow();
     selectedSound = new LoadExternalFile()
    		              .selectSound()
    		               .showOpenDialog(stage);
    
     if (selectedSound != null) {
           soundLabel.setText(
	       selectedSound.getName());
    }
}

@FXML
private void createCard() throws CreateCardException {
	
	String category = categoryComboBox.getValue();
    String title = titleField.getText().trim().toUpperCase();
    String description = descriptionField.getText().trim();
    String tTitle = languageField.getText().trim().toUpperCase();
    String tDescription = tDescriptionField.getText().trim();
    Language language  = languageComboBox.getValue();
   
    if (category == null || category.isEmpty() || category.isBlank() ) {
       messageLabel.setText(bundle.getString("label.add_insert_category"));
        throw new CreateCardException();
	  }
    
    if (title.isEmpty() || title.isBlank()) {
         messageLabel.setText(bundle.getString("label.add_insert_title"));
          throw new CreateCardException();
	 }
    
    if(description.isEmpty() || description.isBlank()) {
    	 messageLabel.setText(bundle.getString("label.add_insert_description"));
         throw new CreateCardException();
    }
    if(tTitle.isEmpty() || tTitle.isBlank()) {
    	messageLabel.setText(bundle.getString("label_add_insert_title_lang"));
        throw new CreateCardException();
    }
    if(language == null) {
    	messageLabel.setText(bundle.getString("label.add_insert_language"));
           throw new CreateCardException();
     }
    if(tDescription.isEmpty() || tDescription.isBlank()) {
   	 messageLabel.setText(bundle.getString("label.add_insert_description_lang"));
        throw new CreateCardException();        
    }   
    
    CardData cardDetails = new CardData(
    		new Translation(description, title, App.getMainLanuage()),
    	    new Translation(tDescription,tTitle, language));
    
	if(selectedSound != null) {
	   File targetFile = new File("src/resources/sound/" 
                                    + selectedSound.getName());
      try {
           Files.copy(selectedSound.toPath(), 
           targetFile.toPath(), 
           StandardCopyOption.REPLACE_EXISTING);          
      }catch (IOException e) {
               e.printStackTrace();
              }
      cardDetails.setSoundPath("src/resources/sound/" 
                                    + selectedSound.getName());
	 } 

	if(selectedImage != null) {
	   File targetFile = new File("src/resources/image/" 
	                                 + selectedImage.getName());
         try {
                Files.copy(selectedImage.toPath(), 
            		       targetFile.toPath(), 
            		       StandardCopyOption.REPLACE_EXISTING);          
        }catch (IOException e) {
                 e.printStackTrace();
               }
	   cardDetails.setImagePath("src/resources/image/" 
                                          + selectedImage.getName());
	 }
	
	else {messageLabel.setText(bundle.getString("label.add_select_image"));
       throw new CreateCardException();
	}
	
	for(PlanetContentCategory c : PlanetContentCategory.values())
		if(bundle.getString(c.getDescription()).equals(category))
			try {App.getCardService().addCard(c,cardDetails);
			}catch(Exception e) {
				messageLabel.setText(bundle.getString("label.add_card_exists"));
				 throw new CreateCardException();
			}
	
	messageLabel.setText(bundle.getString("label.add_success_message")); 
	cancel();	         
}

@FXML
private void cancel() {
	languageComboBox.getSelectionModel().clearSelection();
	categoryComboBox.getSelectionModel().clearSelection();
	titleField.clear();
	languageField.clear();
	descriptionField.clear();
	tDescriptionField.clear();
	imagePreview.setImage(null); 
	selectedImage = null;
	selectedSound = null;
	soundLabel.setText(
			bundle.getString("label.sound_not_selected"));
}

private List<String> getCategoriesName(){
	
	  List<String> list = new ArrayList<>();
	   for(PlanetContentCategory c : PlanetContentCategory.values())
			 list.add(bundle.getString(c.getDescription()));
			 return list; 
	 }
private void setText() {
	labelTitle.setText(bundle.getString("label.add_card_title"));
	labelSecondaryTitle.setText(bundle.getString("label.add_card_scnd_title"));
	labelCardPreview.setText(bundle.getString("label.add_card_preview"));
	btnSelectImage.setText(bundle.getString("btn.select_image"));
	btnSelectSound.setText(bundle.getString("btn.select_sound"));
	btnCancel.setText(bundle.getString("btn.cancel"));
	btnCreateCard.setText(bundle.getString("btn.create_card"));
	imagePlaceholder.setText(bundle.getString("label.image_not_selected"));
	soundLabel.setText(bundle.getString("label.sound_not_selected"));
	titleField.setPromptText(bundle.getString("tf.input_lang_prompt"));
	categoryComboBox.setPromptText(bundle.getString("combo.category_prompt"));
	boxLabelCategory.setText(bundle.getString("label.box_category"));
	boxLableCard.setText(bundle.getString("label.box_card_title"));
	boxLabelDescription.setText(bundle.getString("label.box_card_descriptio"));
	descriptionField.setPromptText(bundle.getString("tf.input_description_prompt"));
	tDescriptionField.setPromptText(bundle.getString("tf.input_description_inv_prompt"));
	languageField.setPromptText(bundle.getString("tf.input_lang_inv_prompt"));
	languageComboBox.setPromptText(bundle.getString("combo.language_select"));
	boxLabelLang.setText(bundle.getString("label.box_translation_language"));
	boxLabelTranslation.setText(bundle.getString("label.box_translation"));
	boxLabelDescription2.setText(bundle.getString("label.box_card_descriptio"));
}
}