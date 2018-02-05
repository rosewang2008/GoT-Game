package com.queens.game.networking;

/**
 * Created by aditisri on 1/26/18.
 */
public class LocationUpdateResponse implements Response{
    private int requestId;
    public LocationUpdateResponse(int requestId){
        this.requestId = requestId;
    }

    @Override
    public int getRequestId() {
        return this.requestId;
    }

    @Override
    public Type getType() {
        return Type.LOCATION_UPDATE;
    }

}
