package com.queens.game.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.queens.game.server.MessageAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aditisri on 1/24/18.
 */
public class CommunicationManager {
    private static int SERVER_PORT = 8000;
    private static int CLIENT_PORT = 5000;
    private static InetAddress SERVER_IP= null;
    private static Socket SOCKET = null;
    private static InputStreamReader IN= null;
    private static OutputStreamWriter OUT= null;
    private static GsonBuilder GSON_BUILDER = null;
    private static Gson GSON = null;

    public static void openConnection(){
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

    public static void sendMessageToServer(Message m, Message.Type type){
//        m.write(OUT);
        System.out.println("sent something");
        GSON.toJson(m, Message.class, OUT);
        try {
            OUT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
