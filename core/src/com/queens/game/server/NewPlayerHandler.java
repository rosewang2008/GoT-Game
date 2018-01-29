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
    private OutputStreamWriter out;
    private GsonBuilder builder;
    public NewPlayerHandler(NewPlayerRequest request, OutputStreamWriter out, GsonBuilder builder){
        this.request = request;
        this.out = out;
        this.builder = builder;
    }

    @Override
    public void run() {
        int newPlayerId = GameManager.registerNewPlayer();
        Gson g = builder.create();
        g.toJson(new NewPlayerResponse(request.getId(), newPlayerId), Message.class, this.out);
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
