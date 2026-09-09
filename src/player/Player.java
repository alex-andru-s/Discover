
package player;

import java.util.Map;

import Interface.IPlayer;
import cards.Card;


import java.util.HashMap;

public class Player implements IPlayer {
	
private long id;	
private String name;
private PlayerStatistics statistics;
 
private Map<Long, PlayerCardProgress> progress;

public Player(String name, long id) {
   this.id = id; 
   this.name = name;
   progress = new HashMap<>();
   statistics = new PlayerStatistics(); 
       
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
public void setId(long id) {
	   this.id = id;
}

@Override
public long getId() {
    return this.id;
}

@Override
public PlayerCardProgress getCardProgress(Card card) {
   return progress.computeIfAbsent(card.getCardId(),
		              x -> new PlayerCardProgress());
}

@Override
public PlayerStatistics getStatistics() {
   return statistics;
}

@Override
public void setStatistics(PlayerStatistics statistic) {
	 this.statistics = statistic;
}

@Override
public Map<Long, PlayerCardProgress> getProgress() {
	return this.progress;
 }
	
}
