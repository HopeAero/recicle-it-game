package io.game.recicleIt;

import client.screen.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private ScreenManager screenManager;
    Music music;

    @Override
    public void create() {
        try {
            //Instancias
            screenManager = ScreenManager.getInstance(this);

            //Configuracion y reproduccion de la musica
            music = Gdx.audio.newMusic(Gdx.files.internal("musica.mp3"));
            music.setLooping(true);
            music.setVolume(0.5f);
            music.play();
            // Establece la pantalla de inicio
            screenManager.showGameScreen();
        } catch (Exception e) {
            System.err.println("Error al ejecutar el juego: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

