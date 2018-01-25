package com.rewang.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;

// first class that will be run!
public class GoT extends ApplicationAdapter implements InputProcessor {
    // character
	private SpriteBatch batch;
	private Texture spriteTexture;
	private Sprite spriteCharacter;
	private int spriteX = 0;
	private int spriteY = 0 ;

    // character map layer
    TiledMapTileLayer spriteLayer;

    // character movements
	Animation<TextureRegion> walking;
	Texture walkSheet;
	float stateTime;

    // background
    TiledMap map;
    TiledMapRenderer mapRenderer;
    MapObjects objects;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;

    // view
    OrthographicCamera camera;

	@Override
	public void create () {
        // game dimensions
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        System.out.println(w);
        System.out.println(h);

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();


        // Object layer
//        MapLayer layer = map.getLayers().get("GameObjects");
//        MapObjects objects = layer.getObjects();
//        System.out.println(objects);

        // character
		batch = new SpriteBatch();
//		FileHandle spriteFileHandle = Gdx.files.internal("sprites/idle_girl.png");
		spriteTexture = new Texture(Gdx.files.internal("sprites/pikachu.png"));
		spriteCharacter = new Sprite(spriteTexture);

        // map
        map = new TmxMapLoader().load("maps/indoors.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(map);
        tiledMapRenderer.addSprite(spriteCharacter);
//        mapRenderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(this);

	}


	@Override
	public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // camera
        camera.update();

        // map
//        mapRenderer.setView(camera);
//        mapRenderer.render();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

		// character
		batch.begin();
        batch.draw(spriteTexture, spriteX, spriteY, spriteCharacter.getHeight()/2, spriteCharacter.getWidth()/2);
        spriteMove();
        batch.end();

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
        map.dispose();
	}

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
//        if(keycode == Input.Keys.LEFT)
//            camera.translate(32,0);
//        if(keycode == Input.Keys.RIGHT)
//            camera.translate(-32,0);
//        if(keycode == Input.Keys.UP)
//            camera.translate(0,-32);
//        if(keycode == Input.Keys.DOWN)
//            camera.translate(0,32);
//        if(keycode == Input.Keys.NUM_1)
//            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
//        if(keycode == Input.Keys.NUM_2)
//            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
//        return false;
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
