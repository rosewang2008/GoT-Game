package com.queens.game.networking;

/**
 * Created by aditisri on 1/26/18.
 */
public class NewPlayerResponse implements Response{
    private int requestId;
    private int newPlayerId;

    public NewPlayerResponse(int requestId, int newPlayerId){
        this.requestId = requestId;
        this.newPlayerId = newPlayerId;
    }

    public int getNewPlayerId(){
        return this.newPlayerId;
    }

    @Override
    public int getRequestId() {
        return this.requestId;
    }


    @Override
    public Type getType() {
        return Type.NEW_PLAYER;
    }
}
