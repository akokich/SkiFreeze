package com.akx2.skifreeze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.akx2.skifreeze.SkiFreeze;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1080/2;
        config.height = 1920/2;
        config.vSyncEnabled = false; // Setting to false disables vertical sync
        config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
        config.backgroundFPS = 0; // Setting to 0 disables background fps throttling
		new LwjglApplication(new SkiFreeze(), config);
	}
}
