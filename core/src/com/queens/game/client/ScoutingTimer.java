package com.queens.game.client;

import com.queens.game.networking.ScoutingRequest;

/**
 * Created by aditisri on 1/27/18.
 */
public class ScoutingTimer implements Runnable{
    private long startTime;
    private float frequency;
    private int playerId;
    public ScoutingTimer(float frequency, int playerId){
        this.startTime = 0l;
        this.frequency = frequency;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        this.start();

    }

    public void start(){
        this.startTime = System.currentTimeMillis();
        while(true){
            if(this.timeElapsed()%this.frequency == 0){

                Client.sendMessageToServer(new ScoutingRequest(this.playerId));
                this.startTime = System.currentTimeMillis();
            }

        }
    }

    public long timeElapsed(){
        return System.currentTimeMillis() - this.startTime;
    }
}
