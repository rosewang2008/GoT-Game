package com.queens.game.networking;

import com.queens.game.client.Client;

/**
 * Created by aditisri on 1/27/18.
 */
public class ScoutingRequest implements Request{

    private int playerId;
    private int id;
    private Message.Type type;

    public ScoutingRequest(int playerId){
        this.playerId = playerId;
        this.id = Client.getUniqueId();
        this.type = Type.SCOUTING;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getPlayerId() {
        return this.playerId;
    }

    @Override
    public Type getType() {
        return this.type;
    }
}
