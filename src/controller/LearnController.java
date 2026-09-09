package controller;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import app.App;
import app.BackgroundScene;
import cards.Card;
import cards.Translation;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import planet.PlanetContentCategory;
import java.util.Locale;

public class LearnController {

@FXML
private StackPane cardContainer;
@FXML
private StackPane imageContainer;
@FXML
private ScrollPane categoryScroll;
@FXML
private ProgressBar progressBar;
@FXML
private Label LabelCategory;
@FXML
private Label cardName;
@FXML
private HBox contentCategoryContainer;
@FXML
private Label cardCounter;
@FXML
private ImageView image;
@FXML
private Button soundButton;
@FXML
private Label description;
@FXML
private Label categoryTitle;
@FXML
private Button previousButton;
@FXML
private Button nextButton;
@FXML
private Button returnToMenu;

@FXML
private StackPane root;
private BackgroundScene videoBackground;
private static MediaPlayer mediaPlayer;

private Card currentCard;
private int currentIndex;
private PlanetContentCategory selectedCategory;
private Media sound;
private ResourceBundle bundle;

private List<Card> currentCards;



@FXML
public void initialize() {
   if(this.root != null) { 
	 videoBackground = new BackgroundScene();
	 root.getChildren().add(0, videoBackground); 
   }
   
   bundle = ResourceBundle.getBundle(
		    "resources.lang.messages",
		    Locale.of(App.getMainLanuage().getCode()));
   
   setText();
   loadPlanetContent();
   }

public void loadPlanetContent() {
	if(App.getCardService().getAllCards().size() == 0) {
		new Alert(AlertType.INFORMATION,bundle.getString("jo.game_message"))
		     .showAndWait();
	      throw new IllegalArgumentException(
	        		"No existing cards to learn");
	}
	
	for(Card c: App.getCardService().getAllCards()) {
	    App.getCurrentPlayer().getCardProgress(c);
	}
	
	loadCategories();   
}

private void loadCategories() {

	contentCategoryContainer.getChildren().clear();

    List<PlanetContentCategory> categories =
            App.getCardService().getAllCards().stream()
                    .map(Card::getCategory)
                    .filter(category -> category != null )
                    .distinct()
                    .toList();

    for (PlanetContentCategory category : categories) {

        Button button = createCategoryButton(category);

        contentCategoryContainer.getChildren().add(button);
    }

    if (!categories.isEmpty()) {

        selectCategory(categories.get(0));
    }
 }

private Button createCategoryButton(PlanetContentCategory category) {

        Button button = new Button();
       
        button.setText(bundle.getString(category.getDescription())); 

        button.setMinWidth(165);
        button.setPrefHeight(30);

        button.getStyleClass().add("category-button");

        button.setUserData(category);

        button.setOnAction(event -> selectCategory(category));
        
        return button;
 }

private void selectCategory(PlanetContentCategory category) {
	this.selectedCategory = category;
    categoryTitle.setText(bundle.getString(category.getDescription()));

    this.currentCards = App.getCardService().getAllCards().stream()
            .filter(card -> card.getCategory() == category)
            .toList();

    this.currentIndex = 0;
   
    updateSelectedCategoryButton();
    
    showCard();
    
 }

private void updateSelectedCategoryButton() {

    for (Node node : contentCategoryContainer.getChildren()) {

        if (node instanceof Button button) {

            button.getStyleClass()
                    .remove("category-button-selected");

            if (button.getUserData() == selectedCategory) {

                button.getStyleClass()
                        .add("category-button-selected");
            }
        }
    }
 }

private void showCard( ) {
		
   this.currentCard = this.currentCards.get(currentIndex);
   Translation translation = currentCard.getCardData()
		       .getTranslation(App.getMainLanuage());

   cardName.setText(translation.getName().toUpperCase());

   description.setText(translation.getDescription());
   
   progressBar.setProgress((double)(this.currentIndex + 1  ) 
		                     / this.currentCards.size());
   
   cardCounter.setText(this.currentIndex + 1 + "/" 
                              + this.currentCards.size());
   
   image.setImage(new Image(new File(currentCard
		   .getCardData()
		   .getImagePath())
		   .toURI().toString()));
   
   String path = currentCard.getCardData()
                                .getSoundPath();
   if(path != null) {
   sound = new Media(new File(path)
	      .toURI().toString());
   
   soundButton.setDisable(false);
   soundButton.setVisible(true);
   }
   else {
	   soundButton.setDisable(true);
	   soundButton.setVisible(false);   
   }
   
   updateNavigationButtons();
   App.getCurrentPlayer().getCardProgress(currentCard).setViewed();
   App.getPlayerService().updatePlayerData(App.getCurrentPlayer());
 
}
 
private void updateNavigationButtons() {

    previousButton.setDisable(currentIndex == 0);

    nextButton.setDisable(currentIndex >= currentCards.size() - 1);
    
}

@FXML
private void nextCard() {
	
    for(int i = currentIndex + 1; i < currentCards.size(); i++) {
        if(!App.getCurrentPlayer()
                .getCardProgress(currentCards.get(i))
                .isViewed()) {

            currentIndex = i;
            showCard();
            return;
        }
    }
    
   if(currentIndex < currentCards.size() - 1) {
           currentIndex++;
   } else
          { currentIndex = 0;
   }
   
  showCard();
  
 }

@FXML
private void previousCard() {
	
   if(currentIndex > 0) {
        currentIndex--;
    }
  showCard();
}

@FXML
private  void playSound() {
   
   mediaPlayer = new MediaPlayer(sound);
   mediaPlayer.play();
   
} 

public void returnToMenu() {
   App.getPlayerService().updatePlayerData(App.getCurrentPlayer());
   App.getSceneManager().showMenu();
   
	}
private void setText() {
	LabelCategory.setText(bundle.getString("label.learn"));
	nextButton.setText(bundle.getString("btn.next"));
	previousButton.setText(bundle.getString("btn.previous"));
	returnToMenu.setText(bundle.getString("btn.return_menu"));
	soundButton.setText(bundle.getString("btn.play_sound"));
}
}
