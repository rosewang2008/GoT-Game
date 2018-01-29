package com.queens.game.server;

import java.util.HashMap;
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
        PLAYER_LOCATIONS.put(playerId, new Location(0, 0));
        return playerId;
    }

    public static void broadcastPlayers(){

    }

    public static boolean hasCollision(){
        return false;
    }
}
