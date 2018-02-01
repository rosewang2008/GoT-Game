package com.queens.game.client;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.queens.game.networking.Environment;

/**
 * Created by aditisri on 1/31/18.
 */
public class MapFactory {

    public static TiledMap getMap(Environment env){
        switch(env){
            case OUTDOORS:
                return new TmxMapLoader().load("core/assets/maps/test.tmx");
            case INDOORS:
                return new TmxMapLoader().load("core/assets/maps/inside.tmx");
        }
        return null;
    }
}
