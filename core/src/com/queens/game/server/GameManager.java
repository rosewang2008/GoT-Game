package com.queens.game.server;

import com.queens.game.networking.Environment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aditisri on 1/25/18.
 */
public class GameManager {
    private static Map<Integer, PlayerInfo> PLAYER_LOCATIONS = new HashMap<Integer, PlayerInfo>();
    private static int MAX_PLAYER_ID= 0;
    private static float START_X = 320;
    private static float START_Y = 224;
//    private static TiledMap map = new TmxMapLoader().load("core/assets/maps/test.tmx");

    public static void updatePlayerLocation(int playerId, float x, float y){
        PlayerInfo pi = PLAYER_LOCATIONS.get(playerId);
        pi.setLocation(x, y);
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

    public static List<Float> getLocations(int playerId, boolean xDirection){
        List<Float> locations = new ArrayList<Float>();
        for(int id : PLAYER_LOCATIONS.keySet()){
            if(id == playerId) continue;
            if(xDirection)
                locations.add(PLAYER_LOCATIONS.get(id).getX());
            else
                locations.add(PLAYER_LOCATIONS.get(id).getY());
        }
        return locations;
    }

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
