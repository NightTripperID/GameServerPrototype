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
import com.github.nighttripperid.littleengine.model.behavior.*;
import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;
import com.github.nighttripperid.littleengine.model.graphics.GfxBody;
import com.github.nighttripperid.littleengine.model.physics.CollisionBody;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Data
public abstract class Actor implements Entity, Eventable, Comparable<Actor> {
    @Setter(AccessLevel.NONE)
    private MessageBus messageBus = new MessageBus();
    private String gfxKey;
    private GfxBody gfxBody = new GfxBody();
    private AnimationReel animationReel = new AnimationReel();
    private CollisionBody collisionBody = new CollisionBody();
    private List<RenderTask> renderTasks = new ArrayList<>();
    private List<Publisher> publishers = new ArrayList<>();
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

    @Slf4j
    protected static class MessageBus {
        private static List<String> publishers;
        private static Map<String, List<Actor>> registries = new HashMap<>();

        static {
            Logger logger = LoggerFactory.getLogger(MessageBus.class);
            try (InputStream inputStream = MessageBus.class.getResourceAsStream("/publishers.yml")) {
                if (inputStream != null) {
                    try {
                        List<String> values = new ObjectMapper(new YAMLFactory()).readValue(inputStream, new TypeReference<List<String>>(){});
                        publishers = Collections.unmodifiableList(values);
                    } catch (IOException e) {
                        logger.error("error loading publishers.properties from inputStream.");
                    }
                } else {
                    logger.info("publishers.properties not found. skipping publisherId map initialization.");
                }
            } catch (IOException e) {
                logger.error("error getting inputStream from publishers.properties.");
            }
        }

        private MessageBus() {
            if (publishers != null) {
                publishers.forEach(this::register);
            }
        }

        public List<String> getPublishers() {
            return publishers;
        }

        private void register(String publisher) {
            if (registries.get(publisher) != null) {
                log.error("error registering: publisher's registry already exists.");
            }
            registries.put(publisher, new ArrayList<>());
        }

        public void subscribe(Actor subscriber, String publisher) {
            if (registries.get(publisher) == null) {
                log.error("error subscribing actor {} to publisher {}: publisher's registry not found.",
                        subscriber, publisher);
            } else if (registries.get(publisher).contains(subscriber)) {
                log.error("error subscribing actor {} to publisher {}: subscriber already in publisher's registry.",
                        subscriber, publisher);
            } else {
                registries.get(publisher).add(subscriber);
            }
        }

        public void unsubscribe(Actor subscriber, String publisher) {
            if (registries.get(publisher) == null) {
                log.error("error unsubscribing actor {} from publisher {}: publisher's registry not found.",
                        subscriber, publisher);
            } else if (!registries.get(publisher).contains(subscriber)) {
                log.error("error unsubscribing actor {} from publisher {}: subscriber not in publisher's registry.",
                        subscriber, publisher);
            } else {
                registries.get(publisher).remove(subscriber);
            }
        }

        public void publishToSubscribers(String publisherName, Publisher publisher) {
            registries.get(publisherName).forEach(publisher::publishTo);
        }
    }
}