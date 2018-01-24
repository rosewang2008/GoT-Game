package com.queens.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by aditisri on 1/24/18.
 */
public class PlayerInputProcessor implements InputProcessor{
    private Player player;

    public PlayerInputProcessor(Player p){
        this.player = p;
    }

    @Override
    public boolean keyDown(int keycode) {
//        setKeyPressed(keycode, true);
        Direction d = null;
        switch (keycode) {
            case Input.Keys.LEFT:
                d = Direction.LEFT;
                break;
            case Input.Keys.RIGHT:
                d = Direction.RIGHT;
                break;
            case Input.Keys.UP:
                d = Direction.UP;
                break;
            case Input.Keys.DOWN:
                d = Direction.DOWN;
                break;
        }
        player.move(d);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
//        setKeyPressed(keycode, false);
        return false;
    }

//    public boolean getKeyPressed(int keycode){
//        keyPressLock.lock();
//        boolean isPressed = keysPressed.contains(keycode);
//        keyPressLock.unlock();
//        return isPressed;
//    }

//    public void setKeyPressed(int keycode, boolean isPressed){
//        keyPressLock.lock();
//        if(isPressed) {
//            keysPressed.add(keycode);
//        }
//        else{
//            keysPressed.remove(keycode);
//        }
//        keyPressLock.unlock();
//
//    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
