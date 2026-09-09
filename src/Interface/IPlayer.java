package Interface;

import java.util.Map;

import cards.Card;
import player.PlayerCardProgress;
import player.PlayerStatistics;

public interface IPlayer {

/**
 * Set player name 
 * @param name
 */
void setName(String name);
/**
 * Returns player name
 * @return name
 */
String getName();
/**
 * Set player ID
 * @param id
 */
void setId(long id);
/**
 * Returns player ID
 * @return id
 */
long getId();
/**
 * Returns player progress of a given card 
 * If progress is absent create a new progress
 * 
 * @param card
 * @return card progress 
 */
PlayerCardProgress getCardProgress(Card card);
/**
 * Returns player statistics
 * @return player statistics
 */
PlayerStatistics getStatistics();
/**
 * Set player statistics
 * @param statistic
 */
void setStatistics(PlayerStatistics statistic);
/**
 * 
 * @return progress of all cards
 */
Map<Long, PlayerCardProgress> getProgress();

}
