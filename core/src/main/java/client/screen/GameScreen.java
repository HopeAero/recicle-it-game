package client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen extends AbstractScreen {
    public static final float SPEED = 120;

    float x;
    float y;
    private SpriteBatch batch;
    private Texture img;
    private TextureRegion[] spriteRegions;
    private int currentSpriteIndex;


    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("assets/sprites/character/Adam_idle_16x16.png");

        // Suponiendo que tienes un sprite sheet de 16x16 y quieres dividirlo en 4 sprites
        int spriteWidth = 16;
        int spriteHeight = 32;
        int totalSprites = 4; // Cambia esto según cuántos sprites hay en tu sprite sheet

        spriteRegions = new TextureRegion[totalSprites];
        for (int i = 0; i < totalSprites; i++) {
            spriteRegions[i] = new TextureRegion(img, (i * spriteWidth), 0, spriteWidth, spriteHeight);
        }
        currentSpriteIndex = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();
            currentSpriteIndex = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED * Gdx.graphics.getDeltaTime();
            currentSpriteIndex = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += SPEED * Gdx.graphics.getDeltaTime();
            currentSpriteIndex = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= SPEED * Gdx.graphics.getDeltaTime();
            currentSpriteIndex = 3;
        }

        batch.begin();
        // Dibuja el primer sprite (puedes cambiar el índice según la lógica de animación)
        batch.draw(spriteRegions[currentSpriteIndex], x, y);
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
        img.dispose(); // Asegúrate de liberar la textura también
    }
}
