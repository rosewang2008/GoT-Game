package com.queens.game.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.queens.game.networking.Direction;

import java.util.Arrays;

/**
 * Created by aditisri on 1/23/18.
 */
public class AnimationFactory {
    public static int NUM_SPRITES_HORIZONTAL = 13;
    public static int NUM_SPRITES_VERTICAL = 21;
    private static Texture getSpriteSheet() {
        return new Texture("core/assets/sprites/girl_sprite.png");
    }

    public static float getAnimationHeight(){
        return getSpriteSheet().getHeight()/NUM_SPRITES_VERTICAL;
    }

    public static float getAnimationWidth(){
        return getSpriteSheet().getWidth()/NUM_SPRITES_HORIZONTAL;
    }

    public static Animation getAnimation(Direction d){
        Texture animations = getSpriteSheet();
        TextureRegion[][] tmp = TextureRegion.split(animations,
                animations.getWidth() / NUM_SPRITES_HORIZONTAL,
                animations.getHeight() / NUM_SPRITES_VERTICAL);
        TextureRegion[] relevantFrames = null;
        switch(d){
            case UP:
                relevantFrames = Arrays.copyOfRange(tmp[8], 0, 9);
                break;
            case DOWN:
                relevantFrames = Arrays.copyOfRange(tmp[10], 0, 9);
                break;
            case LEFT:
                relevantFrames = Arrays.copyOfRange(tmp[9], 0, 9);
                break;
            case RIGHT:
                relevantFrames = Arrays.copyOfRange(tmp[11], 0, 9);
                break;
        }
        return new Animation<TextureRegion>(0.1f, relevantFrames);
    }
}
