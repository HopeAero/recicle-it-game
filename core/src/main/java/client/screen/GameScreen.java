package client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen extends AbstractScreen {
    public static final float SPEED = 120;

    Texture background;
    float x;
    float y;
    private Animation<TextureRegion> animation;
    private SpriteBatch batch;
    private float stateTime = 0;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        try {
            Texture spriteSheet = new Texture(Gdx.files.internal("sprites/character/Adam_idle_16x16.png"));
            TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 16, 16);

            TextureRegion[] frames = new TextureRegion[4];
            int index = 0;
            for (int i = 0; i < 4; i++) {
                frames[index++] = tmp[0][i];
            }
            animation = new Animation<TextureRegion>(0.25f, frames);
            x = 0;
            y = 0;
            batch = new SpriteBatch();
            stateTime = 0f;
        }
        catch (Exception e) {
            System.err.println("Error al ejecutar el juego: " + e.getMessage());
        }
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.begin();
        batch.draw(currentFrame, x, y); // Draw the current frame at the updated position
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
