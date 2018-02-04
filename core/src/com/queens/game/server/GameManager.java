package com.queens.game.server;

import com.queens.game.client.Client;
import com.queens.game.client.Player;
import com.queens.game.networking.Environment;

import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by aditisri on 1/25/18.
 */
public class GameManager {
    private static Map<Integer, PlayerInfo> PLAYER_LOCATIONS = new HashMap<>();
    private static Map<Integer, OutputStreamWriter> CLIENT_OUTPUT_STREAMS = new HashMap<>();
    private static int MAX_PLAYER_ID= 0;
    private static float START_X = 320;
    private static float START_Y = 224;
//    private static TiledMap map = new TmxMapLoader().load("core/assets/maps/test.tmx");

    public static void updatePlayerLocation(int playerId, float x, float y){
        PlayerInfo pi = PLAYER_LOCATIONS.get(playerId);
        pi.setLocation(x, y);
    }

    public static void setClientOutputStream(int id, OutputStreamWriter out){
        CLIENT_OUTPUT_STREAMS.put(id, out);
    }

    public static OutputStreamWriter getClientOutputStream(int playerId){
        return CLIENT_OUTPUT_STREAMS.get(playerId);
    }

    public static int getUniquePlayerId(){
        MAX_PLAYER_ID++;
        return MAX_PLAYER_ID;
    }

    public static int registerNewPlayer(){
        int playerId = getUniquePlayerId();
        PLAYER_LOCATIONS.put(playerId, new PlayerInfo(playerId, START_X, START_Y, Environment.OUTDOORS));
        return playerId;
    }

    public static void switchEnvironment(int playerId, Environment newEnv){
        PlayerInfo pi = PLAYER_LOCATIONS.get(playerId);
        pi.switchEnvironment(newEnv);
    }


    public static Set<PlayerInfo> getAllPlayerInfo(){return new HashSet<PlayerInfo>(PLAYER_LOCATIONS.values());}

    public static PlayerInfo getPlayerInfo(int playerId){
        return PLAYER_LOCATIONS.get(playerId);
    }

    public static float getStartX() {
        return START_X;
    }

    public static float getStartY() {
        return START_Y;
    }

    public static boolean hasCollision(){
        return false;
    }
}
