package com.exgames.xenos.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.exgames.xenos.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.vSyncEnabled = true;
		config.width = 1280;
		config.height = 768;
		config.vSyncEnabled = true;
		new LwjglApplication(new Main(), config);
	}
}
