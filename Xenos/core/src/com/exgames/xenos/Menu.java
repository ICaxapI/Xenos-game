package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exgames.xenos.actors.*;
import com.exgames.xenos.maps.NewGame;


/**
 * Created by Alex on 08.04.2017.
 */
public class Menu implements Screen {
    public boolean openOptions;

    private Texture texXenosLogo;
    private Texture texStars;
    private Texture texButtons;
    private Texture texButtonsFrame;

    private ButtonMenu buttonContine;
    private ButtonMenu buttonNewGame;
    private ButtonMenu buttonOption;
    private ButtonMenu buttonExit;
    private ButtonMenu buttonBack;
    private MenuObj xenosLogo;
    private MenuObj buttonsFrame;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    private Music music;

    private Sprite starsSprite1;
    private Sprite starsSprite2;
    private Sprite starsSprite3;
    private Sprite starsSprite1clone;
    private Sprite starsSprite2clone;
    private Sprite starsSprite3clone;

    public float fadeMenu = 0f;

    private Game game;

    Menu(Game game, SpriteBatch batch, OrthographicCamera camera, Viewport viewport){
        this.game = game;
        this.batch = batch;
        this.camera = camera;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        //runner = new Animator("animation_sheet.png");
        if (!Main.skipLogo){
            Main.logo.dispose();
            Main.logo = null;
        }
        stage = new Stage(viewport);
        music = Gdx.audio.newMusic(Gdx.files.internal("resources/music/loop.ogg"));
        music.setLooping(true);
        music.setVolume(Main.volumeMusic);
        if (!music.isPlaying()){
            music.play();
        }
        texStars = new Texture(Gdx.files.internal("resources/background/atlasStars.png"));
        texStars.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion texstars1 = new TextureRegion(texStars, 0, 0, 1024, 1024);
        TextureRegion texstars2 = new TextureRegion(texStars, 1024, 0, 1024, 1024);
        TextureRegion texstars3 = new TextureRegion(texStars, 2048, 0, 1024, 1024);
        starsSprite1 = new Sprite(texstars1);
        starsSprite2 = new Sprite(texstars2);
        starsSprite3 = new Sprite(texstars3);
        starsSprite1.setSize(1536,1536);
        starsSprite2.setSize(1536,1536);
        starsSprite3.setSize(1536,1536);
        starsSprite1clone = new Sprite();
        starsSprite2clone = new Sprite();
        starsSprite3clone = new Sprite();
        starsSprite1clone.set(starsSprite1);
        starsSprite2clone.set(starsSprite2);
        starsSprite3clone.set(starsSprite3);

        texXenosLogo = new Texture(Gdx.files.internal("resources/xenos.png"));
        texXenosLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        xenosLogo = new MenuObj(texXenosLogo, 25, 480-60, 0.7f);

        texButtons = new Texture(Gdx.files.internal("resources/buttons/atlasbuttons.png"));
        texButtons.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        buttonContine = new ButtonMenu(texButtons, 0, 174, 25, 45, 420-50-60);
        buttonNewGame = new ButtonNewGame(texButtons, 1, 152, 25, 45, 350-50-60);
        buttonOption = new ButtonOptionSwitch(texButtons, 2, 85, 25, 45, 280-50-60);
        buttonExit = new ButtonExit(texButtons, 3, 97, 25, 45, 210-50-60);
        buttonBack = new ButtonOptionSwitch(texButtons, 4, 90, 25, 0-xenosLogo.getWidth(), 140-50-60);
        texButtonsFrame = new Texture(Gdx.files.internal("resources/buttons/frame.png"));
        buttonsFrame = new MenuObj(texButtonsFrame, -35,75, 1.5f);

        stage.addActor(buttonsFrame);
        stage.addActor(xenosLogo);
        stage.addActor(buttonContine);
        stage.addActor(buttonNewGame);
        stage.addActor(buttonOption);
        stage.addActor(buttonExit);
        stage.addActor(buttonBack);

        buttonContine.active = false;
        buttonContine.updateSprite();

        //controller = new InputController();
        Gdx.input.setInputProcessor(stage);
    }
    public void optionSwith(float time){
        if (!openOptions) {
            xenosLogo.addAction(Actions.moveTo(25 - buttonsFrame.getWidth(), xenosLogo.getY(), time));
            buttonContine.addAction(Actions.moveTo(45 - buttonsFrame.getWidth(), buttonContine.getY(), time));
            buttonNewGame.addAction(Actions.moveTo(45 - buttonsFrame.getWidth(), buttonNewGame.getY(), time));
            buttonOption.addAction(Actions.moveTo(45 - buttonsFrame.getWidth(), buttonOption.getY(), time));
            buttonExit.addAction(Actions.moveTo(45 - buttonsFrame.getWidth(), buttonExit.getY(), time));
            buttonsFrame.addAction(Actions.moveTo(-35 - buttonsFrame.getWidth(), buttonsFrame.getY(), time));
            buttonBack.addAction(Actions.moveTo(20, buttonBack.getY(), time));
            openOptions = true;
        } else {
            xenosLogo.addAction(Actions.moveTo(25, xenosLogo.getY(), time));
            buttonContine.addAction(Actions.moveTo(45, buttonContine.getY(), time));
            buttonNewGame.addAction(Actions.moveTo(45, buttonNewGame.getY(), time));
            buttonOption.addAction(Actions.moveTo(45, buttonOption.getY(), time));
            buttonExit.addAction(Actions.moveTo(45, buttonExit.getY(), time));
            buttonBack.addAction(Actions.moveTo(0 - buttonsFrame.getWidth(), buttonBack.getY(), time));
            buttonsFrame.addAction(Actions.moveTo(-35, buttonsFrame.getY(), time));
            openOptions = false;
        }
    }

    public void newWorld(){
        Screen physics = new NewGame(game, batch, viewport);
        music.stop();
        game.setScreen(physics);
    }

    @Override
    public void render(float delta) {
        float alias1 = (0.1f*delta*100);
        float alias2 = (0.15f*delta*100);
        float alias3 = (0.2f*delta*100);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (fadeMenu < 1){
            fadeMenu += delta;
            if (fadeMenu > 1) {
                fadeMenu = 1;
            }
        }
        starsSprite1.setPosition(starsSprite1.getX() - alias1, starsSprite1.getY());
        starsSprite2.setPosition(starsSprite2.getX() - alias2, starsSprite2.getY());
        starsSprite3.setPosition(starsSprite3.getX() - alias3, starsSprite3.getY());
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        outOfScreenCheck(starsSprite1, starsSprite1clone, alias1);
        outOfScreenCheck(starsSprite2, starsSprite2clone, alias2);
        outOfScreenCheck(starsSprite3, starsSprite3clone, alias3);
        starsSprite1.draw(batch, fadeMenu);
        starsSprite2.draw(batch, fadeMenu);
        starsSprite3.draw(batch, fadeMenu);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    /*Смотрит, вышел ли спрайт за пределы экрана. Если вышел - отрисовывает клон ровна справа до тех пор,
    пока тот не скроется. Если оригинал скрылся за пределы экрана - прекращаем отрисовывать клон и ставим оригинал на его место.*/
    private void outOfScreenCheck(Sprite sprite, Sprite SpriteClone, float alias){
        if (-sprite.getX() + camera.viewportWidth >= sprite.getWidth() & !(-sprite.getX() >= sprite.getWidth())){
            SpriteClone.setPosition(sprite.getX() + sprite.getWidth(), 0);
            SpriteClone.draw(batch, fadeMenu);
        } else if (-sprite.getX() >= sprite.getWidth()){
            sprite.setPosition(SpriteClone.getX() - alias, 0);
        }
    }

    private float fadeIn(float fade){
        if (fade < 1) {
            fade = fade + Gdx.graphics.getDeltaTime();
        }
        if (fade >= 1) {
            fade = 1;
        }
        return fade;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        music.dispose();
        stage.dispose();
        texButtons.dispose();
        texStars.dispose();
        texXenosLogo.dispose();
        texButtonsFrame.dispose();
    }
}
