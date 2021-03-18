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
package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.Actor;
import com.github.nighttripperid.littleengine.model.physics.PointFloat;
import com.github.nighttripperid.littleengine.model.scene.ActorData;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ActorDataHydrator {

    public static ActorData hydrate(TILED_TileMap tiled_tileMap) {
        ActorData actorData = new ActorData();
        tiled_tileMap.getLayers().stream().filter(layer -> layer.getType().equals("objectgroup"))
                .collect(Collectors.toList()).forEach(layer -> {
                    layer.getObjects().stream().filter(object -> object.getType().equals("actor"))
                    .collect(Collectors.toList()).forEach(object -> {
                        Map<String, String> properties = new HashMap<>();
                        object.getProperties().forEach(property ->
                                properties.put(property.getName(), property.getValue()));
                        try {
                            Class<?> clazz = Class.forName(properties.get("class"));
                            Actor actor = (Actor) clazz.newInstance();
                            actor.getHitBox().pos = (new PointFloat((float) object.getX(), (float) object.getY()));
                            actor.getHitBox().size = (
                                    new PointFloat((float) object.getWidth(),
                                                    (float) object.getHeight()));
                            properties.remove("class");
                            properties.keySet().forEach(fieldName -> injectField(actor.getClass(), actor,
                                    fieldName, properties.get(fieldName))
                            );
                            actorData.getActors().add(actor);
                        } catch (ClassNotFoundException e) {
                            log.error("Error finding Class with name \"{}\". " +
                                    "Be sure to use a fully qualified class name: {}",
                                    properties.get("class"), e.getMessage());
                        } catch (IllegalAccessException e) {
                            log.error("Error accessing Class to instantiate with name \"{}\". " +
                                    "make sure class is public: {}",
                                    properties.get("class"), e.getMessage());
                        } catch (InstantiationException e) {
                            log.error("Error instantiating Class with name \"{}\": {}",
                                    properties.get("class"), e.getMessage());
                        }
                    });
                });
        return actorData;
    }

    private static void injectField(Class<?> clazz, Actor actor, String fieldName, Object value) {
        while (clazz != Object.class) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(actor, value);
                return;
            } catch (NoSuchFieldException e) {
                log.info("Could not find field \"{}\" in class \"{}\":" +
                        "will search superclass", fieldName, clazz.getName());
                clazz = clazz.getSuperclass();
                if (clazz == Object.class)
                    log.error("Error finding field \"{}\" in class \"{}\": {}",
                            fieldName, clazz.getName(), e.getMessage());
            } catch (IllegalAccessException e) {
                log.error("Error accessing field \"{}\" in class \"{}\": {}",
                        fieldName, clazz.getName(), e.getMessage());
            }
        }
    }
}
