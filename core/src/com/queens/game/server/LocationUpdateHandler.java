package com.queens.game.server;


import com.queens.game.networking.LocationUpdateRequest;

/**
 * Created by aditisri on 1/25/18.
 */
public class LocationUpdateHandler implements Runnable{
    private LocationUpdateRequest update;

    public LocationUpdateHandler(LocationUpdateRequest message){
        this.update = message;
    }

    @Override
    public void run() {
        GameManager.Location oldLocation = new GameManager.Location(update.getOldX(), update.getOldY());
        GameManager.Location newLocation = new GameManager.Location(update.getNewX(), update.getNewY());
        GameManager.updatePlayerLocation(update.getPlayerId(), oldLocation, newLocation);

    }
}
