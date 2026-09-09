package Interface;

import java.util.List;

import cards.Language;
import cards.Translation;

public interface ICardData {
	
/**
 * Set the path to image of card 
 * 
 * @param imagePath
*/
void setImagePath(String imagePath);

/**
 * Returns the path to the card image
 * 
 * @return imagePath
*/
String getImagePath();

/**
 * Set the path to sound of card 
 * 
 * @param soundPath
*/
void setSoundPath(String soundPath);

/**
 * Returns the path to the card sound
 * 
 * @return soundPath
*/
String getSoundPath();

/**
 * Set the card translation
 * 
 * @param translation
*/
void setTranslation(Translation translation);

/**
 * Returns the translation of the card for the selected language
 * 
 * @param language
 * 
 * @return soundPath
*/
Translation getTranslation(Language language);

/**
 * Returns the list of all existing translations.
 * 
 * @return List<>
*/
List<String> getTranslations();

/**
 * Update translation data 
 * 
 * @param language(selected language)
 * @param name(name of card)
 * @param description(description of card)
*/
void updateTranslation(Language language, String name, String description);

}
