package com.shakil.supermarioclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.shakil.supermarioclone.MarioBros;

/**
 * Created by Shakil on 27-Mar-16.
 */
public class MenuScreenO implements Screen{

    private final Music music;
    private Texture menuSprite;
    private MarioBros game;

    public MenuScreenO(MarioBros game) {
        this.game = game;
        menuSprite = MarioBros.assetManager.get("menu.png", Texture.class);
        music = MarioBros.assetManager.get("happy.wav", Music.class);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.spriteBatch.begin();
        game.spriteBatch.draw(menuSprite, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.spriteBatch.end();
        if(Gdx.input.justTouched()) {
            music.stop();
            game.setScreen(new PlayScreen((MarioBros) game));
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
