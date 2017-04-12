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
        //System.out.println(gradRect);
        a = newWorld.centerx - screenX;
        b = newWorld.centery - screenY;
        c = Math.sqrt((a*a)+(b*b));
        grad = Math.toDegrees(Math.acos((b*b+c*c-a*a)/(2*b*c)));
        if (a < 0){
            grad = 180+(180-grad);
        }
        newWorld.mouseGrad = grad;
//        System.out.println(grad);
//        System.out.println(gradRect);
//        System.out.println(Math.acos((b*b+c*c-a*a)/(2*b*c)));
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled " + amount);
        return false;
    }
}
