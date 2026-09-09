package Interface;

public interface IPlayerStatistics {

/**
 * Returns player points 
 * @return points
 */
int getPoints();

/**
 * Add player points 
 * @param points
 */
void addPoints(int points);

/**
 * Returns player total number of answers
 * @return  wrong and correct answers
 */
int getAnswered();

/**
 * Add player correct answers 
 * @param correct
 */
void addCorrect(int correct); 
/**
 * 	Returns player correct answers sum
 * @return  correct answers
 */
int getCorrect();

/**
 * Add player wrong answers
 * @param wrong
 */
void addWrong(int wrong);

/**
 * Returns player wrong answers sum
 * @return sum wrong answers
 */
int getWrong();

/**
 * Returns  player accuracy 
 * @return accuracy 
 */
double getCardsAccuracy();

/**
 * Returns player level
 * @return  level
 */
int getLevel();

/**
 * Add player level
 * @param level
 */
void addLevel(int level);
	
	
	 	   	
}
