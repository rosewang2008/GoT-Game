package com.queens.game.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.queens.game.networking.NewPlayerRequest;

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
	boolean isGoing;
	List<Location> otherPlayers;

	public class Location{
		public float x;
		public float y;
		public Location(float x, float y){
			this.x = x;
			this.y = y;
		}
	}

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
		otherPlayers = new ArrayList<Location>();


	}

	public void drawPlayerShadow(Location loc, SpriteBatch batch){
		Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		p.setColor(Color.RED);
		p.fillRectangle(0, 0, 32, 32);
		batch.draw(new Texture(p), loc.x, loc.y);

	}

	public Location getLocationRelativeToPlayer(Location loc){
	    float xDelta = loc.x - this.player.getWorldX();
	    float yDelta = loc.y - this.player.getWorldY();
        return new Location(this.player.getScreenX() + xDelta, this.player.getScreenY() + yDelta);
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
	    Thread t = new Thread(new ScoutingTimer(500, this.player.getId()));
	    t.start();
		this.isGoing= true;
	}

	public void setOtherPlayers(List<Float> xLocations, List<Float> yLocations){
	    List<Location> locations = new ArrayList<Location>();
	    for(int i = 0; i < xLocations.size(); i++){
	    	locations.add(getLocationRelativeToPlayer(new Location(xLocations.get(i), yLocations.get(i))));
		}
		this.otherPlayers = locations;
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
			for (Location l : otherPlayers){
			    drawPlayerShadow(l, batch);
			}

			batch.end();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
