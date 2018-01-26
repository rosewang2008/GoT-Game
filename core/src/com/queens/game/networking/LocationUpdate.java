package com.queens.game.networking;

import com.google.gson.Gson;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 1/24/18.
 */
public class LocationUpdate implements Message{
    private float oldX;
    private float oldY;
    private float newX;
    private float newY;
    private Message.Type type;
    public LocationUpdate(float oldX, float oldY, float newX, float newY){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.type = Type.LOCATION_UPDATE;
    }

    public Message.Type getType(){
        return this.type;
    }

    public void print(){
        System.out.println(oldX + " " + oldY + " " + newX + " " + newY);
    }


    @Override
    public void write(OutputStreamWriter out) {
        Gson g = new Gson();
        g.toJson(this, out);
    }
}
