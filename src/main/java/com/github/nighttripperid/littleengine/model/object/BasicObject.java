package com.github.nighttripperid.littleengine.model.object;

import com.github.nighttripperid.littleengine.model.physics.Rect;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;

public interface BasicObject {
    Rect getHitBox();
    Sprite getSprite();
}
