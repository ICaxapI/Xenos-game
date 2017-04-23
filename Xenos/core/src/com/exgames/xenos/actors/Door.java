package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Alex on 23.04.2017.
 */
public class Door extends  WorldObject {
    private static Timer timer = new Timer();
    private boolean active;
    private TimerTask timerTask;
    private float xStart;
    private float yStart;
    private float xFin;
    private float yFin;
    private boolean open = false;
    private int wait = 0;
    private boolean close = false;
    private boolean axisUpDown;

    public Door(Texture texture, String world, String nameModel, BodyDef.BodyType bodyType, int density, float sizeX, float sizeY, float linearDamping, float angularDamping, float restitution, float friction, float x, float y) {
        super(texture, world, nameModel, bodyType, density, sizeX, sizeY, linearDamping, angularDamping, restitution, friction, x, y);
    }
    public Door(String world, String nameModel, BodyDef.BodyType bodyType, int density, float linearDamping, float angularDamping, float restitution, float friction, float x, float y) {
        super(world, nameModel, bodyType, density, linearDamping, angularDamping, restitution, friction, x, y);
    }

    public void initVector(){
        xStart = getRect().getWorldPoint(getRect().getLocalCenter()).x;
        yStart = getRect().getWorldPoint(getRect().getLocalCenter()).y;
        xFin = getRect().getWorldPoint(getRect().getLocalCenter()).x + 10;
        yFin = getRect().getWorldPoint(getRect().getLocalCenter()).y + 10;
    }

    public void swith(Boolean axisUpDown) {
        this.axisUpDown = axisUpDown;
        if (!active) {
            active = true;
            getRect().setLinearVelocity(new Vector2(0, 4));
            timerTask = new Door.MyTimerTask();
            timer.scheduleAtFixedRate(timerTask, 250, 250);
        }
    }

    public void stopTimer(){
        timerTask.cancel();
    }

    public void draw(Batch batch, float alpha){
        if (mySprite != null) {
            mySprite.draw(batch, alpha);
            Vector2 pos = getRect().getPosition().sub(getModelOrigin());
            mySprite.setPosition(pos.x, pos.y);
            mySprite.setOrigin(getModelOrigin().x, getModelOrigin().y);
            mySprite.setRotation(getRect().getAngle() * MathUtils.radiansToDegrees);
        }
        //System.out.println(rect.getUserData());
    }
    public void draw(Batch batch){
        draw(batch, 1f);
    }

    public void updateVelocity(){
        if (axisUpDown){

        }
    }

    class MyTimerTask extends TimerTask {

        MyTimerTask(){
        }

        public void run() {
            if (close){
                getRect().setLinearVelocity(new Vector2(0, 0));
                stopTimer();
                active = false;
                open = false;
                wait = 0;
                close = false;
                System.out.println("4");
            }

            if (!open){
                open = true;
                getRect().setLinearVelocity(new Vector2(0, 0));
                System.out.println("1");
            } else if (wait < 4){
                wait++;
                System.out.println("2");
            } else {
                getRect().setLinearVelocity(new Vector2(0, -4));
                close = true;
                System.out.println("3");
            }

        }

    }
}
