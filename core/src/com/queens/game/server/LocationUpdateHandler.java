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
        GameManager.Location oldLocation = new GameManager.Location(update.getOldX(), update.getOldY());
        GameManager.Location newLocation = new GameManager.Location(update.getNewX(), update.getNewY());
        GameManager.updatePlayerLocation(update.getPlayerId(), oldLocation, newLocation);

    }
}
