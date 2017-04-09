package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.exgames.xenos.Main;

/**
 * Created by Alex on 06.04.2017.
 */
public class ButtonMenu extends Actor{
    public boolean enable = true;
    public boolean active = true;
    boolean clicked = false;
    boolean mousehere = false;
    InputListener listener;
    private TextureRegion buttonTex;
    private TextureRegion buttonTexActivated;
    private TextureRegion buttonTexDeactivated;
    private TextureRegion buttonTexClicked;
    private Sprite mySprite;

    public ButtonMenu(Texture atlas, int yNumb, int width, int height, float x, float y){
        buttonTex = new TextureRegion(atlas, 0, yNumb*height, width, height);
        buttonTexActivated = new TextureRegion(atlas, (atlas.getWidth()/4), yNumb*height, width, height);
        buttonTexDeactivated = new TextureRegion(atlas, (atlas.getWidth()/4)*2, yNumb*height, width, height);
        buttonTexClicked = new TextureRegion(atlas, (atlas.getWidth()/4)*3, yNumb*height, width, height);
        mySprite = new Sprite(buttonTex);
        mySprite.setSize(width*2, height*2);
        mySprite.setBounds(x, y, mySprite.getWidth(), mySprite.getHeight());
        setBounds(x, y, mySprite.getWidth(), mySprite.getHeight());
        setPosition(x, y);
        mySprite.setPosition(x, y);
        listener = new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchUp");
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                System.out.println("touchDragged");
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                System.out.println("scrolled");
                return super.scrolled(event, x, y, amount);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                System.out.println("keyDown");
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                System.out.println("keyUp");
                return super.keyUp(event, keycode);
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                System.out.println("KeyTyped");
                return super.keyTyped(event, character);
            }

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

    public void updateSprite(){
        if (active) {
            if (mousehere) {
                if (clicked) {
                    mySprite.set(new Sprite(buttonTexClicked));
                } else {
                    mySprite.set(new Sprite(buttonTexActivated));
                }
            } else {
                mySprite.set(new Sprite(buttonTex));
            }
        } else {
            mySprite.set(new Sprite(buttonTexDeactivated));
        }
    }

    @Override
    public void draw(Batch batch, float alpha){
        if (enable) {
            mySprite.draw(batch, Main.menu.fadeMenu);
        }
    }

    @Override
    public void act(float delta){
        mySprite.setBounds(getX(), getY(), getWidth(), getHeight());
        super.act(delta);
    }
}
