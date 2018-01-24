package com.queens.game.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aditisri on 1/24/18.
 */
public class Server {
    private int port;
    private ServerSocket socket;
    public Server(int port){
        this.port = port;
        this.socket = null;
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
                System.out.println(InetAddress.getLocalHost());
                System.out.println(s.getInetAddress());
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
