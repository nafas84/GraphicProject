package io.github.Graphic.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.Graphic.TillDown;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new TillDown(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(144);
        config.useVsync(true);
        //config.setWindowIcon("icon.png");
        config.setTitle("20 Minutes Till Dawn");
        config.setWindowedMode(1920, 1080);
        config.setResizable(false);
        //TillDown.fileChooser = new DesktopFileChooser();
        //AtomicBomber.fileChooserConfiguration = new NativeFileChooserConfiguration();
        return config;
    }
}
