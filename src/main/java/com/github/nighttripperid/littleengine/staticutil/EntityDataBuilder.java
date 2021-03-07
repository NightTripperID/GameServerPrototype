package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.EntityData;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EntityDataBuilder {

    public static EntityData build(TILED_TileMap tiled_tileMap) {
        EntityData entityData = new EntityData();
        tiled_tileMap.getLayers().stream().filter(layer -> layer.getType().equals("objectgroup"))
                .collect(Collectors.toList()).forEach(layer -> {
            layer.getObjects().stream().filter(object -> object.getType().equals("entity"))
                    .collect(Collectors.toList()).forEach(object -> {
                Map<String, String> properties = new HashMap<>();
                object.getProperties().forEach(property ->
                        properties.put(property.getName(), property.getValue()));
                try {
                    Class<?> clazz = Class.forName(properties.get("class"));
                    Entity entity = (Entity) clazz.newInstance();
                    entity.setPosition(new PointDouble(object.getX(), object.getY()));
                    properties.remove("class");
                    properties.keySet().forEach(fieldName ->
                            injectField(entity.getClass(), entity,
                                    fieldName, properties.get(fieldName))
                    );
                    entityData.getEntities().add(entity);
                } catch (ClassNotFoundException e) {
                    log.error("Error finding Class with name \"{}\". " +
                            "Make sure to use fully qualified class name.", properties.get("class"));
                } catch (IllegalAccessException e) {
                    log.error("Error accessing Class to instantiate with name \"{}\". " +
                            "make sure class is public.", properties.get("class"));
                } catch (InstantiationException e) {
                    log.error("Error instantiating Class with name \"{}\"", properties.get("class"));
                }
            });
        });
        return entityData;
    }

    private static void injectField(Class<?> clazz, Entity entity, String fieldName, Object value) {
        while (clazz != Object.class) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(entity, value);
                return;
            } catch (NoSuchFieldException e) {
                log.info("Could not find field \"{}\" in class \"{}\":" +
                        "will search superclass", fieldName, clazz.getName());
                clazz = clazz.getSuperclass();
                if (clazz == Object.class)
                    log.error("Error finding field \"{}\" in class \"{}\"", fieldName, clazz.getName());
            } catch (IllegalAccessException e) {
                log.error("Error accessing field \"{}\" in class \"{}\"", fieldName, clazz.getName());
            }
        }
    }
}