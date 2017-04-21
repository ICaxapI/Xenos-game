package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Alex on 08.04.2017.
 */
public class LogoShow implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Sprite logo;
    private float fadeLogo = 0f;
    private boolean completelogo = false;
    private Texture texLogo;
    private TimerTask timerTask;
    private static Timer timer;
    private boolean timerStart;

    public LogoShow(Game game, SpriteBatch batch){
        this.game = game;
        this.batch = batch;
    }

    @Override
    public void show() {
        texLogo = new Texture(Gdx.files.internal("resources/FinalNEW.png"));
        texLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new Sprite(texLogo);
        logo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/2);
        logo.setPosition(0,((Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/2))/2));
        timerTask = new MyTimerTask();
        timer = new Timer();

    }
    public void stopTimer(){
        timer.cancel();
        timerTask.cancel();
    }
    @Override
    public void render(float delta) {
        if (!completelogo) {
            if (fadeLogo < 1) {
                fadeLogo = fadeLogo + delta;
            }
            if (fadeLogo >= 1) {
                fadeLogo = 1;
                if (!timerStart){
                    timerStart = true;
                    timer.scheduleAtFixedRate(timerTask, 1000, 1000);
                }
            }
        } else {
            if (fadeLogo <= 1) {
                fadeLogo = fadeLogo - delta;
            }
            if (fadeLogo <= 0) {
                fadeLogo = 0;
                completelogo = false;
                game.setScreen(Main.menu);
            }
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        logo.draw(batch, fadeLogo);
        batch.end();
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
        texLogo.dispose();
    }
    class MyTimerTask extends TimerTask {
        MyTimerTask( ) {
        }
        public void run( ) {
            completelogo = true;
            stopTimer();
        }

    }
}
