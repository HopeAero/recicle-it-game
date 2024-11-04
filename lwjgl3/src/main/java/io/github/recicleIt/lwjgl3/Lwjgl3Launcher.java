package io.github.recicleIt.lwjgl3;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.game.recicleIt.Main;

/** Lanza la aplicación de escritorio (LWJGL3). */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // Esto maneja el soporte para macOS y ayuda en Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Recicle it");
        //// Vsync limita los fotogramas por segundo a lo que tu hardware puede mostrar y ayuda a eliminar
        //// el desgarro de pantalla. Esta configuración no siempre funciona en Linux, por lo que la línea siguiente es una salvaguarda.
        configuration.useVsync(true);
        //// Limita los FPS a la tasa de refresco del monitor actualmente activo, más 1 para intentar igualar las tasas de refresco fraccionarias.
        //// La configuración de Vsync anterior debería limitar los FPS reales para que coincidan con el monitor.
        configuration.setForegroundFPS(60);//// Si eliminas la línea anterior y configuras Vsync en falso, puedes obtener FPS ilimitados, lo cual puede ser
        //// útil para probar el rendimiento, pero también puede ser muy estresante para algunos hardware.
        //// También puede ser necesario configurar los controladores de la GPU para deshabilitar completamente Vsync; esto puede causar desgarro de pantalla.
        configuration.setWindowedMode(800, 500); // esta línea cambia el tamaño de la ventana
        //// Puedes cambiar estos archivos; están en lwjgl3/src/main/resources/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
