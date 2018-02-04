package com.queens.game.networking;

import com.queens.game.client.Client;

/**
 * Created by aditisri on 2/2/18.
 */
public class CollisionVerificationRequest implements Request{
    private int fromPlayerId;
    private int toPlayerId;
    private int id;
    private float x;
    private float y;
    public CollisionVerificationRequest(int fromPlayerId, int toPlayerId, float x, float y){
        this.fromPlayerId = fromPlayerId;
        this.toPlayerId = toPlayerId;
        this.id = Client.getUniqueId();
        this.x = x;
        this.y = y;

    }
    @Override
    public int getId() {
        return this.id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getToPlayerId(){
        return this.toPlayerId;
    }

    @Override
    public int getPlayerId() {
        return this.fromPlayerId;
    }

    @Override
    public Type getType(){
        return Type.COLLISION_VERIFICATION;
    }
}
