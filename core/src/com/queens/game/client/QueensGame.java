package com.queens.game.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.queens.game.networking.NewPlayerRequest;
import com.queens.game.networking.NewPlayerResponse;
import com.queens.game.networking.Response;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

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
	boolean isGoing;

	@Override
	public void create () {
		isGoing = false;
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
//		Thread t = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				Client.start();
//			}
//		});
//		t.start();
		Client.sendMessageToServer(new NewPlayerRequest());

	}

	public Camera getCamera(){
		return this.camera;
	}

	public float getStepDistance(){
		return stepDistance;
	}

	public TiledMap getMap(){
		return this.map;
	}

	public void setPlayerInputHandler(PlayerInputProcessor p){
		Gdx.input.setInputProcessor(p);
	}

	public void setPlayer(Player p){
		this.player = p;
	}

	public void start(){
		this.isGoing= true;
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(isGoing) {
			camera.update();
			renderer.setView(camera);
			renderer.render();
			batch.begin();
			player.draw(batch);
			batch.end();
		}
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
