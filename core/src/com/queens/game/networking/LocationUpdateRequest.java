package com.queens.game.networking;

import com.google.gson.Gson;
import com.queens.game.client.Client;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 1/24/18.
 */
public class LocationUpdateRequest implements Request{
    private float oldX;
    private float oldY;
    private float newX;
    private float newY;
    private Message.Type type;
    private int playerId;
    private int id;
    public LocationUpdateRequest(int playerId, float oldX, float oldY, float newX, float newY){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.playerId = playerId;
        this.type = Type.LOCATION_UPDATE;
        this.id = Client.getUniqueId();
    }

    public int getPlayerId(){
        return this.playerId;
    }

    public float getOldX(){
        return this.oldX;
    }

    public float getOldY(){
        return this.oldY;
    }

    public float getNewX(){
        return this.newX;
    }

    public float getNewY(){
        return this.newY;
    }

    public Message.Type getType(){
        return this.type;
    }

    public void print(){
        System.out.println(oldX + " " + oldY + " " + newX + " " + newY);
    }


    @Override
    public int getId() {
        return 0;
    }
}
