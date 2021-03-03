package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.graphics.EntityGFX;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.Consumer;

public class InitGFX {
    @Getter(AccessLevel.NONE)
    private Consumer<EntityGFX> script;

    public InitGFX(Consumer<EntityGFX> script) {
        this.script = script;
    }

    public void run(EntityGFX entityGFX) {
        script.accept(entityGFX);
    }
}
