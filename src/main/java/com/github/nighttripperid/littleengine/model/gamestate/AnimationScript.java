package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Map;
import java.util.function.Consumer;

public class AnimationScript {
    @Getter(AccessLevel.NONE)
    private Consumer<Map<Integer, Sprite>> script;

    public AnimationScript(Consumer<Map<Integer, Sprite>> script) {
        this.script = script;
    }
    public void run(Map<Integer, Sprite> spriteMap) {
        script.accept(spriteMap);
    }
}
