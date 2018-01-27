package com.queens.game.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.queens.game.networking.LocationUpdateRequest;
import com.queens.game.networking.Message;
import com.queens.game.networking.MessageAdapter;
import com.queens.game.networking.Request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
                Thread t = new Thread(new RequestListener(s));
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
