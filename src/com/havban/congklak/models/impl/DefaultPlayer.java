package com.havban.congklak.models.impl;

import com.havban.congklak.models.Hole;
import com.havban.congklak.models.Player;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class DefaultPlayer implements Player {

    private int id;
    private String name;

    public DefaultPlayer(int id, String name){
        this.id = id;
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return "Player "+id+" - "+name;
    }

    @Override
    public boolean equals(Object o){

        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Player p = (Player) o;
        if (id != p.getId())
            return false;
        return true;
    }
}
