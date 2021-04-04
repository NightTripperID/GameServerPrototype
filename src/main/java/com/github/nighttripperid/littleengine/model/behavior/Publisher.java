package com.github.nighttripperid.littleengine.model.behavior;

import com.github.nighttripperid.littleengine.model.Actor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    private Consumer<Actor> publisher;
    public void publishTo(Actor subscriber) {
        publisher.accept(subscriber);
    }
}
