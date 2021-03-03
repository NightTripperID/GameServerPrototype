package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.Eventable;
import lombok.Data;

@Data
public abstract class GameState implements Eventable {
    private Intent intent;
    private GameStateEntities gameStateEntities = new GameStateEntities();
    private GameMap gameMap = new GameMap();
}
