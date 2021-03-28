package com.github.nighttripperid.littleengine.model.graphics;

import com.github.nighttripperid.littleengine.model.physics.Rect;
import com.github.nighttripperid.littleengine.model.physics.VectorF2D;
import lombok.Data;

@Data
public class GfxBody extends Rect {
    private Sprite sprite;
    private VectorF2D offset = VectorF2D.of(0.0f);
}