package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.exgames.xenos.Main;

/**
 * Created by Alex on 09.04.2017.
 */
public class ButtonNewGame extends ButtonMenu {
    public ButtonNewGame(Texture atlas, int yNumb, int width, int height, float x, float y) {
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
                Main.menu.newWorld();
                clicked = true;
                updateSprite();
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
