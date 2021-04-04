package com.github.nighttripperid.littleengine.model.behavior;

import com.github.nighttripperid.littleengine.model.Actor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private Consumer<Actor> topic;
    public void publishTo(Actor subscriber) {
        topic.accept(subscriber);
    }
}
