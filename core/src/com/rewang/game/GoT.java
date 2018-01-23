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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

        // map
        map = new TmxMapLoader().load("maps/indoors.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(this);

        // character
		batch = new SpriteBatch();
//		FileHandle spriteFileHandle = Gdx.files.internal("sprites/idle_girl.png");
		spriteTexture = new Texture(Gdx.files.internal("sprites/pikachu.png"));
		spriteCharacter = new Sprite(spriteTexture);

        // character map layer
        // the following is C/P from website
//        // Create a new map layer
//        TiledMapTileLayer tileLayer = new TiledMapTileLayer(mapWidth,mapHeight,64,64);
//
//        // Create a cell(tile) to add to the layer
//        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
//
//        // The sprite/tilesheet behind our new layer is a single image (our sprite)
//        // Create a TextureRegion that is the entire size of our texture
//        TextureRegion textureRegion = new TextureRegion(texture,64,64);
//
//        // Now set the graphic for our cell to our newly created region
//        cell.setTile(new StaticTiledMapTile(textureRegion));
//
//        // Now set the cell at position 4,10 ( 8,20 in map coordinates ).  This is the position of a tree
//        // Relative to 0,0 in our map which is the bottom left corner
//        tileLayer.setCell(4,10,cell);
//
//        // Ok, I admit, this part is a gross hack.
//        // Get the current top most layer from the map and store it
//        MapLayer tempLayer = tiledMap.getLayers().get(tiledMap.getLayers().getCount()-1);
//        // Now remove it
//        tiledMap.getLayers().remove(tiledMap.getLayers().getCount()-1);
//        // Now add our newly created layer
//        tiledMap.getLayers().add(tileLayer);
//        // Now add it back, now our new layer is not the top most one.
//        tiledMap.getLayers().add(tempLayer);

	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // camera
        camera.update();

        // map
        mapRenderer.setView(camera);
        mapRenderer.render();

		// character
		batch.begin();
        batch.draw(spriteTexture, spriteX, spriteY, spriteCharacter.getHeight()/2, spriteCharacter.getWidth()/2);
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
