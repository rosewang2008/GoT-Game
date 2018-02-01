package com.queens.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.queens.game.networking.NewPlayerResponse;

/**
 * Created by aditisri on 1/28/18.
 */
public class NewPlayerHandler implements Runnable{
    private NewPlayerResponse res;
    private QueensGame game;

    public NewPlayerHandler(NewPlayerResponse res, QueensGame game){
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
                Player newPlayer = new Player(res.getNewPlayerId(),320, 224, game);
                game.setPlayer(newPlayer);
                game.setPlayerInputHandler(new PlayerInputProcessor(newPlayer));
                game.start();
            }
        });

    }
}
