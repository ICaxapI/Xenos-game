package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Olejon on 16.04.2017.
 */
public class Cloud extends Actor{
    private static boolean initialized = false;
    private static TextureRegion texltRegion;
    private static TextureRegion texlbRegion;
    private static TextureRegion texrtRegion;
    private static TextureRegion texrbRegion;
    private static TextureRegion texcRegion;
    private static TextureRegion texmtRegion;
    private static TextureRegion texmlRegion;
    private static TextureRegion texmrRegion;
    private static TextureRegion texmbRegion;
    private static TextureRegion textail;
    private Sprite ltSprite;
    private Sprite lbSprite;
    private Sprite rtSprite;
    private Sprite rbSprite;
    private Sprite  cSprite;
    private Sprite mtSprite;
    private Sprite mlSprite;
    private Sprite mrSprite;
    private Sprite mbSprite;
    private Label myLabel;
    private float x;
    private float y;
    private float scale;
    private Label.LabelStyle labelStyle;

    public Cloud(Texture atlas, float x, float y, String string, BitmapFont font, Stage stage, float scale){
        if (!initialized) {
            atlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
            texltRegion = new TextureRegion(atlas, 0, 0, 3, 3);
            texlbRegion = new TextureRegion(atlas, 0, 8, 3, 3);
            texrtRegion = new TextureRegion(atlas, 8, 0, 3, 3);
            texrbRegion = new TextureRegion(atlas, 8, 8, 3, 3);
            texcRegion = new TextureRegion(atlas, 3, 3, 5, 5);
            texmtRegion = new TextureRegion(atlas, 3, 0, 5, 3);
            texmlRegion = new TextureRegion(atlas, 0, 3, 3, 5);
            texmrRegion = new TextureRegion(atlas, 8, 3, 3, 5);
            texmbRegion = new TextureRegion(atlas, 3, 8, 5, 3);
            textail = new TextureRegion(atlas, 11, 10, 5, 6);
            initialized = true;
        }
        labelStyle = new Label.LabelStyle();//Не забыть dispose!
        labelStyle.font = font;
        myLabel = new Label(string, labelStyle);
        myLabel.setText(string);
        myLabel.setPosition(x, y);
        ltSprite = new Sprite(texltRegion); //левый верхний
        mtSprite = new Sprite(texmtRegion); //верхний центр
        rtSprite = new Sprite(texrtRegion); //правый верхний
        mlSprite = new Sprite(texmlRegion); //левый посередине
        cSprite = new Sprite(texcRegion); //центр
        mrSprite = new Sprite(texmrRegion); //правый посередине
        lbSprite = new Sprite(texlbRegion); //левый нижний
        mbSprite = new Sprite(texmbRegion); //нижний центр
        rbSprite = new Sprite(texrbRegion); //правый нижний
        this.x = x;
        this.y = y;
        this.scale = scale;
        updateCloud();
        setPosition(x, y);
        stage.addActor(this);
    }

    public void updateCloud(){
        ltSprite.setSize(texltRegion.getRegionWidth()*scale,texltRegion.getRegionHeight()*scale);
        ltSprite.setPosition(x - ltSprite.getWidth(), y + myLabel.getPrefHeight());
        mtSprite.setSize(myLabel.getPrefWidth(),texmtRegion.getRegionHeight()*scale);
        mtSprite.setPosition(x, y + myLabel.getPrefHeight());
        rtSprite.setSize(texrtRegion.getRegionWidth()*scale,texrtRegion.getRegionHeight()*scale);
        rtSprite.setPosition(x+myLabel.getPrefWidth(), y + myLabel.getPrefHeight());
        mlSprite.setSize(texmlRegion.getRegionWidth()*scale, myLabel.getPrefHeight());
        mlSprite.setPosition(x - mlSprite.getWidth(), y);
        cSprite.setSize(myLabel.getPrefWidth(), myLabel.getPrefHeight());
        cSprite.setPosition(x , y);
        mrSprite.setSize(texmlRegion.getRegionWidth()*scale, myLabel.getPrefHeight());
        mrSprite.setPosition(x + myLabel.getPrefWidth(), y);
        lbSprite.setSize(texlbRegion.getRegionWidth()*scale,texlbRegion.getRegionHeight()*scale);
        lbSprite.setPosition(x - lbSprite.getWidth(), y - lbSprite.getHeight());
        mbSprite.setSize(myLabel.getPrefWidth(),texmbRegion.getRegionHeight()*scale);
        mbSprite.setPosition(x, y - mbSprite.getHeight());
        rbSprite.setSize(texrbRegion.getRegionWidth()*scale,texrbRegion.getRegionHeight()*scale);
        rbSprite.setPosition(x+myLabel.getPrefWidth(), y - rbSprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha){
        updateCloud();

        ltSprite.draw(batch);
        mtSprite.draw(batch);
        rtSprite.draw(batch);
        mlSprite.draw(batch);
        cSprite.draw(batch);
        mrSprite.draw(batch);
        lbSprite.draw(batch);
        mbSprite.draw(batch);
        rbSprite.draw(batch);

        myLabel.draw(batch, 1f);
    }

    public void dispose(){

    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}
