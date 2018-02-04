package com.queens.game.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.queens.game.client.*;
import com.queens.game.server.ScoutingHandler;
import com.queens.game.networking.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by aditisri on 1/27/18.
 */
public class RequestListener implements Runnable{
    private InputStreamReader in;
    private OutputStreamWriter out;
    private GsonBuilder builder;
    private Server server;
    private Socket socket;

    public RequestListener(Server server, Socket s, InputStreamReader in, OutputStreamWriter out){
        this.server = server;
        this.socket = s;
        this.in = in;
        this.out = out;
        this.builder = new GsonBuilder();
        builder.registerTypeAdapter(Message.class, new MessageAdapter());

    }

    public void listen(){
        Gson g = this.builder.create();
        while(!this.socket.isClosed()){
            Request req = g.fromJson(new JsonReader(this.in), Message.class);
            System.out.println(req);
            Thread t = null;
            switch(req.getType()){
                case LOCATION_UPDATE:
                    t = new Thread(new LocationUpdateHandler((LocationUpdateRequest) req, this.out, this.builder));
                    break;
                case NEW_PLAYER:
                    t = new Thread(new NewPlayerHandler((NewPlayerRequest) req, this.server, this.out));
                    break;
                case SCOUTING:
                    t = new Thread(new ScoutingHandler((ScoutingRequest) req, this.server, this.out));
                    break;
                case ENVIRONMENT_SWITCH:
                    t = new Thread(new EnvironmentSwitchHandler((EnvironmentSwitchRequest) req, this.server, this.out));
                    break;
                case COLLISION_VERIFICATION:
                    t = new Thread(new CollisionVerificationHandler((CollisionVerificationRequest) req, this.server, this.out));
                    break;

            }
            if(t != null) {
                t.start();
            }
        }

    }

    @Override
    public void run() {
        this.listen();
    }
}
