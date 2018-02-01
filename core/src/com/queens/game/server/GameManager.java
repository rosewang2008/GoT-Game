package com.queens.game.server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aditisri on 1/25/18.
 */
public class GameManager {
    public static class Location{
        public float x;
        public float y;
        public Location(float x, float y){
            this.x = x;
            this.y = y;
        }
    }

    private static Map<Integer, Location> PLAYER_LOCATIONS = new HashMap<Integer, Location>();
    private static int MAX_PLAYER_ID= 0;
//    private static TiledMap map = new TmxMapLoader().load("core/assets/maps/test.tmx");

    public static void updatePlayerLocation(int playerId, Location oldLocation, Location newLocation){
        PLAYER_LOCATIONS.put(playerId, newLocation);
    }

    public static int getUniquePlayerId(){
        MAX_PLAYER_ID++;
        return MAX_PLAYER_ID;
    }

    public static int registerNewPlayer(){
        int playerId = getUniquePlayerId();
        PLAYER_LOCATIONS.put(playerId, new Location(320, 224));
        return playerId;
    }

    public static List<Float> getLocations(int playerId, boolean xDirection){
        List<Float> locations = new ArrayList<Float>();
        for(int id : PLAYER_LOCATIONS.keySet()){
            if(id == playerId) continue;
            if(xDirection)
                locations.add(PLAYER_LOCATIONS.get(id).x);
            else
                locations.add(PLAYER_LOCATIONS.get(id).y);
        }
        return locations;
    }

    public static Map<Integer, Location> getAllPlayerLocations(){
        return PLAYER_LOCATIONS;
    }

    public static boolean hasCollision(){
        return false;
    }
}
