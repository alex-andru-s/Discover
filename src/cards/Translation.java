package cards;

import Interface.ITranslation;

public class Translation implements ITranslation {
	
private String description;
private String name;
private Language language;

public Translation(String description, String name, Language language) {
	this.description = description;
	this.language = language;
	this.name = name;
}

@Override
public void setName(String name) {
	this.name = name;
}

@Override
public String getName() {
	return this.name;
}

@Override
public void setDescription(String description) {
	this.description = description;
}

@Override
public String getDescription() {
	return this.description;
}

@Override
public void setLanguage(Language language) {
	this.language = language;
}

@Override
public Language getLanguage() {
	return this.language;
}

}
