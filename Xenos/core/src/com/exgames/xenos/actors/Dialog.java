package com.exgames.xenos.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.exgames.xenos.JsonUtils;
import com.exgames.xenos.Main;
import com.exgames.xenos.WorldBuilder;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Olejon on 16.04.2017.
 */
public class Dialog extends Actor{
    private static boolean initialized = false;
    private static TextureRegion texltRegion;
    private static TextureRegion texrtRegion;
    private static TextureRegion texcRegion;
    private static TextureRegion texmtRegion;
    private Sprite ltSprite;
    private Sprite rtSprite;
    private Sprite  cSprite;
    private Sprite mtSprite;
    private DialogLabel[] myLabel;
    private float scale;
    private float  heightDial = 0f;
    private float alias = 10;
    private int lastIndexAns;
    private int lastIndexExit;
    private WorldObject myObject;

    public Dialog(Texture atlas, String[] ansvers, String ansversID[], String[] exit, String[] exitId, BitmapFont font, Stage stage, float scale, WorldObject myObject){
        if (!initialized) {
            atlas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
            texltRegion = new TextureRegion(atlas, 0, 0, 3, 3);
            texrtRegion = new TextureRegion(atlas, 8, 0, 3, 3);
            texcRegion = new TextureRegion(atlas, 3, 3, 5, 5);
            texmtRegion = new TextureRegion(atlas, 3, 0, 5, 3);
            initialized = true;
        }
        this.myObject = myObject;
        heightDial = alias;
        float heightdraw = 0;
        float maxWidth = 0;
        lastIndexAns = ansvers.length - 1; //тут тоже может кинуть IOE, если так будет сделать +1
        if (exit != null) {
            lastIndexExit = exit.length - 1;
            myLabel = new DialogLabel[ansvers.length + exit.length];
            for (int i = 0; i < (ansvers.length + exit.length); i++) {
                if (i <= exit.length - 1) {
                    myLabel[i] = new DialogLabel(exit[i], font, true);
                    myLabel[i].getLabel().setText(exit[i]);
                    heightDial += myLabel[i].getLabel().getHeight() + alias;
                    heightdraw += alias;
                    myLabel[i].setY(heightdraw);
                    heightdraw += myLabel[i].getLabel().getHeight();
                    myLabel[i].setID(exitId[i]);
                    if (myLabel[i].getLabel().getWidth() > maxWidth) {
                        maxWidth = myLabel[i].getLabel().getWidth();
                    }
                } else {
                    myLabel[i] = new DialogLabel(ansvers[i - exit.length], font, false);
                    myLabel[i].getLabel().setText(ansvers[i - exit.length]);
                    heightDial += myLabel[i].getLabel().getHeight() + alias;
                    heightdraw += alias;
                    myLabel[i].setY(heightdraw);
                    heightdraw += myLabel[i].getLabel().getHeight();
                    myLabel[i].setID(ansversID[i - exit.length]);
                    if (myLabel[i].getLabel().getWidth() > maxWidth) {
                        maxWidth = myLabel[i].getLabel().getWidth();
                    }
                }
            }
        } else {
            myLabel = new DialogLabel[ansvers.length];
            for (int i = 0; i < (ansvers.length); i++) {
                myLabel[i] = new DialogLabel(ansvers[i], font, false);
                myLabel[i].getLabel().setText(ansvers[i]);
                heightDial += myLabel[i].getLabel().getHeight() + alias;
                heightdraw += alias;
                myLabel[i].setY(heightdraw);
                heightdraw += myLabel[i].getLabel().getHeight();
                myLabel[i].setID(ansversID[i]);
                if (myLabel[i].getLabel().getWidth() > maxWidth) {
                    maxWidth = myLabel[i].getLabel().getWidth();
                }
            }
        }
        //myLabel.setPosition(x, y);
        ltSprite = new Sprite(texltRegion); //левый верхний
        mtSprite = new Sprite(texmtRegion); //верхний центр
        rtSprite = new Sprite(texrtRegion); //правый верхний
        cSprite = new Sprite(texcRegion); //центр
        this.scale = scale;
        stage.addActor(this);
        updateCloud();
        //setPosition(x, y);
        //stage.addActor();
        if (exit != null) {
            for (int j = 0; j < ansvers.length + exit.length; j++) {
                myLabel[j].setX((getStage().getWidth() / 2) - (myLabel[j].getWidth() / 2f));
                stage.addActor(myLabel[j]);
            }
        } else {
            for (int j = 0; j < ansvers.length; j++) {
                myLabel[j].setX((getStage().getWidth() / 2) - (myLabel[j].getWidth() / 2f));
                stage.addActor(myLabel[j]);
            }
        }
    }

    private void updateCloud(){
        cSprite.setSize(getStage().getWidth(),  heightDial);
        ltSprite.setSize(texltRegion.getRegionWidth() * scale, texltRegion.getRegionHeight() * scale);
        rtSprite.setSize(texrtRegion.getRegionWidth() * scale, texrtRegion.getRegionHeight() * scale);
        mtSprite.setSize(cSprite.getWidth() - ((texltRegion.getRegionWidth() * scale) + (texrtRegion.getRegionWidth() * scale)), texmtRegion.getRegionHeight() * scale);

        cSprite.setPosition(0, 0);
        ltSprite.setPosition(0, cSprite.getHeight());
        rtSprite.setPosition(ltSprite.getWidth() + mtSprite.getWidth(), cSprite.getHeight());
        mtSprite.setPosition(ltSprite.getWidth(), cSprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha){
        ltSprite.draw(batch);
        mtSprite.draw(batch);
        rtSprite.draw(batch);
        cSprite.draw(batch);
        for (int j = 0; j <= lastIndexAns; j++) {
            myLabel[j].draw(batch, 1f);
        }
        updateCloud();
    }

    class DialogLabel extends Actor {
        Label label;
        Label.LabelStyle labelStyle;
        InputListener listener;
        boolean exit;
        String id;
        Color select = new Color(0xffbc2aff);
        Color unselect = new Color(Color.WHITE);

        DialogLabel(String string,  BitmapFont font, boolean itExit){
            labelStyle = new Label.LabelStyle();//Не забыть dispose!
            labelStyle.font = font;
            labelStyle.fontColor = Color.WHITE;
            label = new Label(string, labelStyle);
            label.setAlignment(Align.center);
            addInputListener();
            exit = itExit;
        }

        public void updateActor(){
            setBounds(label.getX(), label.getY(), label.getWidth(), label.getHeight());
        }

        @Override
        public void setY(float y) {
            super.setY(y);
            label.setY(y);
            updateActor();
        }

        @Override
        public void setX(float x) {
            super.setX(x);
            label.setX(x);
            updateActor();
        }

        @Override
        public void draw(Batch batch, float alpha){
            label.draw(batch, alpha);
        }

        private void addInputListener(){
            listener = new InputListener(){
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    System.out.println("keyDown DIALOG");
                    return false;
                }

                @Override
                public boolean keyUp(InputEvent event, int keycode) {
                    System.out.println("keyUp DIALOG");
                    return false;
                }

                @Override
                public boolean keyTyped(InputEvent event, char character) {
                    System.out.println("KeyTyped DIALOG");
                    return false;
                }

                @Override
                public boolean mouseMoved(InputEvent event, float x, float y) {
                    return false;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("touchDown DIALOG");
                    if (!exit){
                        myObject.removeCloud();
                        myObject.removeDialog();
                        myObject.changeStateDialog(id);
                    } else {
                        myObject.removeCloud();
                        myObject.removeDialog();
                        myObject.changeStateDialog(id);
                    }
                    return false;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    labelStyle.fontColor = select;
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    labelStyle.fontColor = unselect;
                    super.exit(event, x, y, pointer, toActor);
                }
            };
            addListener(listener);
        }

        public Label getLabel() {
            return label;
        }
        public void setID(String id){
            this.id = id;
        }
    }


    public void dispose(){
        this.remove();
        for (int i = 0; i < myLabel.length; i++) {
            myLabel[i].remove();
            myLabel[i] = null;
        }
        ltSprite = null;
        rtSprite = null;
        cSprite = null;
        mtSprite = null;
        myLabel = null;
    }

}



