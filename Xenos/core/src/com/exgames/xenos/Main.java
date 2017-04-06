package com.exgames.xenos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.swing.*;

public class Main extends ApplicationAdapter {
    float x = 0;

    private ButtonMenu buttonContine;
    private ButtonMenu buttonNewGame;
    private ButtonMenu buttonOption;
    private ButtonMenu buttonExit;
    private ButtonMenu buttonBack;

    private InputController controller;
    private OrthographicCamera camera;
	SpriteBatch batch;

	private Texture texLogo;
	private Texture texButtons;
    private Texture texStars;

    private TextureRegion texstars1;
    private TextureRegion texstars2;
    private TextureRegion texstars3;

	private Animator runner;
	private Sprite logo;
    private Sprite starsSprite1;
    private Sprite starsSprite2;
    private Sprite starsSprite3;
    private Sprite starsSprite1clone;
    private Sprite starsSprite2clone;
    private Sprite starsSprite3clone;

	private float fade = 0f;

	private boolean completelogo = true;
	private boolean logoexit = false;
	private Timer timer = new Timer(1000, e -> {
		completelogo = true;
	});

	@Override
	public void create () {
		batch = new SpriteBatch();
		//runner = new Animator("animation_sheet.png");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        Gdx.graphics.setTitle("Xenos");


		texLogo = new Texture(Gdx.files.internal("FinalNEW.png"));
		texLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		logo = new Sprite(texLogo);
		logo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/2);
		logo.setPosition(0,((Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/2))/2));

        texStars = new Texture (Gdx.files.internal("background/atlasStars.png"));
        texStars.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texstars1 = new TextureRegion(texStars, 0, 0, 1024, 1024);
        texstars2 = new TextureRegion(texStars, 1024, 0, 1024, 1024);
        texstars3 = new TextureRegion(texStars, 2048, 0, 1024, 1024);
        starsSprite1 = new Sprite(texstars1);
        starsSprite2 = new Sprite(texstars2);
        starsSprite3 = new Sprite(texstars3);
        starsSprite1.setSize(2048,2048);
        starsSprite2.setSize(2048,2048);
        starsSprite3.setSize(2048,2048);
        starsSprite1clone = new Sprite();
        starsSprite2clone = new Sprite();
        starsSprite3clone = new Sprite();
        starsSprite1clone.set(starsSprite1);
        starsSprite2clone.set(starsSprite2);
        starsSprite3clone.set(starsSprite3);


		texButtons = new Texture(Gdx.files.internal("buttons/atlasbuttons.png"));
        buttonContine = new ButtonMenu(texButtons, 0, 174, 25);
        buttonNewGame = new ButtonMenu(texButtons, 1, 152, 25);
        buttonOption = new ButtonMenu(texButtons, 2, 85, 25);
        buttonExit = new ButtonMenu(texButtons, 3, 97, 25);
        buttonBack = new ButtonMenu(texButtons, 4, 90, 25);

        controller = new InputController();
        Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		whatRenderNow();
		batch.end();
	}
	public void whatRenderNow(){
		if (!logoexit) {
			showlogo(completelogo);
		} else {
            starsSprite1.setPosition(starsSprite1.getX() - 0.1f, starsSprite1.getY());
            starsSprite2.setPosition(starsSprite2.getX() - 0.15f, starsSprite2.getY());
            starsSprite3.setPosition(starsSprite3.getX() - 0.2f, starsSprite3.getY());
            outOfScreenCheck(starsSprite1, starsSprite1clone, 0.1f);
            outOfScreenCheck(starsSprite2, starsSprite2clone, 0.15f);
            outOfScreenCheck(starsSprite3, starsSprite3clone, 0.2f);
            starsSprite1.draw(batch);
            starsSprite2.draw(batch);
            starsSprite3.draw(batch);
		}
	}

    /*Смотрит, вышел ли спрайт за пределы экрана. Если вышел - отрисовывает клон ровна справа до тех пор,
    пока тот не скроется. Если оригинал скрылся за пределы экрана - прекращаем отрисовывать клон и ставим оригинал на его место.*/
    public void outOfScreenCheck (Sprite sprite, Sprite SpriteClone, float alias){
        if (-sprite.getX() + Gdx.graphics.getWidth() >= sprite.getScaleX() + sprite.getWidth() & !(-sprite.getX() >= sprite.getWidth())){
            SpriteClone.setPosition(sprite.getX() + 2048, 0);
            SpriteClone.draw(batch);
        } else if (-sprite.getX() >= sprite.getWidth()){
            sprite.setPosition(SpriteClone.getX() - alias, 0);
            System.out.println("ХОБА");
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
