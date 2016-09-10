package com.shakil.supermarioclone.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.shakil.supermarioclone.MarioBros;
import com.shakil.supermarioclone.Screens.PlayScreen;

/**
 * Created by Shakil on 27-Mar-16.
 */
public class NyanCat extends Sprite{
    public World world;
    public Body body;
    BodyDef bodyDef;

    private Animation rainbowAnimation;
    private int frameCount;
    private float velocity;

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    private float acceleration;

    public NyanCat(PlayScreen playScreen) {
        super(new TextureRegion(MarioBros.assetManager.get("nyanCat.png", Texture.class), 0, 0, 512, 32));
        this.world = playScreen.world;
        setUpAnimation();
        setBounds(0.0f, 0.0f, 64 * MarioBros.SCALE_TO_METER, 32 * MarioBros.SCALE_TO_METER);
    }

    private void setUpAnimation() {
        frameCount = 1;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 0, 0, 64, 32));
        for(int i = 1; i < 8; i++)
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 32));
        rainbowAnimation = new Animation(6.0f, frames);
        frames.clear();
    }

    @Override
    public String toString() {
        return "Rainbow";
    }

    public void defineAngryBird(float x, float y) {
        bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0.0f;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13.0f * MarioBros.SCALE_TO_METER);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        body.getFixtureList().get(0).setUserData(this.toString());
        shape.dispose();
    }

    public void update(float deltaTime){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(frameCount));
        frameCount = (frameCount + 1) % 8;
    }

    private TextureRegion getFrame(float frameCount) {
        TextureRegion textureRegion = rainbowAnimation.getKeyFrame(frameCount, false);
        return textureRegion;
    }

    public void repositionAngry(float angryX, float angryY) {
        body.setTransform(angryX, angryY, 0);
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }
}
