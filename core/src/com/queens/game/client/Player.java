package com.queens.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.ObjectMap;
import com.queens.game.networking.Direction;
import com.queens.game.networking.Environment;
import com.queens.game.networking.EnvironmentSwitchRequest;
import com.queens.game.networking.LocationUpdateRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by aditisri on 1/23/18.
 */
public class Player {
    private Map<Direction, Animation<TextureRegion>> animations;
    private Direction currentDirection;
    private float stateTime;
    private float height;
    private float width;
    private float screenX;
    private float screenY;
    private float worldX;
    private float worldY;
    private int id;
    private QueensGame game;

    public Player(int id, float startx, float starty, QueensGame game){
        initAnimations();
        this.currentDirection = Direction.RIGHT;
        this.height = AnimationFactory.getAnimationHeight();
        this.width = AnimationFactory.getAnimationWidth();
        this.worldX = startx;
        this.worldY = starty;
        this.screenX = startx;
        this.screenY = starty;
        this.stateTime = 0f;
        this.id = id;
        this.game = game;
    }

    public int getId(){
        return this.id;
    }

    private void initAnimations(){
        this.animations = new HashMap<Direction, Animation<TextureRegion>>();
        for(Direction d : Direction.values()){
            this.animations.put(d, AnimationFactory.getAnimation(d));
        }
    }

    public void setWorldX(float x){ this.worldX = x; }

    public void setWorldY(float y){this.worldY = y;}

    public float getWorldX(){
        return this.worldX;
    }

    public float getWorldY(){
        return this.worldY;
    }

    public float getScreenX(){
        return this.screenX;
    }

    public float getScreenY(){
        return this.screenY;
    }

    public void setCurrentDirection(Direction d){
        this.stateTime = 0f;
        this.currentDirection = d;
    }

    public Animation getAnimation(Direction d){
        return animations.get(d);
    }

    public int getRow(TiledMapTileLayer mapLayer){
        return (int)(worldY/mapLayer.getTileHeight());
    }

    public int getCol(TiledMapTileLayer mapLayer){
        return (int)(worldX/mapLayer.getTileWidth());

    }

    public void move(Direction d){
        this.setCurrentDirection(d);
        float deltaX = 0;
        float deltaY = 0;
        float stepDistance = game.getStepDistance();
        switch(d){
            case UP:
                deltaY += stepDistance;
                break;
            case DOWN:
                deltaY -= stepDistance;
                break;
            case LEFT:
                deltaX -= stepDistance;
                break;
            case RIGHT:
                deltaX += stepDistance;
                break;
        }
        LocationUpdateRequest updateRequest= new LocationUpdateRequest(id, worldX, worldY, worldX + deltaX, worldY + deltaY);
        worldX += deltaX;
        worldY += deltaY;
        this.game.getCamera().translate(deltaX, deltaY, 0);
        MapLayers layers = this.game.getMap().getLayers();
        for(int i = layers.size()-1; i >=0; i--){
            TiledMapTileLayer layer = (TiledMapTileLayer) layers.get(i);
            if (this.hasCollision(layer)) {
                System.out.println("got to collision");
                TiledMapTileLayer.Cell cell = getCell(layer);
                undoMove(deltaX, deltaY);
                if (cellHasProperty(cell, "objectType")) {
                    String objectType = (String) getProperty(cell, "objectType");
                    System.out.println(objectType);
                    respondToCollision(cell, objectType);
                }
                break;
            }
        }
        Client.sendMessageToServer(updateRequest);
    }

    public TiledMapTileLayer.Cell getCell(TiledMapTileLayer layer){
        int row = getRow(layer);
        int col = getCol(layer);
        return layer.getCell(col, row);
    }

    public boolean hasCollision(TiledMapTileLayer layer){
        TiledMapTileLayer.Cell cell = getCell(layer);
        if(!cellHasProperty(cell, "walkable"))
            return false;
        return !(Boolean) getProperty(cell, "walkable");
    }

    public boolean cellHasProperty(TiledMapTileLayer.Cell cell, String property){
        if(cell == null){
            return false;
        }
        TiledMapTile tile = cell.getTile();
        if(tile == null){
            return false;
        }
        return tile.getProperties().containsKey(property);
    }

    public Object getProperty(TiledMapTileLayer.Cell cell, String property){
        return cell.getTile().getProperties().get(property);
    }

    public void switchEnvironment(Environment env){
        Client.sendMessageToServer(new EnvironmentSwitchRequest(this.id, env));
        game.waitForServer();
    }


    public void respondToCollision(TiledMapTileLayer.Cell cell, String objectType){
        switch (ObjectHierarchy.valueOf(objectType)){
            case HOUSE:
                boolean isEntrance = (Boolean) getProperty(cell,"entrance");
                if(isEntrance) switchEnvironment(Environment.INDOORS);
                break;
            case PLAYER:
                break;
            case BED:
                break;
            case TABLE:
                break;
            case CHAIR:
                break;
            case COUNTER:
                break;
            case WALL:
                break;
            case DOOR:
                boolean isExit = (Boolean) getProperty(cell, "entrance");
                if(isExit) switchEnvironment(Environment.OUTDOORS);
                break;
        }
    }

    public void undoMove(float deltaX, float deltaY){
        this.worldX -= deltaX;
        this.worldY -= deltaY;
        this.game.getCamera().translate(-deltaX, -deltaY, 0);
    }

    public void draw(SpriteBatch batch){
        this.stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = this.animations.get(this.currentDirection).getKeyFrame(this.stateTime, false);
        batch.draw(frame, this.screenX, this.screenY);

    }

    public void drawBoundaries(SpriteBatch batch){
        Pixmap p = new Pixmap((int)this.width, (int)this.height, Pixmap.Format.RGBA8888);
        p.setColor(Color.CYAN);
        p.fillRectangle(0, 0, (int)width, (int)height);
        Texture t = new Texture(p);
        batch.draw(t, screenX, screenY);
    }

}
