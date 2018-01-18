package com.rewang.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;

import java.util.Arrays;

// first class that will be run!
public class GoT implements ApplicationListener {
	private SpriteBatch batch;
	private int spriteX;
	private int spriteY;
	private Sprite spriteCharacter;
	private Texture spriteTexture;
	Animation<TextureRegion> walking;
	Texture walkSheet;
	float stateTime;
	float spriteSpeed = 10.0f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		FileHandle spriteFileHandle = Gdx.files.internal("sprites/idle_girl.png");
		spriteTexture = new Texture(spriteFileHandle);
		spriteCharacter = new Sprite(spriteTexture, 0, 158, 32, 64);
		spriteX = 0;
		spriteY = 0;
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
		System.out.println(Gdx.input.isKeyPressed(Keys.LEFT));

		if (Gdx.input.isKeyPressed(Keys.LEFT))
			spriteX -= Gdx.graphics.getDeltaTime() * spriteSpeed;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			spriteX += Gdx.graphics.getDeltaTime() * spriteSpeed;
		if(Gdx.input.isKeyPressed(Keys.UP))
			spriteY += Gdx.graphics.getDeltaTime() * spriteSpeed;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			spriteY -= Gdx.graphics.getDeltaTime() * spriteSpeed;
		batch.begin();
		batch.draw(spriteCharacter, (int)spriteX, (int)spriteY);
		batch.end();
//		Gdx.gl.glClearColor(ddd1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		stateTime += Gdx.graphics.getDeltaTime();
//		TextureRegion frame = walking.getKeyFrame(stateTime, true);
//		batch.begin();
//		batch.draw(frame, 50, 50);
//		batch.end();
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
