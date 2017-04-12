package com.exgames.xenos.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.exgames.xenos.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = true;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		config.width = 1240;
		config.height = 720;
		new LwjglApplication(new Main(), config);
	}
}
