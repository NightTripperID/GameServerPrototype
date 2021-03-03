package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.graphics.EntityGFX;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameStateEntities {
    private EntityGFX entityGFX = new EntityGFX();
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> pendingEntities = new ArrayList<>();
}
