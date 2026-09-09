package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Interface.IGameSession;
import cards.Card;

public class GameSession implements IGameSession {

private  GameMode gameMode;
private  List<Card> cards;
private Set<Card> answeredCards;

private int currentIndex = 0;
private int score = 0;

public GameSession(List<Card> cards,  GameMode gameMode) {
    this.gameMode = gameMode;
    this.cards = cards;
    answeredCards = new HashSet<>();
}

@Override
public void setGameMode(GameMode gameMode) {
    this.gameMode = gameMode;
}

@Override
public GameMode getGameMode() {
        return gameMode;
}

@Override
public Card getCurrentCard() {
   if(cards == null || cards.isEmpty()) {
            return null;
    }
    return cards.get(currentIndex);
}

@Override
public int getScore() {
     return score;
}

@Override
public void addScore(int points) {
      score += points;
}

@Override
public void nextCard() {
   if(currentIndex < cards.size() - 1) {
            currentIndex++;
        }
}
@Override
public boolean isFinished() {
    if(cards.isEmpty()) {
	    return true;
	 }
    else return currentIndex == cards.size() - 1
	           && answeredCards.contains(getCurrentCard());
}
@Override
public void setAnswered(Card card) {
    answeredCards.add(card);
}

@Override
public List<Card> getCards() {
	return this.cards;
}


public  void setCards(List<Card> newCardsList) {
	
	this.cards = new ArrayList<>(newCardsList);
	this.answeredCards.clear();
	this.currentIndex = 0;
}


}