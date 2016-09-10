package com.shakil.supermarioclone.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;


/**
 * Created by Shakil on 27-Mar-16.
 */
public class SpriteAccessor implements TweenAccessor<Sprite>{

    public static final int ALPHA = 0;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType){
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, target.getColor().a);
                break;
            default:
                assert false;
        }
    }
}
