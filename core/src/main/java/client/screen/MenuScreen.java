// MenuScreen.java
package client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends AbstractScreen {
    private Texture texture;
    private SpriteBatch batch;
    private float x, y, scale;
    private OrthographicCamera camera;
    private Viewport viewport;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        texture = new Texture(Gdx.files.internal("assets/menu.png"));
        batch = new SpriteBatch();

        // Configura la cámara y el viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 500, camera); // Ajusta esto a la resolución de tu imagen
        viewport.apply();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualiza la cámara
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texture, x, y, texture.getWidth() * scale, texture.getHeight() * scale);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();
        float imageWidth = texture.getWidth();
        float imageHeight = texture.getHeight();

        // Calcula el factor de escala para mantener la relación de aspecto
        scale = Math.min(screenWidth / imageWidth, screenHeight / imageHeight);

        // Calcula la posición para centrar la imagen
        x = (screenWidth - imageWidth * scale) / 2;
        y = (screenHeight - imageHeight * scale) / 2;

        // Ajusta la posición de la cámara
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
