package com.github.nighttripperid.littleengine.model.iscript;

import com.github.nighttripperid.littleengine.model.graphics.Sprite;

import java.util.Map;

public interface Animation {
    void run(Map<Integer, Sprite> spriteMap);
}
