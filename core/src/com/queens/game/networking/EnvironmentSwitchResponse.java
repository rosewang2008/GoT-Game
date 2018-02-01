package com.queens.game.networking;

/**
 * Created by aditisri on 2/1/18.
 */
public class EnvironmentSwitchResponse implements Response{
    private int requestId;
    private float x;
    private float y;
    private Environment env;
    public EnvironmentSwitchResponse(int requestId, Environment env, float x, float y){
        this.requestId = requestId;
        this.x = x;
        this.y = y;
        this.env = env;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Environment getEnvironment(){
        return this.env;
    }

    @Override
    public int getRequestId() {
        return this.requestId;
    }

    @Override
    public Type getType() {
        return Type.ENVIRONMENT_SWITCH;
    }
}
