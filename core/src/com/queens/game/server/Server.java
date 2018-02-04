package com.queens.game.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.queens.game.networking.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
                OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
                InputStreamReader in = new InputStreamReader(s.getInputStream());
                Thread t = new Thread(new RequestListener(this, s, in, out));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void sendMessageToClient(Response res, OutputStreamWriter out){
        this.g.toJson(res, Message.class, out);
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args){
        Server s = new Server(8000);
        s.start();

    }

}
