package player;

import Interface.IPlayerStatistics;

public class PlayerStatistics implements IPlayerStatistics {

private int points = 0;
private int correct = 0;
private int wrong = 0;
private int level = 0 ;

@Override
public int getPoints() {
    return this.points;
}

@Override
public void addPoints(int points) {
    this.points += points;
}

@Override
public int getAnswered() {
    return this.correct + this.wrong;
}

@Override
public int getCorrect() {
    return correct;
}

@Override
public int getWrong() {
    return wrong;
}

@Override
public double getCardsAccuracy() {
	if(getAnswered() != 0)
       return (double)this.correct / getAnswered();
     else 
    	 return 0;
}
@Override
public void addCorrect(int correct) {
	this.correct = correct;
	
}
@Override
public void addWrong(int wrong) {
	this.wrong = wrong;
	
}
@Override
public int getLevel() {
	
	return this.level;
}
@Override
public void addLevel(int level) {
	this.level = level;
}
 	   	
}
