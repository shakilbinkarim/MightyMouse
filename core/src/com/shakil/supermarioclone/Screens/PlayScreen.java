package com.shakil.supermarioclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shakil.supermarioclone.MarioBros;
import com.shakil.supermarioclone.Scenes.Hud;
import com.shakil.supermarioclone.Sprites.AngryBird;
import com.shakil.supermarioclone.Sprites.Mario;
import com.shakil.supermarioclone.Sprites.NyanCat;


/**
 * Created by Shakil on 17-Mar-16.
 */
public class PlayScreen implements Screen {

    private MarioBros game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;

    //Tiled Map Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Bodies & Fixtures
    BodyDef bodyDef;
    PolygonShape shape;
    FixtureDef fixtureDef;
    Body body;

    //Box2d variables
    public World world;
    //private Box2DDebugRenderer debugRenderer;

    //Mario
    private Mario mario;

    //AngryBird
    private Array<AngryBird> angryBirdArray;
    private Array<NyanCat> nyanCatArray;
    private int points;
    private boolean gameOver;

    private Music music;

    public PlayScreen(MarioBros game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(MarioBros.V_WIDTH * MarioBros.SCALE_TO_METER,
                MarioBros.V_Height * MarioBros.SCALE_TO_METER, camera);
        hud = new Hud(game.spriteBatch);
        points = 0;
        startMusic();
        setupTileMap();
        setupBox2d();
        takeCareOfObjectsInTileMap();
        defineSprites();
        createCollisionListener();
        gameOver = false;
    }

    private void startMusic() {
        music = MarioBros.assetManager.get("MusicBg.wav", Music.class);
        music.setLooping(true);
        music.play();
    }

    private void defineSprites() {
        mario = new Mario(this);

        float angryX, angryY;
        angryBirdArray = new Array<AngryBird>();
        for (int i = 0; i < 4; i++){
            angryBirdArray.add(new AngryBird(this));
            angryX = (mario.body.getPosition().x + (MarioBros.V_WIDTH / 2)) * MarioBros.SCALE_TO_METER;
            angryY = (float)Math.random() + 0.35f;
            angryBirdArray.get(i).defineAngryBird(angryX, angryY);
            angryBirdArray.get(i).setVelocity((float) Math.random() + 0.55f);
            angryBirdArray.get(i).setAcceleration((float)Math.abs(Math.random() - 0.45f));
        }

        nyanCatArray = new Array<NyanCat>();
        for (int i = 0; i < 2; i++){
            angryX = (mario.body.getPosition().x + (MarioBros.V_WIDTH / 2)) * MarioBros.SCALE_TO_METER;
            angryY = (float)Math.random() + 0.35f;
            nyanCatArray.add(new NyanCat(this));
            nyanCatArray.get(i).defineAngryBird(angryX, angryY);
            nyanCatArray.get(i).setVelocity((float) Math.random() + 0.55f);
            nyanCatArray.get(i).setAcceleration((float)Math.abs(Math.random() - 0.45f));
        }
    }

    private void setupBox2d() {
        world = new World(new Vector2(0, -10), true);
        //debugRenderer = new Box2DDebugRenderer();
    }

    private void setupTileMap() {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, MarioBros.SCALE_TO_METER);
        camera.position.set((viewport.getWorldWidth() / 2.0f), (viewport.getWorldHeight() / 2.0f), 0);
    }

    private void takeCareOfObjectsInTileMap() {
        bodyDef = new BodyDef();
        shape = new PolygonShape();
        fixtureDef = new FixtureDef();
        createBodyAndFixture(3);
    }

    private void createBodyAndFixture(int i) {
        for(MapObject mapObject : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) * MarioBros.SCALE_TO_METER,
                    (rectangle.getY() + rectangle.getHeight() / 2) * MarioBros.SCALE_TO_METER);
            body = world.createBody(bodyDef);

            shape.setAsBox((rectangle.getWidth() / 2) * MarioBros.SCALE_TO_METER,
                    (rectangle.getHeight() / 2) * MarioBros.SCALE_TO_METER);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }

    @Override
    public void show() {

    }

    public void update(float deltaTime){
        //TODO: get rid of print
        handleInput(deltaTime);
        handleSpriteMotion();
        world.step(1.0f / 60.0f, 6, 2);
        mario.update(deltaTime);
        for (int i = 0; i < angryBirdArray.size; i++){
            angryBirdArray.get(i).update(deltaTime);
        }
        for (int i = 0; i < nyanCatArray.size; i++){
            nyanCatArray.get(i).update(deltaTime);
        }
        checkMarioPosition();
        checkAngryPosition();
        checkRainbowPosition();
//        System.out.println(mario.body.getPosition().y);
        camera.position.x = mario.body.getPosition().x;
        hud.update(deltaTime);
        camera.update();
        renderer.setView(camera);
    }

    private void checkRainbowPosition() {
        //TODO: complete rainbow
        for(int i = 0; i < nyanCatArray.size; i++){
            if(!camera.frustum.pointInFrustum(
                    new Vector3(nyanCatArray.get(i).body.getPosition().x,
                            nyanCatArray.get(i).body.getPosition().y, 0))){
                float rainbowY = (float)Math.random() + 0.35f;
                float rainbowX = camera.position.x + camera.viewportWidth / 2;
                nyanCatArray.get(i).repositionAngry(rainbowX, rainbowY);
            }
        }
    }

    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {
            //TODO: fix collision detection
            @Override
            public void beginContact(Contact contact) {
                try {
                    Fixture fixtureA = contact.getFixtureA();
                    Fixture fixtureB = contact.getFixtureB();

                    if(fixtureA.getType().toString().equals("Circle")){
                        System.out.println(fixtureB.getUserData().toString());
                        if(fixtureB.getUserData().toString().equals("Angry")&&
                                fixtureA.getUserData().toString().equals("Mighty")){
                            MarioBros.assetManager.get("hit.wav", Sound.class).play();
                            points++;
                            hud.updateScore(points);
                            System.out.println(points);
                        }
                    }
                    if(fixtureB.getUserData().toString().equals("Rainbow") &&
                            fixtureA.getUserData().toString().equals("Mighty")){
                        //TODO: kill Mighty + remove prints
                        gameOver = true;
                    }
                } catch (Exception e){

                }

            }

            @Override
            public void endContact(Contact contact) {
//                try {
//                    Fixture fixtureA = contact.getFixtureA();
//                    Fixture fixtureB = contact.getFixtureB();
//
//                } catch (Exception e) {
//                    System.out.println("Exception");
//                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
    }

    private void checkAngryPosition() {
        for(int i = 0; i < angryBirdArray.size; i++){
            if(!camera.frustum.pointInFrustum(
                    new Vector3(angryBirdArray.get(i).body.getPosition().x,
                            angryBirdArray.get(i).body.getPosition().y, 0))){
                float angryY = (float)Math.random() + 0.35f;
                float angryX = camera.position.x + camera.viewportWidth / 2;
                angryBirdArray.get(i).repositionAngry(angryX, angryY);
            }
        }
    }

    private void handleSpriteMotion() {
        for(int i = 0; i < angryBirdArray.size; i++){
            if(angryBirdArray.get(i).body.getLinearVelocity().x >= -(angryBirdArray.get(i).getVelocity()))
                angryBirdArray.get(i).body.applyLinearImpulse(new Vector2(-angryBirdArray.get(i).getAcceleration(), 0.0f), mario.body.getWorldCenter(), true);
        }
        for(int i = 0; i < nyanCatArray.size; i++){
            if(nyanCatArray.get(i).body.getLinearVelocity().x >= -(nyanCatArray.get(i).getVelocity()))
                nyanCatArray.get(i).body.applyLinearImpulse(new Vector2(-nyanCatArray.get(i).getAcceleration(), 0.0f), mario.body.getWorldCenter(), true);
        }
        if (mario.body.getLinearVelocity().x <= 1) {
            mario.body.applyLinearImpulse(new Vector2(0.1f, 0.0f), mario.body.getWorldCenter(), true);
        }
    }

    private void checkMarioPosition() {
        if (mario.body.getPosition().x > 33.2f){
            mario.body.setTransform(3.0f, mario.body.getPosition().y, 0);
        }
    }

    private void handleInput(float deltaTime) {
        if(Gdx.input.justTouched()) {
            mario.body.applyLinearImpulse(new Vector2(0.0f, 2.2f), mario.body.getWorldCenter(), true);
            MarioBros.assetManager.get("jump.wav", Sound.class).play();
        }
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //debugRenderer.render(world, camera.combined);
        camera.update();

        //draw mouse
        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();
        mario.draw(game.spriteBatch);
        for(int i = 0; i < angryBirdArray.size; i++){
            angryBirdArray.get(i).draw(game.spriteBatch);
        }
        for(int i = 0; i < nyanCatArray.size; i++){
            nyanCatArray.get(i).draw(game.spriteBatch);
        }
        game.spriteBatch.end();

        game.spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver){
            //TODO: gameover
            music.stop();
            MarioBros.assetManager.get("Gameover.wav", Sound.class).play();
            Preferences preferences = Gdx.app.getPreferences("mine");
            int previousHighestScore = preferences.getInteger("highscore");
            preferences.putBoolean("beat", false);
            System.out.println("Previous Highest Score ========================== " + previousHighestScore);
            if(previousHighestScore < points){
                preferences.putInteger("highscore", points);
                preferences.putBoolean("beat", true);
            }
            preferences.putInteger("score", points);
            preferences.flush();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        //TODO: dispose
        hud.dispose();
    }
}
