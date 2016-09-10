package com.shakil.supermarioclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shakil.supermarioclone.MarioBros;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MarioBros.V_WIDTH;
		config.height = MarioBros.V_Height;
		new LwjglApplication(new MarioBros(), config);
	}
}
