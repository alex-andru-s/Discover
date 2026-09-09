package Interface;

import java.util.List;

import cards.Card;
import game.GameMode;

public interface IGameSession {
	
/**
 * Set game mode 
 * 
 * @param gameMode
 */
public void setGameMode(GameMode gameMode);

/**
 * Returns the game mode
 * 
 * @return gameMode
 */
public GameMode getGameMode();

/**
 * Returns current card
 * 
 * @return current card
 */
public Card getCurrentCard();

/**
 * Returns the session points
 * 
 * @return points
 */
public int getScore();

/**
 * Add points 
 * 
 * @param points
 */
public void addScore(int points);

/**
 *  Increase current index
 */
public void nextCard();

/**
 * Returns whether the game is over or not
 * 
 * @return game is over if cards list is empty or all cards have been answered.
 */
public boolean isFinished();

/**
 * Add the card to the list of completed cards.
 * @param card
 */
public void setAnswered(Card card);

/**
 * Returns a list with all existing card for this session
 * 
 * @return List<>
 */
public List<Card> getCards();

/**
 * Set the cards available,  reset the index and the list of completed cards. 
 * 
 * @param newCardsList
 */
public  void setCards(List<Card> newCardsList);
}
