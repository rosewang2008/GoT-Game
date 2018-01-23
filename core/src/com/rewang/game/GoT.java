package com.rewang.game;
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

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;

public class GoT extends ApplicationAdapter implements InputProcessor {

	// Sprite character
	private SpriteBatch batch;
	private Texture spriteTexture;
	private Sprite spriteCharacter;
	private int spriteX = 0;
	private int spriteY = 0 ;

	// background
	TiledMapRenderer tiledMapRenderer;
	TiledMap tiledMap;
	private SpriteBatch spriteBatch;

    // camera
    OrthographicCamera camera;

	@Override
	public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        // map
        tiledMap = new TmxMapLoader().load("maps/indoors.tmx");
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);

        //character
		batch = new SpriteBatch();
		FileHandle spriteFileHandle = Gdx.files.internal("sprites/pikachu.png");
		spriteTexture = new Texture(spriteFileHandle);
		spriteCharacter = new Sprite(spriteTexture);
	}

	@Override
	public void render () {
        // background
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // camera
        camera.update();

        // map
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

		// drawing
		batch.begin();
		spriteCharacter.setPosition(spriteX, spriteY);
		spriteCharacter.draw(batch);
		batch.end();
		spriteMove();
	}

	public void spriteMove() {
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			spriteX--;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			spriteX++;
			System.out.println(spriteX);
		if(Gdx.input.isKeyPressed(Keys.UP))
			spriteY++;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			spriteY--;
	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
		spriteTexture.dispose();
	}

    @Override
    public boolean keyDown(int keycode) {
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
}
