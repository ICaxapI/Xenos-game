package com.exgames.xenos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Alex on 06.04.2017.
 */
public class ButtonMenu {
    private TextureRegion buttonTex;
    private TextureRegion buttonTexActivated;

    public ButtonMenu(Texture atlas, int yNumb, int width, int height){
        buttonTex = new TextureRegion(atlas, 0, yNumb*height, width, height);
        buttonTexActivated = new TextureRegion(atlas, (atlas.getWidth()/2), yNumb*height, width, height);
    }
}
