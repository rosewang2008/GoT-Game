package com.queens.game.client;

import com.queens.game.networking.CollisionVerificationResponse;

/**
 * Created by aditisri on 2/3/18.
 */
public class CollisionVerificationHandler implements Runnable{
    private CollisionVerificationResponse response;
    private QueensGame game;
    public CollisionVerificationHandler(CollisionVerificationResponse res, QueensGame game){
        this.response = res;
        this.game = game;
    }

    @Override
    public void run() {
        handle();

    }

    public void handle(){
        if(response.getIsCollision()){
            System.out.println("is collision");
        }
        game.resumePlay();

    }
}
