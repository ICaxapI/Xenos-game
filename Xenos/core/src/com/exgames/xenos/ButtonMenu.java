package com.exgames.xenos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 06.04.2017.
 */
public class ButtonMenu extends Actor{
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

}
