package com.queens.game.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.queens.game.networking.CollisionVerificationRequest;
import com.queens.game.networking.Environment;
import com.queens.game.networking.LocationUpdateRequest;
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
		public Environment env;
		public Location(float x, float y, Environment env){
			this.x = x;
			this.y = y;
			this.env = env;
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
		map = MapFactory.getMap(Environment.OUTDOORS);
//		float unitScale = 1 / 32f;
//		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		renderer = new OrthogonalTiledMapRenderer(map);
		keysPressed = new HashSet<>();
		keyPressLock = new ReentrantLock();
		Client.sendMessageToServer(new NewPlayerRequest());
		otherPlayers = new ArrayList<>();
	}


	public void drawPlayerShadow(Location loc, SpriteBatch batch){
		Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		p.setColor(Color.RED);
		p.fillRectangle(0, 0, 32, 32);
		batch.draw(new Texture(p), loc.x, loc.y);
	}

	public void waitForServer(){
	    isGoing = false;
	}

	public void resumePlay(){
		isGoing = true;
	}

	public Location getLocationRelativeToPlayer(Location loc){
	    float xDelta = loc.x - this.player.getWorldX();
	    float yDelta = loc.y - this.player.getWorldY();
        return new Location(this.player.getScreenX() + xDelta, this.player.getScreenY() + yDelta, loc.env);
	}

	public void checkforPlayerCollisions(){
	    otherPlayers
				.stream()
				.filter(loc -> loc.x == player.getWorldX() && loc.y == player.getWorldY())
				.map(loc -> {
					Client.sendMessageToServer(new CollisionVerificationRequest(this.player.getId()));
					return null;
				});

	}

	public void switchEnvironment(Environment env, float x, float y){
		setMap(MapFactory.getMap(env));
		renderer = new OrthogonalTiledMapRenderer(this.map);
		// want to find displacement from center
		System.out.println(x + " " + y);
//		float cameraX = 320 + (x - 320);
//		float cameraY = 240 + (y - 224);
		camera.translate(320-camera.position.x, 240-camera.position.y, 0);
		camera.translate(x - camera.position.x, y-camera.position.y, 0);
		player.setWorldX(x);
		player.setWorldY(y);
		Client.sendMessageToServer(new LocationUpdateRequest(player.getId(), 0, 0, player.getWorldX(), player.getWorldY()));
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

	public void setMap(TiledMap map){this.map = map;}

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

	public void setOtherPlayers(List<Float> xLocations, List<Float> yLocations, List<Environment> envs){
	    List<Location> locations = new ArrayList<Location>();
	    for(int i = 0; i < xLocations.size(); i++){
	    	locations.add(getLocationRelativeToPlayer(new Location(xLocations.get(i), yLocations.get(i), envs.get(i))));
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
