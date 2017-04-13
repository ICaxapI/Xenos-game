package com.exgames.xenos;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Alex on 06.04.2017.
 */
public class InputController implements InputProcessor {
    private static double a;
    private static double b;
    private static double c;
    private static double grad;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A){
            newWorld.rect.setAngularVelocity(1);
        }
        if (keycode == Input.Keys.D){
            newWorld.rect.setAngularVelocity(-1);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
        a = newWorld.centerx - screenX;
        b = newWorld.centery - screenY;
        c = Math.hypot(a,b);
        grad = Math.toDegrees(Math.acos((Math.pow(b, 2.0)+Math.pow(c, 2.0)-Math.pow(a, 2.0))/(2*b*c)));
        if (a < 0){
            grad = 360-grad;
        }
        newWorld.mouseGrad = grad;
        newWorld.updateGrad = true;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled " + amount);
        return false;
    }
}
