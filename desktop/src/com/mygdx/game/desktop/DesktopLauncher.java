package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Fruit;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	      cfg.title = "Fruit";
	      cfg.width = 800;
	      cfg.height = 520;
	      new LwjglApplication(new Fruit(), cfg);
	}
}
