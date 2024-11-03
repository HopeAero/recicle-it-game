package client.screen;

import com.badlogic.gdx.Game;

public class ScreenManager {
    private static ScreenManager instance;
    private Game game;
    private AbstractScreen menuScreen;
    private AbstractScreen gameScreen;

    private ScreenManager(Game game) {
        this.game = game;
        menuScreen = new MenuScreen(game);
        gameScreen = new GameScreen(game);
    }

    public static ScreenManager getInstance(Game game) {
        if (instance == null) {
            instance = new ScreenManager(game);
        }
        return instance;
    }

    public void showMenuScreen() {
        game.setScreen(menuScreen);
    }

    public void showGameScreen() {
        game.setScreen(gameScreen);
    }
}
