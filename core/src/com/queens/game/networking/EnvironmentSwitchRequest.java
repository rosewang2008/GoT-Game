package com.queens.game.networking;

import com.queens.game.client.Client;

/**
 * Created by aditisri on 2/1/18.
 */
public class EnvironmentSwitchRequest implements Request{
    private int playerId;
    private int id;
    private Environment newEnv;

    public EnvironmentSwitchRequest(int playerId, Environment newEnv){
        this.playerId = playerId;
        this.id = Client.getUniqueId();
        this.newEnv = newEnv;
    }

    public Environment getNewEnv(){
        return this.newEnv;
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
        return Type.ENVIRONMENT_SWITCH;
    }
}
