package com.github.nighttripperid.littleengine.graphics.tilemapping;

import lombok.Data;

import java.util.List;

@Data
public class TileMap {
    private int width;
    private int height;
    private int tilewidth;
    private int tileheight;
    private List<Layer> layers;
    private List<Tileset> tilesets;
}
