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
 * Created by Shakil on 18-Mar-16.
 */
public class Mario extends Sprite{

    public World world;
    public Body body;

    private Animation mightyAnimation;
    private int frameCount;

    public Mario(PlayScreen playScreen) {
        super(new TextureRegion(MarioBros.assetManager.get("mighty.png", Texture.class), 0, 0, 2304, 128));
        this.world = playScreen.world;
        setUpAnimation();
        defineMario(33.0f, 32 * MarioBros.SCALE_TO_METER);
        setBounds(0.0f, 0.0f, 42 * MarioBros.SCALE_TO_METER, 21 * MarioBros.SCALE_TO_METER);
    }

    @Override
    public String toString() {
        return "Mighty";
    }

    private void setUpAnimation() {
        frameCount = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 10; i++)
            frames.add(new TextureRegion(getTexture(), i * 256, 0, 256, 128));
        mightyAnimation = new Animation(2.0f, frames);
        frames.clear();
    }

    public void defineMario(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
        frameCount = (frameCount + 1) % 9;
    }

    private TextureRegion getFrame(float deltaTime) {
        TextureRegion textureRegion = mightyAnimation.getKeyFrame(deltaTime, true);
        return textureRegion;
    }

}
