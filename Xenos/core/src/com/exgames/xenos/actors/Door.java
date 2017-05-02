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
    private boolean heroInside = false;

    public Door(Texture texture, String world, String nameModel, BodyDef.BodyType bodyType, int density, float sizeX, float sizeY, float linearDamping, float angularDamping, float restitution, float friction, float x, float y, boolean axisUpDown) {
        super(texture, world, nameModel, bodyType, density, sizeX, sizeY, linearDamping, angularDamping, restitution, friction, x, y);
        this.axisUpDown = axisUpDown;
    }
    public Door(String world, String nameModel, BodyDef.BodyType bodyType, int density, float linearDamping, float angularDamping, float restitution, float friction, float x, float y, boolean axisUpDown) {
        super(world, nameModel, bodyType, density, linearDamping, angularDamping, restitution, friction, x, y);
        this.axisUpDown = axisUpDown;
    }

    public void initVector(){
        xStart = getRect().getWorldCenter().x;
        yStart = getRect().getWorldCenter().y;
        yFin = getRect().getWorldCenter().y;
        xFin = getRect().getWorldCenter().x;
    }

    public void swith() {
        if (!active) {
            active = true;
            if (axisUpDown) {
                yFin = getRect().getWorldCenter().y + 1;
            } else {
                xFin = getRect().getWorldCenter().x + 1;
            }
            timerTask = new Door.MyTimerTask();
            timer.scheduleAtFixedRate(timerTask, 500, 500);
        }
    }

    private void stopTimer(){
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
        if (active) {
            updateVelocity();
        }
    }
    public void draw(Batch batch){
        draw(batch, 1f);
    }

    private void updateVelocity(){
        if (axisUpDown){
            if (getRect().getWorldCenter().y != yFin) {
                float needSpeed = (yFin - getRect().getWorldCenter().y) * 10;
                getRect().setLinearVelocity(new Vector2(0, needSpeed));
            }
        } else {
            if (getRect().getWorldCenter().x != xFin) {
                float needSpeed = (xFin - getRect().getWorldCenter().x) * 10;
                getRect().setLinearVelocity(new Vector2(needSpeed, 0));
            }
        }
    }

    class MyTimerTask extends TimerTask {

        MyTimerTask(){
        }

        public void run() {
            if (axisUpDown) {
                if (!open) {
                    open = true;
                    yFin = getRect().getWorldCenter().y;
                } else if (wait < 2) {
                    wait++;
                } else if (close) {
                    yFin = getRect().getWorldCenter().y;
                    active = false;
                    open = false;
                    wait = 0;
                    close = false;
                    getRect().setLinearVelocity(new Vector2(0, 0));
                    stopTimer();
                } else if (heroInside) {

                } else {
                    yFin = yStart;
                    close = true;
                }
            } else {
                if (!open) {
                    open = true;
                    xFin = getRect().getWorldCenter().x;
                } else if (wait < 2) {
                    wait++;
                } else if (close) {
                    xFin = getRect().getWorldCenter().x;
                    stopTimer();
                    active = false;
                    open = false;
                    wait = 0;
                    close = false;
                } else if (heroInside) {

                } else {
                    xFin = xStart;
                    close = true;
                }
            }
        }

    }

    public void setHeroInside(boolean heroInside){
        this.heroInside = heroInside;
    }
}
