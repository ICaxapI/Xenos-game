package com.exgames.xenos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture logotex;
	Animator runner;
	Sprite logo;
	float fade = 0f;
	boolean notcomplete = true;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//runner = new Animator("animation_sheet.png");
		logotex = new Texture(Gdx.files.internal("FinalNEW.png"));
		logotex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		logo = new Sprite(logotex);
		logo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/2);
		logo.setPosition(0,((Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/2))/2));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		logo.draw(batch, fade);
		batch.end();
		fadeswitch(notcomplete);
	}

	public void fadeswitch(boolean in){
		if (in) {
			if (fade < 1) {
				fade = fade + 0.01f;
			}
			if (fade >= 1) {
				fade = 1;
				notcomplete = false;
			}
		} else {
			if (fade <= 1) {
				fade = fade - 0.01f;
			}
			if (fade <= 0) {
				fade = 0;
				notcomplete = true;
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
