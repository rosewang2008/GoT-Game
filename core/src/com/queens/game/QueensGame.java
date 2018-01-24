package com.queens.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QueensGame extends ApplicationAdapter{
	SpriteBatch batch;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;
	Set<Integer> keysPressed;
	Lock keyPressLock;
	static float scrollSpeed = 32;
	static float stepDistance = 32;
	Player player;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 30,20);
        camera.setToOrtho(false);
		camera.update();
		map = new TmxMapLoader().load("core/assets/maps/test.tmx");
//		float unitScale = 1 / 32f;
//		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		renderer = new OrthogonalTiledMapRenderer(map);
		keysPressed = new HashSet<Integer>();
		keyPressLock = new ReentrantLock();
		player = new Player(Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/2)%32, Gdx.graphics.getHeight()/2 - (Gdx.graphics.getHeight()/2)%32, stepDistance, camera, (TiledMapTileLayer)map.getLayers().get(1));
		Gdx.input.setInputProcessor(new PlayerInputProcessor(player));
		CommunicationManager.openConnection();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		player.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}


	public void drawSingleTile(SpriteBatch batch){
	    int size = 32;
	    Pixmap p = new Pixmap(size, size, Pixmap.Format.RGBA8888);
	    p.setColor(Color.CYAN);
	    p.fillRectangle(0, 0, size, size);
	    batch.draw(new Texture(p), 0, 0);

	}
}
