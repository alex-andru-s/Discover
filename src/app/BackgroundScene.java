package app;

import java.net.URL;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class BackgroundScene extends StackPane {

	
private MediaPlayer mediaPlayer;
private MediaView mediaView;

public BackgroundScene() {
	 
URL resource = getClass().getResource("/resources/video/background2.mp4");

if(resource == null) { 
	throw new IllegalArgumentException( "Video not found: " + resource);
	        }

Media media = new Media(resource.toExternalForm());

mediaPlayer =  new MediaPlayer(media);

// play loop
mediaPlayer.setCycleCount( MediaPlayer.INDEFINITE);

// settings 
mediaPlayer.setAutoPlay(true);
mediaPlayer.setMute(true);

mediaView =new MediaView(mediaPlayer);

// screen dimension 
mediaView.setPreserveRatio(false);

mediaView.fitWidthProperty().bind(widthProperty());
mediaView.fitHeightProperty().bind(heightProperty());

getChildren().add(mediaView);
	    }

public void dispose() {
	mediaPlayer.dispose();
	    }

}
