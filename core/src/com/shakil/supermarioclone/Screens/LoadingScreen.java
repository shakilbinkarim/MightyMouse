package com.shakil.supermarioclone.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.shakil.supermarioclone.MarioBros;

/**
 * Created by Shakil on 27-Mar-16.
 */
public class LoadingScreen implements Screen {

    private MarioBros game;
    private Sprite backgroundSprite;
    private Sprite progressBar;
    private float progress;

    public LoadingScreen(Game game) {
        this.game = (MarioBros)game;
        backgroundSprite = new Sprite(new Texture("loadingBg.png"));
        progressBar = new Sprite(new Texture("progressbar.png"));
        progressBar.setPosition(32.0f, Gdx.graphics.getHeight() / 4.0f);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        queueAssets();
        this.progress = 0.0f;
    }

    private void queueAssets() {
        game.assetManager.load("nyanCat.png", Texture.class);
        game.assetManager.load("menu.png", Texture.class);
        game.assetManager.load("angry.png", Texture.class);
        game.assetManager.load("mighty.png", Texture.class);
        game.assetManager.load("tileset_gutter.png", Texture.class);
        game.assetManager.load("MusicBg.wav", Music.class);
        game.assetManager.load("happy.wav", Music.class);
        game.assetManager.load("Gameover.wav", Sound.class);
        game.assetManager.load("hit.wav", Sound.class);
        game.assetManager.load("jump.wav", Sound.class);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        game.spriteBatch.begin();
        backgroundSprite.draw(game.spriteBatch);
        progressBar.setSize((Gdx.graphics.getWidth() - 32) * progress, 32.0f);
        progressBar.draw(game.spriteBatch);
        game.spriteBatch.end();
    }

    private void update(float delta) {
        progress = game.assetManager.getProgress();
        if(game.assetManager.update()){
            game.setScreen(new MenuScreenO((MarioBros) game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
