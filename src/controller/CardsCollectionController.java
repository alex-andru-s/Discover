package controller;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import app.App;
import cards.Card;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import player.PlayerCardProgress;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class CardsCollectionController {

@FXML
private FlowPane cardsContainer;
@FXML
private Label labelTitle;
@FXML
private Label countLabel;
@FXML
private Label emptyLabel;
	
private double learned;
private ResourceBundle bundle;
@FXML
public void initialize() {
	 bundle = ResourceBundle.getBundle(
	    	 "resources.lang.messages",
	    	  Locale.of(App.getMainLanuage().getCode()));
	 
	 learned = App.getCurrentPlayer()
				.getProgress().values().stream()
			    .filter(card->card.isLearned() == true ).count();
	 setText();
	 loadCollection();
}

private void loadCollection() {
	
	cardsContainer.getChildren().clear();

	PlayerCardProgress progress;

	for(Card card : App.getCardService().getAllCards()) {
	  progress = App.getCurrentPlayer().getCardProgress(card);
	  
	    if(progress.isLearned()) {

	      Node cardView = createCardView(card, progress);
	      cardsContainer.getChildren().add(cardView);
	    }
	 }
	countLabel.setText(bundle.getString("label.collection_learned")
			                              + " " + (int)learned);
    if(learned == 0)
	   showEmpty();
    
}

private Node createCardView(Card card, PlayerCardProgress progress) {

	  VBox cardBox = new VBox(8);
      cardBox.setAlignment(Pos.CENTER);
      cardBox.getStyleClass().add("collection-card");
      cardBox.setPrefWidth(180);
	  cardBox.setPrefHeight(220);
	  
      ImageView image = new ImageView();
      image.setFitWidth(60);
      image.setFitHeight(60);
      image.setImage(new Image(new File(card
			   .getCardData()
			   .getImagePath())
			   .toURI().toString()));
      
	  Label nameLabel = new Label(
			  card.getCardData()
			  .getTranslation(App.getMainLanuage())
			  .getName().toUpperCase());

	  nameLabel.getStyleClass().add("collection-card-name");

	  Label descriptionLabel = new Label(
			  card.getCardData()
			  .getTranslation(App.getMainLanuage())
			  .getDescription());

	  descriptionLabel.setWrapText(true);
	  descriptionLabel.setMaxWidth(150);
      descriptionLabel.getStyleClass().add("collection-card-description");

	  Label accuracyLabel = new Label( String.format(
			  bundle.getString("label.collection_accuracy") + ": " + "%.0f%%",
	                        progress.getCardAccuracy()));

	  accuracyLabel.getStyleClass().add("collection-card-accuracy");

	  Label statusLabel = new Label(bundle.getString("label.collection_card_learned"));

	  statusLabel.getStyleClass().add("collection-card-status");
	  

	  cardBox.getChildren().addAll(
			        image,
	                nameLabel,
	                descriptionLabel,
	                accuracyLabel,
	                statusLabel);

	   return cardBox;
  }

private void showEmpty() {

	countLabel.setText(bundle.getString("label.collection_learned") + " " + 0);
    emptyLabel.setVisible(true);
    emptyLabel.setText(bundle.getString("label.collection_empty"));
  }

private void setText() {
	labelTitle.setText(bundle.getString("label.collection_title"));
	
}
	
}
