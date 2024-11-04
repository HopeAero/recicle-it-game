// ScreenManager.java
package client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Logger;

public class ScreenManager {
    private static ScreenManager instance;
    private Game game;
    private AbstractScreen menuScreen;
    private AbstractScreen gameScreen;
    private static final Logger logger = new Logger(ScreenManager.class.getName(), Logger.DEBUG);

    private ScreenManager(Game game) {
        this.game = game;
        menuScreen = new MenuScreen(game);
        gameScreen = new GameScreen(game);
    }

    public static ScreenManager getInstance(Game game) {
        try {
            if (instance == null) {
                instance = new ScreenManager(game);
            }
        } catch (Exception e) {
            logger.error("Failed to get instance of ScreenManager", e);
        }
        return instance;
    }

    public void showMenuScreen() {
        try {
            game.setScreen(menuScreen);
        } catch (Exception e) {
            logger.error("Failed to set menu screen", e);
        }
    }

    public void showGameScreen() {
        try {
            game.setScreen(gameScreen);
        } catch (Exception e) {
            logger.error("Failed to set game screen", e);
        }
    }
}
