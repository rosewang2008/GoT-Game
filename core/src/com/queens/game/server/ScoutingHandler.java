package com.queens.game.server;

import com.queens.game.networking.Environment;
import com.queens.game.networking.ScoutingRequest;
import com.queens.game.networking.ScoutingResponse;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<PlayerInfo> filterOutIrrelevant(Set<PlayerInfo> allPlayerInfo){
        PlayerInfo selfInfo= GameManager.getPlayerInfo(request.getPlayerId());
        return allPlayerInfo.stream().filter(info -> info != selfInfo && selfInfo.getEnvironment() == info.getEnvironment()).collect(Collectors.toSet());
    }

    public List<Float> getxPositions(Set<PlayerInfo> allPlayerInfo){
        return allPlayerInfo.stream().map(PlayerInfo::getX).collect(Collectors.toList());
    }

    public List<Float> getyPositions(Set<PlayerInfo> allPlayerInfo){
        return allPlayerInfo.stream().map(PlayerInfo::getY).collect(Collectors.toList());
    }

    public List<Environment> getEnvironments(Set<PlayerInfo> allPlayerInfo){
        return allPlayerInfo.stream().map(PlayerInfo::getEnvironment).collect(Collectors.toList());
    }

    @Override
    public void run() {
        Set<PlayerInfo> allPlayerInfo = filterOutIrrelevant(GameManager.getAllPlayerInfo());
        this.server.sendMessageToClient(new ScoutingResponse(this.request.getId(), getxPositions(allPlayerInfo), getyPositions(allPlayerInfo), getEnvironments(allPlayerInfo)), this.out);
    }
}
