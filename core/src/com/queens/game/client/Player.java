package com.queens.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.queens.game.networking.LocationUpdateRequest;

import java.util.HashMap;
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
    private Camera camera;
    private float stepDistance;
    private TiledMapTileLayer collisionMapLayer;
    private int id;

    public Player(int id, float startx, float starty, float stepDistance, Camera c, TiledMapTileLayer collisionMapLayer){
        initAnimations();
        this.currentDirection = Direction.RIGHT;
        this.height = AnimationFactory.getAnimationHeight();
        this.width = AnimationFactory.getAnimationWidth();
        this.worldX = startx;
        this.worldY = starty;
        this.screenX = startx;
        this.screenY = starty;
        this.stateTime = 0f;
        this.camera = c;
        this.stepDistance = stepDistance;
        this.collisionMapLayer = collisionMapLayer;
        this.id = id;
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
        switch(d){
            case UP:
                deltaY += this.stepDistance;
                break;
            case DOWN:
                deltaY -= this.stepDistance;
                break;
            case LEFT:
                deltaX -= this.stepDistance;
                break;
            case RIGHT:
                deltaX += this.stepDistance;
                break;
        }
        LocationUpdateRequest updateRequest= new LocationUpdateRequest(id, worldX, worldY, worldX + deltaX, worldY + deltaY);
        worldX += deltaX;
        worldY += deltaY;
        this.camera.translate(deltaX, deltaY, 0);
        if(this.hasCollision()){
            undoMove(deltaX, deltaY);
        }
        Client.sendMessageToServer(updateRequest);

    }

    public boolean hasCollision(){
        int row = getRow(this.collisionMapLayer);
        int col = getCol(this.collisionMapLayer);
        TiledMapTileLayer.Cell cell = this.collisionMapLayer.getCell(col, row);
        if(cell == null){
            return false;
        }
        TiledMapTile tile = cell.getTile();
        if(tile == null){
            return false;
        }
        if(!tile.getProperties().containsKey("walkable")){
            return false;
        }
        return !(Boolean)tile.getProperties().get("walkable");

    }

    public void undoMove(float deltaX, float deltaY){
        this.worldX -= deltaX;
        this.worldY -= deltaY;
        this.camera.translate(-deltaX, -deltaY, 0);
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
