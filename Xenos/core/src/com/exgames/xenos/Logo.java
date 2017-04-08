package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;

/**
 * Created by Alex on 08.04.2017.
 */
public class Logo implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Sprite logo;
    private float fadeLogo = 0f;
    private boolean completelogo = false; //Ставить true для скипа лого
    private Timer timer = new Timer(1000, e -> {
        completelogo = true;
    });

    public Logo(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        Texture texLogo = new Texture(Gdx.files.internal("FinalNEW.png"));
        texLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new Sprite(texLogo);
        logo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/2);
        logo.setPosition(0,((Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/2))/2));
    }

    @Override
    public void render(float delta) {
        if (!completelogo) {
            if (fadeLogo < 1) {
                fadeLogo = fadeLogo + 0.01f;
            }
            if (fadeLogo >= 1) {
                fadeLogo = 1;
                timer.start();
            }
        } else {
            if (fadeLogo <= 1) {
                fadeLogo = fadeLogo - 0.01f;
            }
            if (fadeLogo <= 0) {
                fadeLogo = 0;
                timer.stop();
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
        batch.dispose();
    }
}
