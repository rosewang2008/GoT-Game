package com.queens.game.client;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.GsonBuilder;
import com.queens.game.networking.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aditisri on 1/26/18.
 */
public class Client{
    private static int PORT = 5000;
    private static int SERVER_PORT = 8000;
    private static InetAddress SERVER_IP= null;
    private static Socket SOCKET = null;
    private static InputStreamReader IN= null;
    private static OutputStreamWriter OUT= null;
    private static GsonBuilder GSON_BUILDER = null;
    private static Gson GSON = null;
    private static int MAX_REQUEST_NUM;
    private static Map<Integer, Request> UNRESOLVED_REQUESTS;

    public static int getUniqueId(){
        MAX_REQUEST_NUM++;
        return MAX_REQUEST_NUM;
    }

    public static void init(){
        MAX_REQUEST_NUM = 0;
        UNRESOLVED_REQUESTS = new HashMap<Integer, Request>();
        GSON_BUILDER = new GsonBuilder();
        GSON_BUILDER.registerTypeAdapter(Message.class, new MessageAdapter());
        GSON = GSON_BUILDER.create();
        try {
            SERVER_IP = InetAddress.getLocalHost();
            SOCKET = new Socket(SERVER_IP, SERVER_PORT);
            IN = new InputStreamReader(SOCKET.getInputStream());
            OUT = new OutputStreamWriter(SOCKET.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void start(QueensGame game){
        init();
        while(true){
            JsonReader reader = new JsonReader(IN);
            Response res = GSON.fromJson(reader, Message.class);
            resolveRequest(res.getRequestId());
            Thread t = null;
            switch(res.getType()){
                case LOCATION_UPDATE:
                    t = new Thread(new LocationUpdateHandler());
                    break;
                case NEW_PLAYER:
                    t = new Thread(new NewPlayerHandler((NewPlayerResponse) res, game));
                    break;
                case SCOUTING:
                    t = new Thread(new ScoutingHandler((ScoutingResponse) res, game));
                    break;
                case ENVIRONMENT_SWITCH:
                    t = new Thread(new EnvironmentSwitchHandler((EnvironmentSwitchResponse) res, game));
            }
            if(t != null){
                t.start();
            }
        }
    }

    public static void resolveRequest(int id){
        UNRESOLVED_REQUESTS.remove(id);
    }

    public static void sendMessageToServer(Request req){
        UNRESOLVED_REQUESTS.put(req.getId(), req);
        GSON.toJson(req, Message.class, OUT);
        try {
            OUT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
