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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;

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
		camera.setToOrtho(false, 30, 20);
		camera.update();
		map = new TmxMapLoader().load("core/assets/maps/test.tmx");
		float unitScale = 1 / 32f;
		Gdx.input.setInputProcessor(this);
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		x = 0;
		y = 0;
		charBounds = new Rectangle(0, 0, walkSheet.getWidth()/13, walkSheet.getHeight()/21);
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
		batch.begin();
//		drawObjectOutlines(batch);
//		drawCharacterOutline(batch);
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
	    switch(keycode){
			case Input.Keys.LEFT:
				x -= 10;
				charBounds.x -= 10;
				fixCollisions(-10, 0);
				break;
			case Input.Keys.RIGHT:
				x += 10;
				charBounds.x += 10;
				fixCollisions(10, 0);
				break;
			case Input.Keys.UP:
				y += 10;
				charBounds.y += 10;
				fixCollisions(0, 10);
				break;
			case Input.Keys.DOWN:
				y -= 10;
				charBounds.y -= 10;
				fixCollisions(0, -10);
				break;
		}
		return false;
	}



	@Override
	public boolean keyUp(int keycode) {

		return false;
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

	public void fixCollisions(float xDelta, float yDelta){
		MapLayer objectLayer = map.getLayers().get(2);
		MapObjects objects = objectLayer.getObjects();
		for(RectangleMapObject rectObj : objects.getByType(RectangleMapObject.class)){
			Rectangle objBound = rectObj.getRectangle();
			if(objBound.overlaps(charBounds)){
				x -= xDelta;
				y -= yDelta;
				charBounds.x -= xDelta;
				charBounds.y -= yDelta;
				return;
			}
		}
	}

	public void drawObjectOutlines(SpriteBatch batch){
		MapLayer objectLayer = map.getLayers().get(2);
		MapObjects objects = objectLayer.getObjects();
		for(RectangleMapObject rectObj : objects.getByType(RectangleMapObject.class)){
			Rectangle objBound = rectObj.getRectangle();
			Pixmap p = new Pixmap((int)objBound.getWidth(), (int)objBound.getHeight(), Pixmap.Format.RGBA8888);
			p.setColor(Color.RED);
			Texture t = new Texture(p);
			batch.draw(t, objBound.x, objBound.y);
		}
	}

	public void drawCharacterOutline(SpriteBatch batch){
	    Pixmap p = new Pixmap((int)charBounds.getWidth(), (int)charBounds.getHeight(), Pixmap.Format.RGBA8888);
	    p.setColor(Color.RED);
	    Texture t = new Texture(p);
	    batch.draw(t, charBounds.x, charBounds.y);
	}
}
