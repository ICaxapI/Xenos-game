package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 18.04.2017.
 */
public class Hero extends Actor {
    private Sprite mySprite;

    public Hero(Texture texture, float x, float y, float width, float height){
        mySprite = new Sprite(texture);
        mySprite.setSize(width, height);
        mySprite.setBounds(x, y, mySprite.getWidth(), mySprite.getHeight());
        setBounds(x, y, mySprite.getWidth(), mySprite.getHeight());
        setPosition(x, y);
        mySprite.setPosition(x, y);
    }

    public void updateSprite(){

    }

    @Override
    public void draw(Batch batch, float alpha){
        mySprite.draw(batch);
    }

    @Override
    public void act(float delta){
        mySprite.setBounds(getX(), getY(), getWidth(), getHeight());
        super.act(delta);
    }
}
