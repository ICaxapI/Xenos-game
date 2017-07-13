package com.exgames.xenos.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.exgames.xenos.JsonUtils;
import com.exgames.xenos.Main;
import com.exgames.xenos.WorldBuilder;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Olejon on 16.04.2017.
 */
public class Cloud extends Actor{
    private Sound peek;
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
    public Label myLabel;
    public String needString;
    private float x;
    private float y;
    private float scale;
    private Label.LabelStyle labelStyle;
    private static Timer timer;
    private TimerTask timerTask;
    private MyTimerTaskStop timerTaskStop;
    private WorldObject worldObject;

    public Cloud(Texture atlas, float x, float y, String string, BitmapFont font, Stage stage, float scale,int period, String soundPatch, WorldObject worldObject){
        if (!initialized) {
            timer = new Timer(true);
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
        this.worldObject = worldObject;
        peek = Gdx.audio.newSound(Gdx.files.internal(soundPatch));
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        myLabel = new Label(string, labelStyle);
        myLabel.setText("");
        myLabel.setAlignment(Align.center);
        this.needString = string;
        x -= myLabel.getWidth()/2f;
        y -= myLabel.getHeight()/2f;
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
        addInputListener(stage);
        timerTask = new MyTimerTaskAddChar();
        timer.scheduleAtFixedRate(timerTask, period, period);
    }

    private void updateCloud(){
        ltSprite.setSize(texltRegion.getRegionWidth() * scale, texltRegion.getRegionHeight() * scale);
        ltSprite.setPosition(x - ltSprite.getWidth(), y + myLabel.getHeight());
        mtSprite.setSize(myLabel.getWidth(), texmtRegion.getRegionHeight() * scale);
        mtSprite.setPosition(x, y + myLabel.getHeight());
        rtSprite.setSize(texrtRegion.getRegionWidth() * scale, texrtRegion.getRegionHeight() * scale);
        rtSprite.setPosition(x + myLabel.getWidth(), y + myLabel.getHeight());
        mlSprite.setSize(texmlRegion.getRegionWidth() * scale, myLabel.getHeight());
        mlSprite.setPosition(x - mlSprite.getWidth(), y);
        cSprite.setSize(myLabel.getWidth(), myLabel.getHeight());
        cSprite.setPosition(x, y);
        mrSprite.setSize(texmlRegion.getRegionWidth() * scale, myLabel.getHeight());
        mrSprite.setPosition(x + myLabel.getWidth(), y);
        lbSprite.setSize(texlbRegion.getRegionWidth() * scale, texlbRegion.getRegionHeight() * scale);
        lbSprite.setPosition(x - lbSprite.getWidth(), y - lbSprite.getHeight());
        mbSprite.setSize(myLabel.getWidth(), texmbRegion.getRegionHeight() * scale);
        mbSprite.setPosition(x, y - mbSprite.getHeight());
        rbSprite.setSize(texrbRegion.getRegionWidth() * scale, texrbRegion.getRegionHeight() * scale);
        rbSprite.setPosition(x + myLabel.getWidth(), y - rbSprite.getHeight());
        setBounds(lbSprite.getX(), lbSprite.getY(),
                (lbSprite.getWidth() * lbSprite.getScaleX()) + (mbSprite.getWidth() * mbSprite.getScaleX()) + (rbSprite.getWidth() * rbSprite.getScaleX()),
                (lbSprite.getHeight() * lbSprite.getScaleY()) + (mlSprite.getHeight() * mlSprite.getScaleY()) + (ltSprite.getHeight() * lbSprite.getScaleY()));
        myLabel.setPosition(x, y);
    }

    //private Vector2 getCordsScreen(){
        //Vector2 heroWorld = WorldBuilder.getHero().getRect().getWorldCenter();
        //Vector2 objWorld = getRect().getPosition().sub(getModelOrigin());
        //float alignx = (objWorld.x - heroWorld.x)*(getStage().getWidth()/16f);
        //float aligny = (objWorld.y - heroWorld.y)*(getStage().getHeight()/9f);
        //Vector2 objStage = new Vector2(getStage().getWidth()/2f + alignx,  getStage().getHeight()/2f + aligny);
        //return objStage;
    //}

    @Override
    public void draw(Batch batch, float alpha){
        if (worldObject.isMovedCloud()){
            x = worldObject.getX() + (worldObject.getWidth()/2) - (myLabel.getWidth()/2);
            y = worldObject.getY() + worldObject.getHeight() + 20;
            updateCloud();
        }
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

    void dispose(){
        peek.dispose();
        this.remove();
        if (timerTask != null) {
            timerTask.cancel();
        }
        if (timerTaskStop != null) {
            timerTaskStop.cancel();
        }
        ltSprite = null;
        lbSprite = null;
        rtSprite = null;
        rbSprite = null;
        cSprite = null;
        mtSprite = null;
        mlSprite = null;
        mrSprite = null;
        mbSprite = null;
        myLabel = null;
        needString = null;
        labelStyle  = null;
        timerTask  = null;
        timerTaskStop = null;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    class MyTimerTaskAddChar extends TimerTask {
        private char lastSymbol;
        private int counter;

        MyTimerTaskAddChar() {
        }

        public void run( ) {
            peek.stop( ) ;
            if ( !Objects.equals( myLabel.getText( ).toString( ), needString ) ) {
                StringBuilder temp = new StringBuilder ( );
                for ( int i = 0; i <= counter; i++ ) {
                    temp.append(needString.charAt( i ) );
                    lastSymbol = needString.charAt( i );
                }
                counter++;
                myLabel.setText( temp.toString( ) );
                if ( lastSymbol != ' ' && lastSymbol != '\n' ) {
                    peek.play(Main.volumeSound);
                }
            }
            else {
                timerTaskStop = new MyTimerTaskStop();
                timer.scheduleAtFixedRate(timerTaskStop, 3000, 3000);
                timerTask.cancel();
            }
        }

    }

    class MyTimerTaskStop extends TimerTask {

        MyTimerTaskStop(){
        }

        public void run(){
            worldObject.removeCloud();
        }

    }

    private void addInputListener(Stage stage){
        InputListener listener = new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchDown cloud");
                worldObject.removeCloud();
                return false;
            }
        };
        addListener(listener);
        stage.addActor(this);
    }

}



