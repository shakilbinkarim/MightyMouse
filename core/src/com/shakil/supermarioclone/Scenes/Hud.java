package com.shakil.supermarioclone.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shakil.supermarioclone.MarioBros;
import com.sun.media.jfxmediaimpl.MediaDisposer;


/**
 * Created by Shakil on 17-Mar-16.
 */
public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    public Integer worldTimer;
    private float timeCount;
    public Integer score;

    private Table table;
    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;

    BitmapFont bitmapFont;

    public Hud(SpriteBatch spriteBatch){
        worldTimer = 0;
        timeCount = 0;
        score = 0;
        viewport = new StretchViewport(MarioBros.V_WIDTH, MarioBros.V_Height, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        bitmapFont = new BitmapFont(Gdx.files.internal("myFont.fnt"), false);
        setupTableAndLabels();

    }

    private void setupTableAndLabels() {
        table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(bitmapFont, Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(bitmapFont, Color.WHITE));
        timeLabel = new Label("TIME:", new Label.LabelStyle(bitmapFont, Color.WHITE));
        levelLabel = new Label("", new Label.LabelStyle(bitmapFont, Color.WHITE));
        worldLabel = new Label("", new Label.LabelStyle(bitmapFont, Color.WHITE));
        marioLabel = new Label("HITS", new Label.LabelStyle(bitmapFont, Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

    }

    public void update(float deltaTime){
        timeCount += deltaTime;
        if(timeCount >= 1){
            worldTimer++;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

    }

    public void updateScore(int score){
        scoreLabel.setText(String.format("%06d", score));

    }

    public void dispose() {
        stage.dispose();
    }
}
