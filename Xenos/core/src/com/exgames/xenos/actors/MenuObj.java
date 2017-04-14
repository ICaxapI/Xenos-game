package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.exgames.xenos.Main;

/**
 * Created by Alex on 08.04.2017.
 */
public class MenuObj extends Actor {
    private Sprite sprite;
    private Texture texture;

    public MenuObj(Texture texture, float x, float y, float scale){

        sprite = new Sprite(texture);
        sprite.setBounds(x, y, texture.getWidth(), texture.getHeight());
        setSize(texture.getWidth()*scale, texture.getHeight()*scale);
        setBounds(x, y, getWidth(), getHeight());
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float alpha){
            sprite.draw(batch, Main.menu.fadeMenu);
    }

    @Override
    public void act(float delta){
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        super.act(delta);
    }
}
