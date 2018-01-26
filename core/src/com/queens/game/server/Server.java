package com.queens.game.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.queens.game.networking.LocationUpdate;
import com.queens.game.networking.Message;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.queens.game.networking.Message.Type.LOCATION_UPDATE;

/**
 * Created by aditisri on 1/24/18.
 */
public class Server {
    private int port;
    private ServerSocket socket;
    private GsonBuilder builder;
    private Gson g;
    public Server(int port){
        this.port = port;
        this.socket = null;
        this.builder = new GsonBuilder();
        this.builder.registerTypeAdapter(Message.class, new MessageAdapter());
        this.g = this.builder.create();
        try {
            this.socket = new ServerSocket(port);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void start(){
        while (true) {
            try {
                Socket s = this.socket.accept();
                JsonReader reader = new JsonReader(new InputStreamReader(s.getInputStream()));
                Message m = this.g.fromJson(reader, Message.class);
                Thread t = null;
                switch(m.getType()){
                    case LOCATION_UPDATE:
                        t = new Thread(new LocationUpdateHandler((LocationUpdate) m));
                }
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
        Server s = new Server(8000);
        s.start();

    }

}
