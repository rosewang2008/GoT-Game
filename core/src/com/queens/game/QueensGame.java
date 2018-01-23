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

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QueensGame extends ApplicationAdapter implements InputProcessor{
	SpriteBatch batch;
	Animation<TextureRegion> walking;
	Animation<TextureRegion> walkingLeft;
	Texture walkSheet;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;
	Set<Integer> keysPressed;
	Lock keyPressLock;
	static float scrollSpeed = 32;
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
		Gdx.input.setInputProcessor(this);
//		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		renderer = new OrthogonalTiledMapRenderer(map);
		keysPressed = new HashSet<Integer>();
		keyPressLock = new ReentrantLock();
		System.out.println("camera position " + camera.position);
		player = new Player(Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/2)%32, Gdx.graphics.getHeight()/2 - (Gdx.graphics.getHeight()/2)%32);
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
		walkSheet.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		setKeyPressed(keycode, true);
		move(keycode);
		return false;
	}

	public void move(int keycode) {
		int stepDistance = 32;
		float cameraDistance = stepDistance;
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer)map.getLayers().get(1);
		float xDelta = 0;
		float yDelta = 0;
		Direction d = null;
	    switch (keycode) {
				case Input.Keys.LEFT:
					xDelta -= cameraDistance;
					d = Direction.LEFT;
					break;
				case Input.Keys.RIGHT:
					xDelta += cameraDistance;
					d = Direction.RIGHT;
					break;
				case Input.Keys.UP:
					yDelta += cameraDistance;
					d = Direction.UP;
					break;
				case Input.Keys.DOWN:
					yDelta -= cameraDistance;
					d = Direction.DOWN;
					break;
		}
		camera.translate(xDelta, yDelta);
	    player.setCurrentDirection(d);
	    player.move(xDelta, yDelta);
		if(player.hasCollision(collisionLayer)){
		    player.undoMove(xDelta, yDelta);
			camera.translate(-xDelta, -yDelta);
		}

	}




	@Override
	public boolean keyUp(int keycode) {
	    setKeyPressed(keycode, false);
		return false;
	}

	public boolean getKeyPressed(int keycode){
		keyPressLock.lock();
		boolean isPressed = keysPressed.contains(keycode);
		keyPressLock.unlock();
		return isPressed;
	}

	public void setKeyPressed(int keycode, boolean isPressed){
	    keyPressLock.lock();
	    if(isPressed) {
			keysPressed.add(keycode);
		}
		else{
	    	keysPressed.remove(keycode);
		}
	    keyPressLock.unlock();

	}


	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void drawObjectOutlines(SpriteBatch batch){
		MapLayer objectLayer = map.getLayers().get(1);
		MapObjects objects = objectLayer.getObjects();
		for(RectangleMapObject rectObj : objects.getByType(RectangleMapObject.class)){
			Rectangle objBound = rectObj.getRectangle();
			float scale = 1.4f;
			Rectangle scaledObjBound = new Rectangle(objBound.x/scale, objBound.y/scale, objBound.width/scale, objBound.height/scale);
			Pixmap p = new Pixmap((int)scaledObjBound.getWidth(), (int)scaledObjBound.getHeight(), Pixmap.Format.RGBA8888);
			p.setColor(Color.CYAN);
			p.fillRectangle(0, 0, (int)scaledObjBound.getWidth(), (int)scaledObjBound.getHeight());
			Texture t = new Texture(p);
			batch.draw(t, scaledObjBound.x, scaledObjBound.y);
		}
	}

	public void drawSingleTile(SpriteBatch batch){
	    int size = 32;
	    Pixmap p = new Pixmap(size, size, Pixmap.Format.RGBA8888);
	    p.setColor(Color.CYAN);
	    p.fillRectangle(0, 0, size, size);
	    batch.draw(new Texture(p), 0, 0);

	}
}
