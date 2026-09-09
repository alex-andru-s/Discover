package cards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Interface.ICardData;

public class CardData implements ICardData{


private String imagePath;
private String soundPath;

private Map<Language, Translation> translation;

public CardData(Translation mainTranslation, Translation secondaryTranslation ){
	 this.translation = new HashMap<>();
     this.translation.put(
    		  secondaryTranslation.getLanguage(), secondaryTranslation);
     this.translation.put(
    	      mainTranslation.getLanguage(), mainTranslation);
}

@Override
public void setImagePath(String imagePath){
	this.imagePath = imagePath;
}

@Override
public  String getImagePath(){
    return this.imagePath;
}

@Override
public void setSoundPath(String soundPath){
	this.soundPath = soundPath;
}

@Override
public String getSoundPath(){
	return this.soundPath;
}

@Override
public void setTranslation(Translation translation) {
	if(!this.translation.containsKey(translation.getLanguage()))
	this.translation.put(
			translation.getLanguage(), translation);
}

@Override
public Translation getTranslation(Language language) {
	return this.translation.get(language);
	
}

@Override
public List<String> getTranslations(){
	return this.translation.values().stream()
			.map(lang->lang.getName())
			.toList();
}

@Override
public void updateTranslation(Language language, String name, String description) {
	getTranslation(language).setDescription(description);
	getTranslation(language).setName(name);
	
}


}
