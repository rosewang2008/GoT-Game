package com.queens.game.server;


import com.queens.game.networking.Environment;
import javafx.scene.Parent;

/**
 * Created by aditisri on 2/1/18.
 */
public class PlayerInfo {
    private int playerId;
    private Location location;
    public class Location {
        public float x;
        public float y;
        public Environment env;
        public Location parent;

        public Location(float x, float y, Environment env, Location parent) {
            this.x = x;
            this.y = y;
            this.env = env;
            this.parent = parent;
        }
    }
    public PlayerInfo(int playerId, float x, float y, Environment env){
        this.playerId = playerId;
        this.location = new Location(x, y, env, null);
    }

    public void setLocation(float x, float y){
        this.location.x = x;
        this.location.y = y;
    }

    public float getX(){
        return this.location.x;
    }

    public float getY(){
        return this.location.y;
    }

    public Environment getEnvironment(){return this.location.env;}


    public void switchEnvironment(Environment newEnv){
        if(this.location.parent != null && newEnv == this.location.parent.env) {
            this.location = this.location.parent;
        }
        else {
            Location newLoc = new Location(GameManager.getStartX(), GameManager.getStartY(), newEnv, this.location);
            this.location = newLoc;
        }
    }

}
