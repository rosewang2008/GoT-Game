package com.queens.game.server;

import com.queens.game.networking.CollisionVerificationRequest;
import com.queens.game.networking.CollisionVerificationResponse;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 2/3/18.
 */
public class CollisionVerificationHandler implements Runnable{
    private CollisionVerificationRequest request;
    private Server server;
    private OutputStreamWriter out;

    public CollisionVerificationHandler(CollisionVerificationRequest req, Server s, OutputStreamWriter out){
        this.request = req;
        this.server = s;
        this.out = out;

    }

    @Override
    public void run() {
        PlayerInfo toInfo = GameManager.getPlayerInfo(this.request.getToPlayerId());
        boolean isCollision = false;
        if (toInfo.getX() == request.getX() && toInfo.getY() == request.getY()){
            isCollision = true;
            server.sendMessageToClient(new CollisionVerificationResponse(-1, true), GameManager.getClientOutputStream(this.request.getToPlayerId()));
        }
        server.sendMessageToClient(new CollisionVerificationResponse(request.getId(), isCollision), out);
    }
}
