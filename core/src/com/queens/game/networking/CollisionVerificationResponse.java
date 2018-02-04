package com.queens.game.networking;

/**
 * Created by aditisri on 2/2/18.
 */
public class CollisionVerificationResponse implements Response{
    private int requestId;
    private boolean isCollision;

    public CollisionVerificationResponse(int requestId, boolean isCollision){
        this.requestId = requestId;
        this.isCollision = isCollision;
    }

    public boolean getIsCollision(){
        return this.isCollision;
    }

    @Override
    public int getRequestId() {
        return this.requestId;
    }

    @Override
    public Type getType() {
        return Type.COLLISION_VERIFICATION;
    }
}
