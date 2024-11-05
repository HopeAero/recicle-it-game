// MenuScreen.java
package client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen extends AbstractScreen {
    private Texture texture;
    private SpriteBatch batch;
    private float x, y, scale;
    private OrthographicCamera camera;
    private ScreenViewport viewport;

    // Textures for buttons
    private Texture playButtonNormal;
    private Texture playButtonHover;
    private Texture playButtonPressed;
    private Texture exitButtonNormal;
    private Texture exitButtonHover;
    private Texture exitButtonPressed;

    // Button states
    private boolean isPlayButtonHovered = false;
    private boolean isExitButtonHovered = false;
    private boolean isPlayButtonClicked = false;
    private boolean isExitButtonClicked = false;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        texture = new Texture(Gdx.files.internal("assets/menu.png"));
        // Load button textures
        playButtonNormal = new Texture(Gdx.files.internal("assets/sprites/buttons/Start/Start1.png"));
        playButtonHover = new Texture(Gdx.files.internal("assets/sprites/buttons/Start/Start2.png"));
        playButtonPressed = new Texture(Gdx.files.internal("assets/sprites/buttons/Start/Start3.png"));

        exitButtonNormal = new Texture(Gdx.files.internal("assets/sprites/buttons/Quit/Quit1.png"));
        exitButtonHover = new Texture(Gdx.files.internal("assets/sprites/buttons/Quit/Quit2.png"));
        exitButtonPressed = new Texture(Gdx.files.internal("assets/sprites/buttons/Quit/Quit3.png"));
        batch = new SpriteBatch();
        // Set up camera and viewport
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);

        // Detect mouse position and convert coordinates
        float mouseX = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())).x;
        float mouseY = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())).y;

        // Check if mouse is over the "Play" button
        isPlayButtonHovered = mouseX >= 340 && mouseX <= 340 + playButtonNormal.getWidth() * 2 &&
            mouseY >= 100 && mouseY <= 100 + playButtonNormal.getHeight() * 2;

        // Check if mouse is over the "Exit" button
        isExitButtonHovered = mouseX >= 340 && mouseX <= 340 + exitButtonNormal.getWidth() * 2 &&
            mouseY >= 50 && mouseY <= 50 + exitButtonNormal.getHeight() * 2;

        // Handle button clicks
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (isPlayButtonHovered && !isPlayButtonClicked) {
                isPlayButtonClicked = true;
                System.out.println("Play button clicked");
                ScreenManager.getInstance(game).showGameScreen();
            }
            if (isExitButtonHovered && !isExitButtonClicked) {
                isExitButtonClicked = true;
                System.out.println("Exit button clicked");
            }
        } else {
            isPlayButtonClicked = false;
            isExitButtonClicked = false;
        }

        batch.begin();
        batch.draw(texture, x, y, texture.getWidth() * scale, texture.getHeight() * scale);

        // Draw "Play" button based on its state
        if (isPlayButtonHovered) {
            if (isPlayButtonClicked) {
                batch.draw(playButtonPressed, 340, 100, playButtonNormal.getWidth() * 2, playButtonNormal.getHeight() * 2);
            } else {
                batch.draw(playButtonHover, 340, 100, playButtonNormal.getWidth() * 2, playButtonNormal.getHeight() * 2);
            }
        } else {
            batch.draw(playButtonNormal, 340, 100, playButtonNormal.getWidth() * 2, playButtonNormal.getHeight() * 2);
        }

        // Draw "Exit" button based on its state
        if (isExitButtonHovered) {
            if (isExitButtonClicked) {
                batch.draw(exitButtonPressed, 340, 50, exitButtonNormal.getWidth() * 2, exitButtonNormal.getHeight() * 2);
            } else {
                batch.draw(exitButtonHover, 340, 50, exitButtonNormal.getWidth() * 2, exitButtonNormal.getHeight() * 2);
            }
        } else {
            batch.draw(exitButtonNormal, 340, 50, exitButtonNormal.getWidth() * 2, exitButtonNormal.getHeight() * 2);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();
        float imageWidth = texture.getWidth();
        float imageHeight = texture.getHeight();

        // Calculate scale factor to maintain aspect ratio
        scale = Math.min(screenWidth / imageWidth, screenHeight / imageHeight);

        // Calculate position to center the image
        x = (screenWidth - imageWidth * scale) / 2;
        y = (screenHeight - imageHeight * scale) / 2;

        // Adjust camera position
        camera.position.set(screenWidth / 2, screenHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        texture.dispose();
        batch.dispose();
    }
}
