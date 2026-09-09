package app;

import cards.Language;
import javafx.application.Application;
import javafx.stage.Stage;
import player.Player;
import player.PlayerMode;
import repositories.CardRepository;
import repositories.PlayerRepository;
import service.CardService;
import service.GameService;
import service.PlayerService;

public class App extends Application{

	private static SceneManager sceneManager;
	private static PlayerRepository playerRepository;
	private static CardRepository cardRepository;
	private static PlayerService playerService;
	private static CardService cardService;
	private static GameService gameService;
	
	private static Player currentPlayer;
	private static PlayerMode playerMode ;
    private static Language mainLanuage = Language.ENGLISH;
    
@Override
public void start(Stage stage) {
		
	playerRepository =  new PlayerRepository();
	cardRepository = new CardRepository();
	playerService = new PlayerService(playerRepository);
	cardService = new CardService(cardRepository);
	gameService =  new GameService();
	sceneManager = new SceneManager(stage);
	sceneManager.showLogin();
	 }

	public static PlayerService getPlayerService() {
	return playerService;
	}

	public static CardService getCardService() {
	return cardService;
	}

	public static GameService getGameService() {
	return gameService;
	}

	public static SceneManager getSceneManager() {
	return sceneManager;
	  }

	public static Player getCurrentPlayer() {
	    return currentPlayer;
	}
	
	public static void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public static void logout() {
	currentPlayer = null;
	sceneManager.showLogin();
	}
	
	public static PlayerMode getPlayerMode() {
	    return playerMode;
	}
   
	public static void setPlayerMode(PlayerMode mode) {
	    playerMode = mode;
	}

	public static Language getMainLanuage() {
		return mainLanuage;
	}

	public static void setMainLanuage(Language mainLanuage) {
		App.mainLanuage = mainLanuage;
	}
	}


