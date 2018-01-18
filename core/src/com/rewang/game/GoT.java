package com.rewang.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;

// first class that will be run!
public class GoT implements ApplicationListener {
	private SpriteBatch batch;
	private Texture spriteTexture;
	private Sprite spriteCharacter;

	private int spriteX = 0;
	private int spriteY = 0 ;
	float spriteSpeed = 10.0f;

	Animation<TextureRegion> walking;
//	Texture walkSheet;
//	float stateTime;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		FileHandle spriteFileHandle = Gdx.files.internal("sprites/idle_girl.png");
		spriteTexture = new Texture(spriteFileHandle);
		spriteCharacter = new Sprite(spriteTexture);
//		walkSheet = new Texture("sprites/girl_sprite.png");
//		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/13, walkSheet.getHeight()/21);
//		TextureRegion[] walkFrames = Arrays.copyOfRange(tmp[11], 0, 9);
//		walking = new Animation<TextureRegion>(0.1f, walkFrames);
//		stateTime = 0f;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// drawing
		batch.begin();
		spriteCharacter.setPosition(spriteX, spriteY);
		spriteCharacter.draw(batch);
		batch.end();
		spriteMove();
	}

	public void spriteMove() {
		System.out.println(Gdx.input.isKeyPressed(Keys.LEFT));
		System.out.println(Gdx.input.isKeyPressed(Keys.RIGHT));
		System.out.println(Gdx.input.isKeyPressed(Keys.UP));
		System.out.println(Gdx.input.isKeyPressed(Keys.DOWN));
		System.out.println();
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			spriteX--;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			spriteX++;
			System.out.println(spriteX);
		if(Gdx.input.isKeyPressed(Keys.UP))
			spriteY++;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			spriteY--;
//			spriteY -= Gdx.graphics.getDeltaTime() * spriteSpeed;
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
}
