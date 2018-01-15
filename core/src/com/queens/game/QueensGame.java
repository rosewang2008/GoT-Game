package com.queens.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

public class QueensGame extends ApplicationAdapter {
	SpriteBatch batch;
	Animation<TextureRegion> walking;
	Texture walkSheet;
	float stateTime;

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


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion frame = walking.getKeyFrame(stateTime, true);
		batch.begin();
		batch.draw(frame, 50, 50);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		walkSheet.dispose();
	}
}
