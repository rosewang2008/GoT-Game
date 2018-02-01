package com.queens.game.client;

import com.badlogic.gdx.Gdx;
import com.queens.game.networking.EnvironmentSwitchResponse;

/**
 * Created by aditisri on 2/1/18.
 */
public class EnvironmentSwitchHandler implements Runnable{
    private EnvironmentSwitchResponse res;
    private QueensGame game;
    public EnvironmentSwitchHandler(EnvironmentSwitchResponse res, QueensGame game) {
        this.res = res;
        this.game = game;
    }

    @Override
    public void run() {
        handle();

    }

    public void handle(){
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.switchEnvironment(res.getEnvironment(), res.getX(), res.getY());
                game.resumePlay();
            }
        });
    }
}
