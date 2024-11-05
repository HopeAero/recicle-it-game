package client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends AbstractScreen {
    public static final float SPEED = 120;

    float x;
    float y;
    private SpriteBatch batch;
    private Texture img;
    private TextureRegion[] spriteRegions;
    private int currentSpriteIndex;
    private OrthographicCamera camera;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private List<Rectangle> collisionRectangles;
    private Rectangle playerRectangle;
    private ShapeRenderer shapeRenderer;
    private ScreenViewport viewport;


    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("assets/sprites/character/Adam_idle_16x16.png");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        shapeRenderer = new ShapeRenderer();
        viewport = new ScreenViewport(camera);

        // Load the map
        map = new TmxMapLoader().load("recicleIt.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        collisionRectangles = new ArrayList<>();

        // Assuming you have a sprite sheet of 16x16 and want to divide it into 4 sprites
        int spriteWidth = 16;
        int spriteHeight = 32;
        int totalSprites = 4;

        // Load the object layer containing collisions
        for (MapObject object : map.getLayers().get("Capa de Objetos 1").getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle rect = rectangleObject.getRectangle();
                collisionRectangles.add(new Rectangle(rect.x, rect.y, rect.width, rect.height));
            }
        }

        spriteRegions = new TextureRegion[totalSprites];
        for (int i = 0; i < totalSprites; i++) {
            spriteRegions[i] = new TextureRegion(img, (i * spriteWidth), 0, spriteWidth, spriteHeight);
        }

        currentSpriteIndex = 0;

        // Initialize player's position
        x = 100; // Set this to the desired starting x position
        y = 100; // Set this to the desired starting y position

        playerRectangle = new Rectangle(x, y, spriteWidth, spriteHeight - 6);


        // Centra la cámara en el medio del mapa
        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Move the player (optional)
        // Puedes comentar o eliminar esta parte si no deseas mover al jugador
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

        // Update player rectangle position
        playerRectangle.setPosition(x, y);

        // Draw the map
        mapRenderer.setView(camera);
        viewport.apply();
        mapRenderer.render();

        // Check for collisions
        for (Rectangle collisionRect : collisionRectangles) {
            if (playerRectangle.overlaps(collisionRect)) {
                // Resolve collision
                resolveCollision(playerRectangle, collisionRect);
            }
        }

        // Draw the player
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(spriteRegions[currentSpriteIndex], playerRectangle.x, playerRectangle.y);
        batch.end();

        // Draw collision rectangles for debugging (optional)
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color for collisions
        shapeRenderer.rect(playerRectangle.x, playerRectangle.y, playerRectangle.width, playerRectangle.height); // Draw player collision rectangle
        for (Rectangle collisionRect : collisionRectangles) {
            shapeRenderer.rect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
        }
        shapeRenderer.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        map.dispose(); // Asegúrate de liberar el mapa
        img.dispose(); // Asegúrate de liberar la textura también
        shapeRenderer.dispose();
    }

    private void resolveCollision(Rectangle player, Rectangle collision) {
        // Calcular el solapamiento en ambos ejes
        float overlapX = Math.min(player.x + player.width - collision.x,
            collision.x + collision.width - player.x);
        float overlapY = Math.min(player.y + player.height - collision.y,
            collision.y + collision.height - player.y);

        // Determinar cuál solapamiento es menor
        if (overlapX < overlapY) {
            // Colisión en el eje X
            if (player.x < collision.x) {
                player.x -= overlapX; // Mover a la izquierda
            } else {
                player.x += overlapX; // Mover a la derecha
            }
        } else {
            // Colisión en el eje Y
            if (player.y < collision.y) {
                player.y -= overlapY; // Mover hacia abajo
            } else {
                player.y += overlapY; // Mover hacia arriba
            }
        }

        // Actualiza la posición de x e y después de la colisión
        x = player.x;
        y = player.y;
    }
}
