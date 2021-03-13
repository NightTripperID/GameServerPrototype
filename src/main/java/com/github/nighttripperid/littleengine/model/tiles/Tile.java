package com.github.nighttripperid.littleengine.model.tiles;

import com.github.nighttripperid.littleengine.model.object.BasicObject;

import java.util.List;

public interface Tile extends BasicObject {
    int getId();
    List<String> getAttributes();
}
