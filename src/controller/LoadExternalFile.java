package controller;

import java.io.File;

import javafx.stage.FileChooser;

public class LoadExternalFile {
	

FileChooser chooser = new FileChooser();

public  FileChooser selectImage() {

	chooser.setTitle("Seleziona l'immagine della carta");
	chooser.getExtensionFilters().add(new FileChooser
	       .ExtensionFilter(
		           "Images",
                   "*.png",
                   "*.jpg",
                   "*.jpeg",
		           "*.webp" ));	
	return chooser;
}

public FileChooser selectSound() {
	
    chooser.setTitle("Seleziona suono della carta");
    chooser.getExtensionFilters().add(new FileChooser
   		   .ExtensionFilter(
	                "Audio",
	                "*.mp3",
	                "*.wav",
	                "*.m4a" ));
    return chooser;
  }
}
