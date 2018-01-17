package com.rewang.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
// first class that will be run!
public class GoT extends Game {
	// batch: used to draw things on the screen
	// one spritebatch only!
	private SpriteBatch batch;
	private Pixmap pixmap;
	private Sprite sprite;
	private Texture texture;
//	private Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		// Pixmap: raw image in memry
		pixmap = new Pixmap(256, 128, Pixmap.Format.RGBA8888);

		// Red fill in
		pixmap.setColor(Color.RED);
		pixmap.fill();


		texture = new Texture(Gdx.files.internal("plane.png"));
		sprite = new Sprite(texture);
//		img = new Texture("plane.jpg");
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
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
	}
}
