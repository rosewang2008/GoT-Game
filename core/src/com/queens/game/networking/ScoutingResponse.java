package com.queens.game.networking;


import java.util.List;

/**
 * Created by aditisri on 1/27/18.
 */
public class ScoutingResponse implements Response{
    private int requestId;
    private List<Float> xLocations;
    private List<Float> yLocations;
    private List<Environment> environments;
    private List<Integer> ids;
    public ScoutingResponse(int requestId, List<Integer> ids, List<Float> xLocations, List<Float> yLocations, List<Environment> envs){
        this.requestId = requestId;
        this.xLocations = xLocations;
        this.yLocations = yLocations;
        this.environments = envs;
        this.ids = ids;
    }

    @Override
    public int getRequestId() {
        return requestId;
    }

    public List<Float> getxLocations(){
        return this.xLocations;
    }

    public List<Float> getyLocations(){
        return this.yLocations;
    }

    public List<Environment> getEnvironments(){ return this.environments;}

    public List<Integer> getIds() {
        return ids;
    }

    @Override
    public Type getType() {
        return Type.SCOUTING;
    }
}
