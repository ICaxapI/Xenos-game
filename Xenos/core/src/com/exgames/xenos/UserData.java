package com.exgames.xenos;

import com.exgames.xenos.actors.Door;

/**
 * Created by Alex on 23.04.2017.
 */
public class UserData {
    private String name;
    private Door door;

    public UserData(String name){
        this.name = name;
    }

    public UserData(String name, Door door){
        this(name);
        this.door = door;
    }

    public Door getDoor(){
        return door;
    }

    public String getName(){
        return name;
    }
}
