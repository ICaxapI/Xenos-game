package com.exgames.xenos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture logotex;
	Texture buttonstex;
	Animator runner;
	Sprite logo;
	float fade = 0f;
	boolean completelogo = false;
	boolean logoexit = false;
	public Timer timer = new Timer(1000, e -> {
		completelogo = true;
	});

	@Override
	public void create () {
		batch = new SpriteBatch();
		//runner = new Animator("animation_sheet.png");

		logotex = new Texture(Gdx.files.internal("FinalNEW.png"));
		logotex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		logo = new Sprite(logotex);
		logo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/2);
		logo.setPosition(0,((Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/2))/2));

		buttonstex = new Texture(Gdx.files.internal("buttons/atlasbuttons.png"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		whatRenderNow();
        batch.draw(buttonstex, 0,0);
		batch.end();
	}
	public void whatRenderNow(){
		if (!logoexit) {
			showlogo(completelogo);
		} else {

		}
	}
	public void showlogo(boolean in){
		if (!in & !logoexit) {
			if (fade < 1) {
				logo.draw(batch, fade);
				fade = fade + 0.01f;
			}
			if (fade >= 1) {
				fade = 1;
				logo.draw(batch, fade);
				timer.start();
			}
		} else if (!logoexit) {
			if (fade <= 1) {
				logo.draw(batch, fade);
				fade = fade - 0.01f;
			}
			if (fade <= 0) {
				fade = 0;
				timer.stop();
				completelogo = false;
				logoexit = true;
			}
		}
	}
	@Override
	public void resize (int width, int height) {
		System.out.println("Изменён размер! Теперь он " + width + " на " + height);
	}

	@Override
	public void pause () {

	}

	@Override
	public void resume () {

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
