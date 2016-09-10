package com.shakil.supermarioclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shakil.supermarioclone.MarioBros;
import com.shakil.supermarioclone.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Shakil on 27-Mar-16.
 */
public class SplashScreen implements Screen{

    private Sprite splashSprite;
    private MarioBros game;
    private TweenManager tweenManager;

    public SplashScreen(MarioBros game) {
        this.game = game;
    }

    @Override
    public void show() {
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Texture texture = new Texture("SplashScreen.png");
        splashSprite = new Sprite(texture);
        splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,0.5f).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        game.setScreen(new LoadingScreen((MarioBros) game));
                        dispose();
                    }
                }
        ).start(tweenManager);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tweenManager.update(delta);
        game.spriteBatch.begin();
        splashSprite.draw(game.spriteBatch);
        game.spriteBatch.end();
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
        splashSprite.getTexture().dispose();
    }
}
