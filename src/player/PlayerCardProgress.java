package player;

import Interface.IPlayerCardProgress;
import game.GameMode;

public class PlayerCardProgress implements IPlayerCardProgress{

private boolean viewed = false;
private int correctAnswersAdv = 0;
private int wrongAnswersAdv = 0;
private int correctAnswers = 0;
private int wrongAnswers = 0;
private int correctAnswersLang = 0;
private int wrongAnswersLang = 0;

public void setViewed() {
	 this.viewed = true;
}

@Override
public void registerCorrect(GameMode gameMode) {

	if(gameMode.equals(GameMode.NORMAL))
	       this.correctAnswers++;
	else if(gameMode.equals(GameMode.ADVANCED))
	         this.correctAnswersAdv++;
	else correctAnswersLang++;
} 

@Override
public void registerWrong(GameMode gameMode) {

	if(gameMode.equals(GameMode.NORMAL))
		  this.wrongAnswers++;
	else if(gameMode.equals(GameMode.ADVANCED))
    	 this.wrongAnswersAdv++;
	else wrongAnswersLang++;
}

@Override
public boolean isLearned() {
     return getCardAccuracy() >= 60 && getAttempts() > 2;
}

@Override
public boolean isLearnedAdvance() {
	
	return determineLeared(this.correctAnswersAdv,this.wrongAnswersAdv);	 
}

@Override
public boolean isLearnedLanguage() {
	
	return determineLeared(this.correctAnswersLang, this.wrongAnswersLang);		
 }

@Override
public double getCardAccuracy() {
    if(getAttempts() != 0) 
       return(double) this.correctAnswers/ getAttempts() * 100;
    else return 0; 
}

@Override
public boolean isViewed() {
	 return this.viewed;
}

@Override
public boolean needsReview() {
    return !isLearned();
}

@Override
public int getAttempts() {
	 return this.correctAnswers + this.wrongAnswers;
}

@Override
public int getCorrectAnswers() {
	return this.correctAnswers;
}

@Override
public int getWrongAnswers() {
	return this.wrongAnswers;
}

private boolean determineLeared(int correct, int wrong) {
	int attempts = correct + wrong;
	 double accuracy = 0.0;
	 
	 if(attempts != 0) {
		  accuracy = ((double)correct / 
   		       (double)attempts) * 100.0;
   }
	 return accuracy >= 60.0 && attempts > 2;
}



}
