package com.shakil.supermarioclone.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.shakil.supermarioclone.MarioBros;
import com.shakil.supermarioclone.Screens.PlayScreen;

/**
 * Created by Shakil on 26-Mar-16.
 */
public class AngryBird extends Sprite{

    public World world;
    public Body body;
    BodyDef bodyDef;

    private Animation angryAnimation;
    private int frameCount;
    private float velocity;

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    private float acceleration;

    public AngryBird(PlayScreen playScreen) {
        super(new TextureRegion(MarioBros.assetManager.get("angry.png", Texture.class), 0, 0, 3584, 256));
        this.world = playScreen.world;
        setUpAnimation();
//        defineAngryBird(34.5f, 32 * MarioBros.SCALE_TO_METER);
        setBounds(0.0f, 0.0f, 32 * MarioBros.SCALE_TO_METER, 32 * MarioBros.SCALE_TO_METER);
    }

    private void setUpAnimation() {
        frameCount = 1;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 0, 0, 256, 256));
        for(int i = 1; i < 14; i++)
            frames.add(new TextureRegion(getTexture(), i * 256, 0, 256, 256));
        angryAnimation = new Animation(7.0f, frames);
        frames.clear();
    }

    @Override
    public String toString() {
        return "Angry";
    }

    public void defineAngryBird(float x, float y) {
        bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0.0f;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(11.0f * MarioBros.SCALE_TO_METER);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        body.getFixtureList().get(0).setUserData(this.toString());
        shape.dispose();
    }

    public void update(float deltaTime){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(frameCount));
        frameCount = (frameCount + 1) % 14;
    }

    private TextureRegion getFrame(float frameCount) {
        TextureRegion textureRegion = angryAnimation.getKeyFrame(frameCount, false);
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
