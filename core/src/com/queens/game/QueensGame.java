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
	Texture walkSheet;
	float stateTime;
	float x;
	float y;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;
	Rectangle charBounds;
	Set<Integer> keysPressed;
	Lock keyPressLock;
	float cameraOffsetX;
	float cameraOffsetY;
	static float scrollSpeed = 32;

	@Override
	public void create () {
		batch = new SpriteBatch();
		walkSheet = new Texture("core/assets/sprites/girl_sprite.png");
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / 13,
				walkSheet.getHeight() / 21);
		TextureRegion[] walkFrames = Arrays.copyOfRange(tmp[11], 0, 9);
		walking = new Animation<TextureRegion>(0.1f, walkFrames);
		stateTime = 0f;
		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 30,20);
        camera.setToOrtho(false);
		camera.update();
		map = new TmxMapLoader().load("core/assets/maps/test.tmx");
//		float unitScale = 1 / 32f;
		Gdx.input.setInputProcessor(this);
//		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		renderer = new OrthogonalTiledMapRenderer(map);
		x = 0;
		y = 0;
		charBounds = new Rectangle(0, 0, walkSheet.getWidth()/13, walkSheet.getHeight()/21);
		keysPressed = new HashSet<Integer>();
		keyPressLock = new ReentrantLock();
		System.out.println("camera position " + camera.position);
		cameraOffsetX = 0;
		cameraOffsetY = 0;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion frame = walking.getKeyFrame(stateTime, true);
		camera.update();
		renderer.setView(camera);
		renderer.render();
//		for(int keycode : keysPressed){
//		    moveCharacter(keycode);
//		}
		batch.begin();
//		drawObjectOutlines(batch);
//		drawSingleTile(batch);
		drawCharacterOutline(batch);
		batch.draw(frame, x, y);
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
		moveCharacter(keycode);
		return false;
	}

	public void moveCharacter(int keycode) {
		int stepDistance = 32;
		float cameraDistance = stepDistance;
	    switch (keycode) {
				case Input.Keys.LEFT:
					x -= stepDistance;
					charBounds.x -= stepDistance;
					camera.translate(-cameraDistance, 0);
					cameraOffsetX -= cameraDistance;
					fixCollisions(-stepDistance, 0, -cameraDistance, 0);
					break;
				case Input.Keys.RIGHT:
					x += stepDistance;
					charBounds.x += stepDistance;
					camera.translate(cameraDistance, 0);
					cameraOffsetX += cameraDistance;
					fixCollisions(stepDistance, 0, cameraDistance, 0);
					break;
				case Input.Keys.UP:
					y += stepDistance;
					charBounds.y += stepDistance;
					camera.translate(0, cameraDistance);
					cameraOffsetY += cameraDistance;
					fixCollisions(0, stepDistance, 0 , cameraDistance);
					break;
				case Input.Keys.DOWN:
					y -= stepDistance;
					charBounds.y -= stepDistance;
					camera.translate(0, -cameraDistance);
					cameraOffsetY -= cameraDistance;
					fixCollisions(0, -stepDistance, 0, -cameraDistance);
					break;
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

	public void fixCollisions(float xDelta, float yDelta, float xDeltaCamera, float yDeltaCamera){
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer)map.getLayers().get(1);
		int col = (int)((cameraOffsetX + charBounds.x) / collisionLayer.getTileWidth());
		int row = (int)((cameraOffsetY + charBounds.y) / collisionLayer.getTileHeight());
		System.out.println("tile height " + collisionLayer.getTileHeight() + " tile width " + collisionLayer.getTileWidth());

		System.out.println("row " + row + " col " + col);
		TiledMapTileLayer.Cell cell = collisionLayer.getCell(col, row);
		if(cell == null){
			System.out.println("no cell");
			return;
		}
		TiledMapTile tile = cell.getTile();
		if(tile == null){
			System.out.println("no tile");
			return;
		}
		if(!tile.getProperties().containsKey("walkable")){
			System.out.println("no walkable property");
			return;
		}
		if(!(Boolean)tile.getProperties().get("walkable")){
			x -= xDelta;
			y -= yDelta;
			charBounds.x -= xDelta;
			charBounds.y -= yDelta;
			camera.translate(-xDeltaCamera, -yDeltaCamera);
			cameraOffsetX -= xDeltaCamera;
			cameraOffsetY -= yDeltaCamera;
		}

		System.out.println(tile.getProperties().get("walkable"));


	}



	public void drawObjectOutlines(SpriteBatch batch){
		MapLayer objectLayer = map.getLayers().get(1);
		MapObjects objects = objectLayer.getObjects();
		for(RectangleMapObject rectObj : objects.getByType(RectangleMapObject.class)){
			Rectangle objBound = rectObj.getRectangle();
			float scale = 1.4f;
			Rectangle scaledObjBound = new Rectangle(objBound.x/scale, objBound.y/scale, objBound.width/scale, objBound.height/scale);
//			System.out.println("object position " + objBound.x + " " + objBound.y);
			Pixmap p = new Pixmap((int)scaledObjBound.getWidth(), (int)scaledObjBound.getHeight(), Pixmap.Format.RGBA8888);
			p.setColor(Color.CYAN);
			p.fillRectangle(0, 0, (int)scaledObjBound.getWidth(), (int)scaledObjBound.getHeight());
			Texture t = new Texture(p);
			batch.draw(t, scaledObjBound.x, scaledObjBound.y);
		}
	}

	public void drawCharacterOutline(SpriteBatch batch){
	    Pixmap p = new Pixmap((int)charBounds.getWidth(), (int)charBounds.getHeight(), Pixmap.Format.RGBA8888);
	    p.setColor(Color.PINK);
	    p.fillRectangle(0, 0, (int)charBounds.getWidth(), (int)charBounds.getHeight());
	    Texture t = new Texture(p);
	    batch.draw(t, charBounds.x, charBounds.y);
	}

	public void drawSingleTile(SpriteBatch batch){
	    int size = 32;
	    Pixmap p = new Pixmap(size, size, Pixmap.Format.RGBA8888);
	    p.setColor(Color.CYAN);
	    p.fillRectangle(0, 0, size, size);
	    batch.draw(new Texture(p), 0, 0);

	}
}
