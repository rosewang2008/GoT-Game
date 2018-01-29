package com.queens.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.queens.game.client.Client;
import com.queens.game.client.QueensGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		final QueensGame game = new QueensGame();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Client.start(game);
			}
		});
		t.start();
		new LwjglApplication(game, config);
	}
}
