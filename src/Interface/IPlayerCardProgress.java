package Interface;

import game.GameMode;

public interface IPlayerCardProgress {
	
/**
 * Increase the number of correct answers for given game mode.
 * @param gameMode
 */
void registerCorrect(GameMode gameMode);

/**
 * Increase the number of wrong answers for given game mode.
 * @param gameMode
 */
void registerWrong(GameMode gameMode);

/**
 * 
 * @return card is learned or not
 */
boolean isLearned();

/**
 * 
 * @return card is learned advanced mode
 */
boolean isLearnedAdvance();
	
/**
 * 
 * @return translation of card is learned
 */
boolean isLearnedLanguage();

/**
 * 
 * @return card is viewed
 */
boolean isViewed();

/**
 * Set the card viewed
 */
void setViewed();

/**
 * 
 * @return card needs review
 */
boolean needsReview();

/**
 * 
 * @return total attempts of card
 */
int getAttempts();

/**
 * 
 * @return accuracy of card
 */
double getCardAccuracy();

/*
 * @return sum of correct answers of card
 */
int getCorrectAnswers();

/**
 * 
 * @return sum of wrong answers of card 
 */
int getWrongAnswers();
		
}
