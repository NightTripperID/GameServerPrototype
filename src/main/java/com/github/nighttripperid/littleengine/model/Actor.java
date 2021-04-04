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
import com.github.nighttripperid.littleengine.exception.DuplicateTopicException;
import com.github.nighttripperid.littleengine.exception.DuplicateSubscriberException;
import com.github.nighttripperid.littleengine.exception.TopicNotFoundException;
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
    private static MessageBus messageBus = new MessageBus();
    private String gfxKey;
    private GfxBody gfxBody = new GfxBody();
    private AnimationReel animationReel = new AnimationReel();
    private CollisionBody collisionBody = new CollisionBody();
    private List<RenderTask> renderTasks = new ArrayList<>();
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

    public static MessageBus getMessageBus() {
        return messageBus;
    }

    @Slf4j
    protected static class MessageBus {
        private static List<String> topics;
        private static Map<String, List<Actor>> registries = new HashMap<>();

        static {
            Logger logger = LoggerFactory.getLogger(MessageBus.class);
            try (InputStream inputStream = MessageBus.class.getResourceAsStream("/topics.yml")) {
                if (inputStream != null) {
                    try {
                        List<String> values = new ObjectMapper(new YAMLFactory()).readValue(inputStream,
                                new TypeReference<List<String>>(){});
                        topics = Collections.unmodifiableList(values);
                    } catch (IOException e) {
                        logger.error("error loading topics.yml from inputStream.");
                    }
                } else {
                    logger.info("topics.yml not found. skipping registry initialization.");
                }
            } catch (IOException e) {
                logger.error("error getting inputStream from topics.yml.");
            }
        }

        private MessageBus() {
            if (topics != null) {
                topics.forEach(this::register);
            }
        }

        public List<String> getTopics() {
            return topics;
        }

        private void register(String topic) {
            if (registries.get(topic) != null) {
                log.error("error registering topic: duplicate entry \"{}\" found in topic list.", topic);
                throw new DuplicateTopicException("error registering topic: duplicate topic entry found " +
                        "in topic list.");
            } else {
                registries.put(topic, new ArrayList<>());
            }
        }

        public void subscribe(Actor subscriber, String topic) {
            if (registries.get(topic) == null) {
                log.error("error subscribing to topic \"{}\": topic not found.", topic);
                throw new TopicNotFoundException("error subscribing to topic: topic not found");
            } else if (registries.get(topic).contains(subscriber)) {
                log.error("error subscribing to topic \"{}\": subscriber already present.", topic);
                throw new DuplicateSubscriberException("error subscribing to topic: subscriber already present.");
            } else {
                registries.get(topic).add(subscriber);
            }
        }

        public void unsubscribe(Actor subscriber, String topic) {
            if (registries.get(topic) == null) {
                log.error("error unsubscribing actor from topic \"{}\": topic not found.", topic);
                throw new TopicNotFoundException("error unsubscribing from topic: topic not found");
            } else if (!registries.get(topic).contains(subscriber)) {
                log.error("error unsubscribing from topic \"{}\": subscriber not found.", topic);
                throw new SubscriberNotFoundException("error unsubscribing from topic: subscriber not found");
            } else {
                registries.get(topic).remove(subscriber);
            }
        }

        public void publishToTopic(String topicName, Topic topic) {
            registries.get(topicName).forEach(topic::publishTo);
        }
    }
}