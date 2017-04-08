package com.exgames.xenos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Alex on 06.04.2017.
 */
public class ButtonMenu extends Actor{
    public boolean active = true;
    InputListener listener;
    private TextureRegion buttonTex;
    private TextureRegion buttonTexActivated;
    private Sprite mySprite;

    public ButtonMenu(Texture atlas, int yNumb, int width, int height, float x, float y){
        buttonTex = new TextureRegion(atlas, 0, yNumb*height, width, height);
        buttonTexActivated = new TextureRegion(atlas, (atlas.getWidth()/2), yNumb*height, width, height);
        mySprite = new Sprite(buttonTex);
        mySprite.setSize(width*2, height*2);
        mySprite.setBounds(x, y, mySprite.getWidth(), mySprite.getHeight());
        setBounds(x, y, mySprite.getWidth(), mySprite.getHeight());
        setPosition(x, y);
        mySprite.setPosition(x, y);
        listener = new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                mySprite.set(new Sprite(buttonTexActivated));
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                mySprite.set(new Sprite(buttonTex));
                super.exit(event, x, y, pointer, toActor);
            }
        };
        addListener(listener);
    }

    @Override
    public void draw(Batch batch, float alpha){
        if (active) {
            mySprite.draw(batch, Main.menu.fadeMenu);
        }
    }

    @Override
    public void act(float delta){
        mySprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }
}
