package com.queens.game.server;

import com.queens.game.client.Player;
import com.queens.game.networking.Environment;
import com.queens.game.networking.EnvironmentSwitchRequest;
import com.queens.game.networking.EnvironmentSwitchResponse;

import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 2/1/18.
 */
public class EnvironmentSwitchHandler implements Runnable{

    private EnvironmentSwitchRequest request;
    private Server server;
    private OutputStreamWriter out;

    public EnvironmentSwitchHandler(EnvironmentSwitchRequest request, Server s, OutputStreamWriter out){
        this.request = request;
        this.server = s;
        this.out = out;
    }

    @Override
    public void run() {
        PlayerInfo oldInfo = GameManager.getPlayerInfo(request.getPlayerId());
        System.out.println("old info " + oldInfo.getX() + " " + oldInfo.getY());
        GameManager.switchEnvironment(request.getPlayerId(), request.getNewEnv());
        PlayerInfo pi = GameManager.getPlayerInfo(request.getPlayerId());
        System.out.println("new info " + pi.getX() + " " + pi.getY());
        server.sendMessageToClient(new EnvironmentSwitchResponse(request.getId(), request.getNewEnv(), pi.getX(), pi.getY()), out);
    }
}
