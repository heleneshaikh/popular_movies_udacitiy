package com.hfad.popularmovies.model;

/**
 * Created by heleneshaikh on 06/07/16.
 */
public class MessageEvent { //OBJECT IN BUS
    public final String fragmentType;
    public final int position;
    public final int id;

    public MessageEvent(int position, String fragmentType, int id) {
        this.position = position;
        this.fragmentType = fragmentType;
        this.id = id;
    }
}
