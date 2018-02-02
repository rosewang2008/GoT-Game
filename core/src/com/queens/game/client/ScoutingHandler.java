package com.queens.game.client;

import com.queens.game.networking.ScoutingResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditisri on 1/28/18.
 */
public class ScoutingHandler implements Runnable{
    private ScoutingResponse res;
    private QueensGame game;
    public ScoutingHandler(ScoutingResponse res, QueensGame game){
        this.res = res;
        this.game = game;
    }

    @Override
    public void run() {
        handle();

    }

    public void handle(){
        game.setOtherPlayers(res.getxLocations(), res.getyLocations(), res.getEnvironments());
    }

}
