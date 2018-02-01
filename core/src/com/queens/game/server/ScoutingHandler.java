package com.queens.game.server;

import com.queens.game.networking.ScoutingRequest;
import com.queens.game.networking.ScoutingResponse;

import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by aditisri on 1/29/18.
 */
public class ScoutingHandler implements Runnable{

    private ScoutingRequest request;
    private Server server;
    private OutputStreamWriter out;

    public ScoutingHandler(ScoutingRequest request, Server s, OutputStreamWriter out){
        this.request = request;
        this.server = s;
        this.out = out;
    }

    @Override
    public void run() {
        List<Float> xPositions = GameManager.getLocations(request.getPlayerId(), true);
        List<Float> yPositions = GameManager.getLocations(request.getPlayerId(), false);
        this.server.sendMessageToClient(new ScoutingResponse(this.request.getId(), xPositions, yPositions), this.out);
    }
}
