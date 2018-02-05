package com.queens.game.server;


import com.google.gson.GsonBuilder;
import com.queens.game.networking.LocationUpdateRequest;
import com.queens.game.networking.LocationUpdateResponse;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 1/25/18.
 */
public class LocationUpdateHandler implements Runnable{
    private LocationUpdateRequest update;
    private OutputStreamWriter out;
    private Server server;

    public LocationUpdateHandler(LocationUpdateRequest message, Server s, OutputStreamWriter out){
        this.update = message;
        this.out = out;
        this.server = s;
    }

    @Override
    public void run() {
        GameManager.updatePlayerLocation(update.getPlayerId(), update.getNewX(), update.getNewY());
        this.server.sendMessageToClient(new LocationUpdateResponse(update.getId()), this.out);
    }
}
