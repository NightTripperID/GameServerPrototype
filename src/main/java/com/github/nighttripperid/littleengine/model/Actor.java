/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.nighttripperid.littleengine.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.nighttripperid.littleengine.exception.DuplicateEventException;
import com.github.nighttripperid.littleengine.exception.DuplicateSubscriberException;
import com.github.nighttripperid.littleengine.exception.EventNotFoundException;
import com.github.nighttripperid.littleengine.exception.SubscriberNotFoundException;
import com.github.nighttripperid.littleengine.model.behavior.*;
import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;
import com.github.nighttripperid.littleengine.model.graphics.GfxBody;
import com.github.nighttripperid.littleengine.model.physics.CollisionBody;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Data
public abstract class Actor implements Entity, Eventable, Comparable<Actor> {
    private static EventBus eventBus = new EventBus();
    private String gfxKey;
    private GfxBody gfxBody = new GfxBody();
    private AnimationReel animationReel = new AnimationReel();
    private CollisionBody collisionBody = new CollisionBody();
    private List<RenderTask> renderTasks = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();
    private Behavior behavior;
    private Animation animation;
    private GfxInitializer gfxInitializer;
    private SceneTransition sceneTransition;
    private CollisionResult collisionResult;
    private Spawn spawn;
    private int renderPriority;
    private int renderLayer;
    private boolean isRemoved;

    @Override
    public int compareTo(Actor actor) {
        return ((int)(float)this.getGfxBody().pos.y - (int)(float)actor.getGfxBody().pos.y);
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    @Slf4j
    protected static class EventBus {
        private static List<String> events;
        private static Map<String, List<Actor>> registries = new HashMap<>();

        static {
            Logger logger = LoggerFactory.getLogger(EventBus.class);
            try (InputStream inputStream = EventBus.class.getResourceAsStream("/events.yml")) {
                if (inputStream != null) {
                    try {
                        List<String> values = new ObjectMapper(new YAMLFactory()).readValue(inputStream,
                                new TypeReference<List<String>>(){});
                        events = Collections.unmodifiableList(values);
                    } catch (IOException e) {
                        logger.error("error loading events.yml from inputStream.");
                    }
                } else {
                    logger.info("events.yml not found. skipping registry initialization.");
                }
            } catch (IOException e) {
                logger.error("error getting inputStream from events.yml.");
            }
        }

        private EventBus() {
            if (events != null) {
                events.forEach(this::register);
            }
        }

        public List<String> getTopics() {
            return events;
        }

        private void register(String event) {
            if (registries.get(event) != null) {
                log.error("error registering event: duplicate entry \"{}\" found in event list.", event);
                throw new DuplicateEventException("error registering event: duplicate event entry found " +
                        "in event list.");
            } else {
                registries.put(event, new ArrayList<>());
            }
        }

        public void subscribe(Actor subscriber, String event) {
            if (registries.get(event) == null) {
                log.error("error subscribing to event \"{}\": event not found.", event);
                throw new EventNotFoundException("error subscribing to event: event not found");
            } else if (registries.get(event).contains(subscriber)) {
                log.error("error subscribing to event \"{}\": subscriber already present.", event);
                throw new DuplicateSubscriberException("error subscribing to event: subscriber already present.");
            } else {
                registries.get(event).add(subscriber);
            }
        }

        public void unsubscribe(Actor subscriber, String event) {
            if (registries.get(event) == null) {
                log.error("error unsubscribing actor from event \"{}\": event not found.", event);
                throw new EventNotFoundException("error unsubscribing from event: event not found");
            } else if (!registries.get(event).contains(subscriber)) {
                log.error("error unsubscribing from event \"{}\": subscriber not found.", event);
                throw new SubscriberNotFoundException("error unsubscribing from event: subscriber not found");
            } else {
                registries.get(event).remove(subscriber);
            }
        }

        public void notifySubscribers(String event) {
            registries.get(event).forEach(subscriber ->
                    subscriber.getNotifications().add(event)
            );
        }
    }
}