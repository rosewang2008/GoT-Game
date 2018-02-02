package com.queens.game.networking;

import com.queens.game.client.Client;

/**
 * Created by aditisri on 2/2/18.
 */
public class CollisionVerificationRequest implements Request{
    private int fromPlayerId;
    private int id;
    public CollisionVerificationRequest(int fromPlayerId){
        this.fromPlayerId = fromPlayerId;
        this.id = Client.getUniqueId();

    }
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getPlayerId() {
        return this.fromPlayerId;
    }

    @Override
    public Type getType() {
        return null;
    }
}
