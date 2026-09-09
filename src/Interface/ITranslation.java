package Interface;

import cards.Language;

public interface ITranslation {
	
/**
 * Set the name of card
 * 
 * @param name(name of card)
*/	
void setName(String name);

/**
 * Returns the card name 
 * 
 * @return name(card name)
*/	
String getName(); 

/**
 * Set the description of card
 * 
 * @param description(description of card)
*/
void setDescription(String description);

/**
 * Returns the card description
 * 
 * @return description(card description)
*/	
String getDescription(); 

/**
 * Set the language of card
 * 
 * @param language(language of card)
*/	
void setLanguage(Language language);

/**
 * Returns the card language
 * 
 * @return language(card language)
*/	
Language getLanguage();

}
