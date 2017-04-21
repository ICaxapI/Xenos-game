package com.exgames.xenos.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.exgames.xenos.Main;

/**
 * Created by 78122 on 10.04.2017.
 */
public class ButtonExit extends  ButtonMenu {
    public ButtonExit(Texture atlas, int yNumb, int width, int height, float x, float y) {
        super(atlas, yNumb, width, height, x, y);
        removeListener(listener);
        listener = new InputListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                mousehere = true;
                updateSprite();
                return super.mouseMoved(event, x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clicked = true;
                updateSprite();
                Main.menu.dispose();
                Gdx.app.exit();
                System.exit(0);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                mousehere = true;
                updateSprite();
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                mousehere = false;
                clicked = false;
                updateSprite();
                super.exit(event, x, y, pointer, toActor);
            }
        };
        addListener(listener);
    }
}
