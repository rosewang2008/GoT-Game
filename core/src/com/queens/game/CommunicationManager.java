package com.queens.game;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by aditisri on 1/24/18.
 */
public class CommunicationManager {
    private static int SERVER_PORT = 8000;
    private static int CLIENT_PORT = 5000;
    private static InetAddress SERVER_IP= null;
    private static Socket SOCKET = null;

    public static void openConnection(){
        try {
            SERVER_IP = InetAddress.getLocalHost();
			SOCKET = new Socket(SERVER_IP, SERVER_PORT);
		}
		catch (IOException e){
			e.printStackTrace();
		}
    }



}
