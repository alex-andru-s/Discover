package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Interface.IGameService;
import app.App;
import cards.Card;
import cards.Language;
import game.GameMode;
import game.GameSession;
import player.PlayerCardProgress;
import player.PlayerMode;

public class GameService implements IGameService {

@Override
public boolean checkAnswer(Card card, String playerAnswer ){

	if(playerAnswer == null || playerAnswer.isBlank()) {
             return false;
	 }
	String correctAnswer =  App.getCardService()
			                .getCard(card)
			                .getCardData()
			                .getTranslation(App.getMainLanuage())
			                .getName();
   
	 return correctAnswer.equalsIgnoreCase(playerAnswer.trim());
}

@Override
public boolean checkAnswer(Card card, String playerAnswer, 
		                                           Language language ){

	String correctAnswer = card.getCardData()
            .getTranslation(language)
            .getName();
	
	return correctAnswer.equalsIgnoreCase(playerAnswer.trim());
}

private int calculatePoints(boolean correct, GameMode mode) {
	
	  if (mode == GameMode.NORMAL ) {
	       return correct ? 1 : 0;
	  }else 
		   return correct ? 2 : 0;
	  }

@Override
public void registerAnswer(boolean correct, GameSession session) {

	 int points = calculatePoints(correct, session.getGameMode());

	 session.addScore(points);
	 GameMode mode = session.getGameMode();
 
	 Card card = session.getCurrentCard();
	 
     if(App.getCurrentPlayer() != null) {
    			 
	   if(correct){ 
		  App.getCurrentPlayer()
		     .getCardProgress(card)
		     .registerCorrect(mode);

		  App.getCurrentPlayer()
			 .getStatistics()
			 .addPoints(points);
		    	 
        }else{
    	      App.getCurrentPlayer()
		      .getCardProgress(card)
		      .registerWrong(mode);
	         } 
	       
    App.getPlayerService().updatePlayerData(App.getCurrentPlayer());
   }
}

@Override
public List<Card> selectCards(List<Card> availableCards, GameMode gameMode) {

  List<Card> newList = new ArrayList<>();
  
  if(App.getPlayerMode().equals(PlayerMode.GUEST)) {
	   newList.addAll(availableCards);
	     Collections.shuffle(newList);
	     return newList;
   }
  for(Card card : availableCards) {
      PlayerCardProgress progress =
                            App.getCurrentPlayer().getCardProgress(card); 
      
      if(gameMode.equals(GameMode.LEARN)) {  	   
	      if(!progress.isLearnedLanguage()) {   	 
	        newList.add(card);
	       }
	  }    
      if(gameMode.equals(GameMode.ADVANCED)){
	      if(!progress.isLearnedAdvance()) {
	           newList.add(card);
	       }
	  }   
      if(gameMode.equals(GameMode.NORMAL)){
	      if(!progress.isLearned()) {
	           newList.add(card);        	
	       }
	  }
   }  
	 Collections.shuffle(newList);
	 return newList;	 
 }

}
