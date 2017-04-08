package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Alex on 08.04.2017.
 */
public class Menu implements Screen {

    private ButtonMenu buttonContine;
    private ButtonMenu buttonNewGame;
    private ButtonMenu buttonOption;
    private ButtonMenu buttonExit;
    private ButtonMenu buttonBack;

    private InputController controller;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Animator runner;
    private Sprite starsSprite1;
    private Sprite starsSprite2;
    private Sprite starsSprite3;
    private Sprite starsSprite1clone;
    private Sprite starsSprite2clone;
    private Sprite starsSprite3clone;
    private Sprite xenosLogo;

    private float fadeMenu = 0f;

    private Game game;

    public Menu(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        //runner = new Animator("animation_sheet.png");
        //camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());

        Texture texStars = new Texture(Gdx.files.internal("background/atlasStars.png"));
        texStars.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion texstars1 = new TextureRegion(texStars, 0, 0, 1024, 1024);
        TextureRegion texstars2 = new TextureRegion(texStars, 1024, 0, 1024, 1024);
        TextureRegion texstars3 = new TextureRegion(texStars, 2048, 0, 1024, 1024);
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

        Texture texXenosLogo = new Texture(Gdx.files.internal("xenos.png"));
        texXenosLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        xenosLogo = new Sprite(texXenosLogo);
        xenosLogo.setSize(xenosLogo.getWidth()/1.3f,xenosLogo.getHeight()/1.3f);
        xenosLogo.setPosition(0,490);
        Texture texButtons = new Texture(Gdx.files.internal("buttons/atlasbuttons.png"));
        buttonContine = new ButtonMenu(texButtons, 0, 174, 25);
        buttonNewGame = new ButtonMenu(texButtons, 1, 152, 25);
        buttonOption = new ButtonMenu(texButtons, 2, 85, 25);
        buttonExit = new ButtonMenu(texButtons, 3, 97, 25);
        buttonBack = new ButtonMenu(texButtons, 4, 90, 25);
        buttonContine.setPosition(20, 420-20);
        buttonNewGame.setPosition(20, 350-20);
        buttonOption.setPosition(20, 280-20);
        buttonExit.setPosition(20, 210-20);
        buttonBack.setPosition(20, 140-20);

        controller = new InputController();
        Gdx.input.setInputProcessor(controller);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (fadeMenu != 1){
            fadeMenu = fadeIn(fadeMenu);
        }
        starsSprite1.setPosition(starsSprite1.getX() - 0.1f, starsSprite1.getY());
        starsSprite2.setPosition(starsSprite2.getX() - 0.15f, starsSprite2.getY());
        starsSprite3.setPosition(starsSprite3.getX() - 0.2f, starsSprite3.getY());
        outOfScreenCheck(starsSprite1, starsSprite1clone, 0.1f);
        outOfScreenCheck(starsSprite2, starsSprite2clone, 0.15f);
        outOfScreenCheck(starsSprite3, starsSprite3clone, 0.2f);
        starsSprite1.draw(batch, fadeMenu);
        starsSprite2.draw(batch, fadeMenu);
        starsSprite3.draw(batch, fadeMenu);
        xenosLogo.draw(batch, fadeMenu);
        buttonContine.draw(batch, fadeMenu);
        buttonNewGame.draw(batch, fadeMenu);
        buttonOption.draw(batch, fadeMenu);
        buttonExit.draw(batch, fadeMenu);
        batch.end();
    }

    /*Смотрит, вышел ли спрайт за пределы экрана. Если вышел - отрисовывает клон ровна справа до тех пор,
    пока тот не скроется. Если оригинал скрылся за пределы экрана - прекращаем отрисовывать клон и ставим оригинал на его место.*/
    public void outOfScreenCheck (Sprite sprite, Sprite SpriteClone, float alias){
        if (-sprite.getX() + Gdx.graphics.getWidth() >= sprite.getScaleX() + sprite.getWidth() & !(-sprite.getX() >= sprite.getWidth())){
            SpriteClone.setPosition(sprite.getX() + 2048, 0);
            SpriteClone.draw(batch, fadeMenu);
        } else if (-sprite.getX() >= sprite.getWidth()){
            sprite.setPosition(SpriteClone.getX() - alias, 0);
        }
    }

    public float fadeIn(float fade){
        if (fade < 1) {
            fade = fade + 0.01f;
        }
        if (fade >= 1) {
            fade = 1;
        }
        return fade;
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Изменён размер! Теперь он " + width + " на " + height);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }
}
