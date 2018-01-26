package com.queens.game.server;


import com.queens.game.networking.LocationUpdate;

/**
 * Created by aditisri on 1/25/18.
 */
public class LocationUpdateHandler implements Runnable{
    private LocationUpdate update;

    public LocationUpdateHandler(LocationUpdate message){
        this.update = message;
    }

    @Override
    public void run() {

    }
}
