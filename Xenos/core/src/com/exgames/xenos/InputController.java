package com.exgames.xenos;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 06.04.2017.
 */
public class InputController implements InputProcessor {
    private static double a;
    private static double b;
    private static double c;
    private static double grad;
    private static boolean pressW;
    private static boolean pressA;
    private static boolean pressS;
    private static boolean pressD;
    private static WorldBuilder world;

    public InputController(WorldBuilder world){
        this.world = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A){
            world.setHeroVector(world.getHeroVector().add(new Vector2(-3,0)));
            pressA = true;
        }
        if (keycode == Input.Keys.D){
            world.setHeroVector(world.getHeroVector().add(new Vector2(3,0)));
            pressD = true;
        }
        if (keycode == Input.Keys.W){
            world.setHeroVector(world.getHeroVector().add(new Vector2(0,3)));
            pressW = true;
        }
        if (keycode == Input.Keys.S){
            world.setHeroVector(world.getHeroVector().add(new Vector2(0,-3)));
            pressS = true;
        }
        world.setKeyboardUpdate(true);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A){
            world.setHeroVector(world.getHeroVector().add(new Vector2(3,0)));
            pressA = false;
        }
        if (keycode == Input.Keys.D){
            world.setHeroVector(world.getHeroVector().add(new Vector2(-3,0)));
            pressD = false;
        }
        if (keycode == Input.Keys.W){
            world.setHeroVector(world.getHeroVector().add(new Vector2(0,-3)));
            pressW = false;
        }
        if (keycode == Input.Keys.S){
            world.setHeroVector(world.getHeroVector().add(new Vector2(0,3)));
            pressS = false;
        }
        if (!pressA && !pressS && !pressD && !pressW) {
            world.setKeyboardUpdate(false);
            world.setMouseGrad(0);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        a = world.getCenterx() - screenX;
        b = world.getCentery() - screenY;
        c = Math.hypot(a,b);
        grad = Math.toDegrees(Math.acos((Math.pow(b, 2.0)+Math.pow(c, 2.0)-Math.pow(a, 2.0))/(2*b*c)));
        if (a < 0){
            grad = 360-grad;
        }
        world.setMouseUpdate(true);
        world.setMouseGrad(grad + 90);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled " + amount);
        return false;
    }
}
