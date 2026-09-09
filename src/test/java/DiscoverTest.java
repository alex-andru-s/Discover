package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cards.Card;
import game.GameMode;
import game.GameSession;
import player.Player;
import player.PlayerCardProgress;


public class DiscoverTest {

Player player;
PlayerCardProgress progress;
Card card;
GameSession session;

List<Card> cards;


/* Player Test */

@Test
public void testPlayerProgressCreated() {
	player = new Player("Name", 1);

	card = new Card(1, null, null);

	PlayerCardProgress progress = player.getCardProgress(card);

	assertNotNull(progress);
}

@Test
public void testPlayerProgressForSameCard() {
	
    player = new Player("Name", 2);
    
	card = new Card(2, null, null);

	PlayerCardProgress progress1 = player.getCardProgress(card);
	PlayerCardProgress progress2 = player.getCardProgress(card);

	assertSame(progress1, progress2);
}

@Test
public void testProgressByEqualID() {
	 player = new Player("Name", 3);

	 Card card1 = new Card(3, null, null);
	 Card card2 = new Card(3, null, null);

	 PlayerCardProgress progress1 = player.getCardProgress(card1);
	 PlayerCardProgress progress2 = player.getCardProgress(card2);

	 assertSame(progress1, progress2);
}

@Test
public void testProgressWithNotEqualID() {
	  player = new Player("Name", 4);

	  Card card1 = new Card(4, null, null);
	  Card card2 = new Card(5, null, null);

	  PlayerCardProgress progress1 = player.getCardProgress(card1);
	  PlayerCardProgress progress2 = player.getCardProgress(card2);

	  assertNotSame(progress1, progress2);
}


/* PlayerCardProgress Test */
	
@Test
public void testCardNotLearned() {
   progress = new PlayerCardProgress();
       assertFalse(progress.isLearnedLanguage()); }

@Test
public void testCardNotLearnedTwoAnswers() {
   progress = new PlayerCardProgress();
   
   progress.registerCorrect(GameMode.LEARN);
   progress.registerCorrect(GameMode.LEARN);
     assertFalse(progress.isLearnedLanguage());
}

@Test
public void testCardIsLearned() {
	progress = new PlayerCardProgress();
	
	progress.registerCorrect(GameMode.LEARN);
	progress.registerCorrect(GameMode.LEARN);
	progress.registerCorrect(GameMode.LEARN);
	  assertTrue(progress.isLearnedLanguage());
}

@Test
public void testCardIsLearnedOneWrong() {
   progress = new PlayerCardProgress();
	
   for(int i = 0; i < 5; i++) {
	 progress.registerCorrect(GameMode.LEARN);
	}
	 progress.registerWrong(GameMode.LEARN);
       assertTrue(progress.isLearnedLanguage());
}

@Test
public void testCardNotLearnedMoreWrong() {
    progress = new PlayerCardProgress();
	 for(int i = 0; i < 6; i++) {
	       progress.registerWrong(GameMode.LEARN);
	 }
    progress.registerCorrect(GameMode.LEARN);
    progress.registerCorrect(GameMode.LEARN);
      assertFalse(progress.isLearnedLanguage());
}

@Test
public void testAccuracyLearned() {
	progress = new PlayerCardProgress();

	 // 3 correct + 2 wrong = 60%
	  for(int i = 0; i < 3; i++) {
	    progress.registerCorrect(GameMode.LEARN);
	   }
	 for(int i = 0; i < 2; i++) {
	    progress.registerWrong(GameMode.LEARN);
	  }
    assertTrue(progress.isLearnedLanguage());
}

@Test
public void testAccuracyNotLearned() {
	progress = new PlayerCardProgress();

	// 2 correct + 2 wrong = 50%
	progress.registerCorrect(GameMode.LEARN);
	progress.registerCorrect(GameMode.LEARN);

	progress.registerWrong(GameMode.LEARN);
	progress.registerWrong(GameMode.LEARN);

	assertFalse(progress.isLearnedLanguage());
}

@Test
public void testCountAnswersNormalMode() {
    progress = new PlayerCardProgress();

	progress.registerCorrect(GameMode.NORMAL);
	progress.registerCorrect(GameMode.NORMAL);
	progress.registerCorrect(GameMode.NORMAL);

	assertTrue(progress.isLearned()); 
	assertFalse(progress.isLearnedAdvance());
	 
}    

@Test
public void testCountAnswersAdvanceMode() {
	progress = new PlayerCardProgress();

	progress.registerCorrect(GameMode.ADVANCED);
	progress.registerCorrect(GameMode.ADVANCED);
	progress.registerCorrect(GameMode.ADVANCED);
	
	assertTrue(progress.isLearnedAdvance());
	assertFalse(progress.isLearnedLanguage());
}


/*  GameSession TESTS */

@Test
public void testEmptySession() {
	cards = new ArrayList<>();

	session = new GameSession(cards, GameMode.LEARN);

	assertNull(session.getCurrentCard());
}


@Test
public void testEmptyGameSessionIsFinished() {
	cards = new ArrayList<>();

	session = new GameSession(cards, GameMode.LEARN);

	assertTrue(session.isFinished());
}

@Test
public void testNotEmptyGameSession() {
	card = new Card(10, null, null);

	cards = new ArrayList<>();
	    cards.add(card);

	session = new GameSession(cards, GameMode.LEARN);

	assertFalse(session.isFinished());
}

@Test
public void TestcurrentCardIsFirst() {

	cards = new ArrayList<>();
	  cards.add(new Card(1, null, null));
	  cards.add(new Card(2, null, null));

    session = new GameSession(cards, GameMode.LEARN);

    assertSame(cards.get(0), session.getCurrentCard());
}

@Test
public void TestNextCard() {

	cards = new ArrayList<>();
	  cards.add(new Card(1, null, null));
	  cards.add(new Card(2, null, null));

	session = new GameSession(cards, GameMode.LEARN);

	session.nextCard();

	assertSame(cards.get(1), session.getCurrentCard());
}

@Test
public void testNextCardLimit() {

	cards = new ArrayList<>();
	   cards.add(new Card(1, null, null));
	   cards.add(new Card(2, null, null));
	   
	session = new GameSession(cards, GameMode.LEARN);

	  session.nextCard();
	  session.nextCard();
	  session.nextCard();
	  session.nextCard();

	  assertSame(cards.get(1), session.getCurrentCard());
}

@Test
public void testLastCardAnsweredEndGame() {
	card = new Card(1, null, null);

	cards = new ArrayList<>();
	   cards.add(card);

	session = new GameSession(cards, GameMode.LEARN);

	assertFalse(session.isFinished());

	session.setAnswered(card);

	assertTrue(session.isFinished());
}

@Test
public void testFirstCardAnsweredNextCard() {

	 cards = new ArrayList<>();
	   cards.add(new Card(1, null, null));
	   cards.add(new Card(2, null, null));
	 	   
     session = new GameSession(cards, GameMode.LEARN);

	 session.setAnswered(cards.get(0));

	 assertFalse(session.isFinished());
}

@Test
public void testSetCardsAndResetSession() {
	Card oldCard = new Card(1, null, null);
	Card newCard = new Card(2, null, null);

	List<Card> oldCards = new ArrayList<>();
	oldCards.add(oldCard);

	session = new GameSession(oldCards, GameMode.LEARN);

	session.setAnswered(oldCard);

	List<Card> newCards = new ArrayList<>();
	newCards.add(newCard);

	session.setCards(newCards);

	assertEquals(1, session.getCards().size());
	assertSame(newCard, session.getCurrentCard());
	assertFalse(session.isFinished());
 }

}
