package com.havban.congklak.models;

import java.util.List;
import java.util.Map;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public interface Board {

    Map<Position, Hole> getHoles();

    Hole getMainHole(Player p);

    Hole getMainHole(int playerSeq);

    Hole getHole(Player p, int seq);

    Hole getHole(Position pos);

    int getSize();

    Hole getHoleAcross(Position pos);

    void walk(Player cp, Position p);

    Position getNextPosition(Player cp, Position pos);

    Hole getNextHole(Player cp, Position pos);
}
