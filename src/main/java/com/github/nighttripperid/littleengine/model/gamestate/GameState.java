package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.Eventable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class GameState implements Eventable {
    private Intent intent;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> pendingEntities = new ArrayList<>();

    private GameMap gameMap = new GameMap();
}
