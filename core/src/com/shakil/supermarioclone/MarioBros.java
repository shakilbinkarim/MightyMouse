package com.shakil.supermarioclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shakil.supermarioclone.Screens.LoadingScreen;
import com.shakil.supermarioclone.Screens.PlayScreen;
import com.shakil.supermarioclone.Screens.SplashScreen;

public class MarioBros extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_Height = 208;
	public static final float SCALE_TO_METER = 0.01f;

	public SpriteBatch spriteBatch;
	public static AssetManager assetManager;

	@Override
	public void create () {
		assetManager = new AssetManager();
		spriteBatch = new SpriteBatch();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		assetManager.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}
