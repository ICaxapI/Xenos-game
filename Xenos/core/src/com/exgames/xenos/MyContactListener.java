package com.exgames.xenos;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Alex on 23.04.2017.
 */
public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        UserData fixA = (UserData) contact.getFixtureA().getBody().getUserData();
        UserData fixB = (UserData) contact.getFixtureB().getBody().getUserData();
        if ((fixA.getName().equals("Hero")&& fixB.getName().equals("Door"))|
                (fixA.getName().equals("Door") && fixB.getName().equals("Hero"))){
            contact.setEnabled(false);
        }
        if (fixA.getName().equals("Detector")){
            contact.setEnabled(false);
            fixA.getDoor().swith(fixA.getAxis());
        }
        if (fixB.getName().equals("Detector")){
            contact.setEnabled(false);
            fixB.getDoor().swith(fixB.getAxis());
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        UserData fixA = (UserData) contact.getFixtureA().getBody().getUserData();
        UserData fixB = (UserData) contact.getFixtureB().getBody().getUserData();
        if ((fixA.getName().equals("Hero")&& fixB.getName().equals("Door"))|
                (fixA.getName().equals("Door") && fixB.getName().equals("Hero"))){
            contact.setEnabled(false);
        }
    }
}
