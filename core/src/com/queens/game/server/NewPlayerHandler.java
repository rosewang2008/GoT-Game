package com.queens.game.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.queens.game.networking.Message;
import com.queens.game.networking.NewPlayerRequest;
import com.queens.game.networking.NewPlayerResponse;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by aditisri on 1/27/18.
 */
public class NewPlayerHandler implements Runnable{
    private NewPlayerRequest request;
    private Server server;
    private OutputStreamWriter out;
    public NewPlayerHandler(NewPlayerRequest request, Server server, OutputStreamWriter out){
        this.request = request;
        this.server = server;
        this.out = out;
    }

    @Override
    public void run() {
        int newPlayerId = GameManager.registerNewPlayer();
        GameManager.setClientOutputStream(newPlayerId, out);
        server.sendMessageToClient(new NewPlayerResponse(request.getId(), newPlayerId), this.out);
    }
}
