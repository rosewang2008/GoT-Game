package com.queens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

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
    private Rectangle bounds;
    private float screenX;
    private float screenY;
    private float worldX;
    private float worldY;

    public Player(float startx, float starty){
        initAnimations();
        this.currentDirection = Direction.RIGHT;
        this.height = AnimationFactory.getAnimationHeight();
        this.width = AnimationFactory.getAnimationWidth();
        bounds = new Rectangle(startx, starty, this.width, this.height);
        worldX = startx;
        worldY = starty;
        screenX = startx;
        screenY = starty;
        stateTime = 0f;
    }


    private void initAnimations(){
        this.animations = new HashMap<Direction, Animation<TextureRegion>>();
        for(Direction d : Direction.values()){
            this.animations.put(d, AnimationFactory.getAnimation(d));
        }
    }

    public void setCurrentDirection(Direction d){
        stateTime = 0f;
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

    public void move(float deltaX, float deltaY){
        worldX += deltaX;
        worldY += deltaY;
    }

    public boolean hasCollision(TiledMapTileLayer mapLayer){
        int row = getRow(mapLayer);
        int col = getCol(mapLayer);
        TiledMapTileLayer.Cell cell = mapLayer.getCell(col, row);
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
        return (Boolean)tile.getProperties().get("walkable");

    }

    public void undoMove(float deltaX, float deltaY){
        this.worldX -= deltaX;
        this.worldY -= deltaY;
    }

    public void draw(SpriteBatch batch){
        this.stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = this.animations.get(this.currentDirection).getKeyFrame(this.stateTime, false);
        batch.draw(frame, this.screenX, this.screenY);

    }



}
