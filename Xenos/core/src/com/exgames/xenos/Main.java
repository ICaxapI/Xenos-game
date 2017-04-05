package com.exgames.xenos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Animator runner;
	Animator runner1;
	Animator runner2;
	Animator runner3;
	Animator runner4;
	Animator runner5;
	Animator runner6;
	Animator runner7;
	Animator runner8;
	Animator runner9;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture(Gdx.files.internal("FinalNEW.png"));
		runner = new Animator();
		runner1 = new Animator();
		runner2 = new Animator();
		runner3 = new Animator();
		runner4 = new Animator();
		runner5 = new Animator();
		runner6 = new Animator();
		runner7 = new Animator();
		runner8 = new Animator();
		runner9 = new Animator();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(runner.needrender(), 50, 50); // #17
		batch.draw(runner1.needrender(), 100, 50); // #17
		batch.draw(runner2.needrender(), 50, 100); // #17
		batch.draw(runner3.needrender(), 0, 0); // #17
		batch.draw(runner4.needrender(), 200, 200); // #17
		batch.draw(runner5.needrender(), 200, 50); // #17
		batch.draw(runner6.needrender(), 50, 200); // #17
		batch.draw(runner7.needrender(), 150, 150); // #17
		batch.draw(runner8.needrender(), 150, 50); // #17
		batch.draw(runner9.needrender(), 50, 150); // #17
		batch.end();
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
		img.dispose();
	}
}
