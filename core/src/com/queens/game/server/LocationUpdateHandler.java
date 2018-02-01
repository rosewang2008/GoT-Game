package com.queens.game.server;


import com.google.gson.GsonBuilder;
import com.queens.game.networking.LocationUpdateRequest;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 1/25/18.
 */
public class LocationUpdateHandler implements Runnable{
    private LocationUpdateRequest update;
    private OutputStreamWriter out;

    public LocationUpdateHandler(LocationUpdateRequest message, OutputStreamWriter out, GsonBuilder builder){
        this.update = message;
        this.out = out;
    }

    @Override
    public void run() {
        GameManager.updatePlayerLocation(update.getPlayerId(), update.getNewX(), update.getNewY());
    }
}
