package com.github.nighttripperid.littleengine.model.iscript;

import com.github.nighttripperid.littleengine.model.scene.GameMap;

public interface Behavior {
    void run(GameMap gameMap, double timeElapsed);
}
