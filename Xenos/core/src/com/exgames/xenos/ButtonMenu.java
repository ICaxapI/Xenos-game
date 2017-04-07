package com.exgames.xenos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Alex on 06.04.2017.
 */
public class ButtonMenu {
    public boolean active = false;
    private TextureRegion buttonTex;
    private TextureRegion buttonTexActivated;
    private Sprite mySprite;

    public ButtonMenu(Texture atlas, int yNumb, int width, int height){
        buttonTex = new TextureRegion(atlas, 0, yNumb*height, width, height);
        buttonTexActivated = new TextureRegion(atlas, (atlas.getWidth()/2), yNumb*height, width, height);
        mySprite = new Sprite(buttonTex);
        mySprite.setSize(width*2, height*2);
    }

    public void setPosition(int x, int y){
        mySprite.setX(x);
        mySprite.setY(y);
    }

    public float getX(){
        return mySprite.getX();
    }

    public float getY(){
        return mySprite.getY();
    }

    public void needRender(Batch batch, float fade){
        mySprite.draw(batch, fade);
    }
}
