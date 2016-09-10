package com.shakil.supermarioclone.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shakil.supermarioclone.MarioBros;

/**
 * Created by Shakil on 27-Mar-16.
 */
public class GameOverScreen implements Screen {

    private final int score;
    private final boolean beat;
    private Viewport viewport;
    private Stage stage;

    private Game game;
    int keeper;

    public GameOverScreen(Game game){
        keeper = 0;
        Preferences preferences = Gdx.app.getPreferences("mine");
        score = preferences.getInteger("score");
        beat = preferences.getBoolean("beat");
        this.game = game;
        viewport = new StretchViewport(MarioBros.V_WIDTH, MarioBros.V_Height,
                new OrthographicCamera());
        stage = new Stage(viewport, ((MarioBros) game).spriteBatch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("myFont.fnt"), false), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label gameOverLabel = new Label("GAME OVER ", font);
        Label beatLabel = new Label("Congratulations! :) Highest Score", font);
        Label scoreLabel = new Label("Your Score: " + score, font);
        Label playAgainLabel = new Label("Tap to Play Again", font);

        table.add(gameOverLabel).expandX();
        table.row();
        if(beat){
            table.add(beatLabel).expandX().padTop(10f);
            table.row();
        }
        table.add(scoreLabel).expandX().padTop(10f);
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        keeper++;
        if(Gdx.input.justTouched() && keeper > 30) {
            game.setScreen(new MenuScreen((MarioBros) game));
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
