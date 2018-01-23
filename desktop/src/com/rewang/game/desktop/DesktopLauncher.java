package com.rewang.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rewang.game.GoT;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Game of Thrones";
		config.height = 100;
		config.width = 100;
		new LwjglApplication(new GoT(), config);
	}
}
