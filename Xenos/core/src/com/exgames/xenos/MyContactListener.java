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
        if ((contact.getFixtureA().getBody().getUserData().equals("Hero")&& contact.getFixtureB().getBody().getUserData().equals("Door"))|
                (contact.getFixtureA().getBody().getUserData().equals("Door") && contact.getFixtureB().getBody().getUserData().equals("Hero"))){
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if ((contact.getFixtureA().getBody().getUserData().equals("Hero")&& contact.getFixtureB().getBody().getUserData().equals("Door"))|
                (contact.getFixtureA().getBody().getUserData().equals("Door") && contact.getFixtureB().getBody().getUserData().equals("Hero"))){
        }
    }
}
