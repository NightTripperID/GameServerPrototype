package com.github.nighttripperid.littleengine.model.behavior;

import com.github.nighttripperid.littleengine.model.Actor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
public class Spawn {
    @Setter
    private Consumer<List<Actor>> spawn;

    public void spawn(List<Actor> pendingActors) {
        spawn.accept(pendingActors);
    }

}
