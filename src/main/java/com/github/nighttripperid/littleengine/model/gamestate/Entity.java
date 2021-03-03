package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.Eventable;
import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.PointInt;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Entity implements Eventable, Comparable<Entity> {
    private boolean removed;
    private int renderPriority;
    private int renderLayer = 2;
    private static final int MIN_RENDER_PRIORITY = 0;
    private static final int MAX_RENDER_PRIORITY =  3;
    private Sprite sprite;

    public PointInt direction;
    public PointDouble position;
    public PointDouble speed;

    private List<RenderRequest> renderRequests = new ArrayList<>();
    private BehaviorScript behaviorScript;
    private AnimationScript animationScript;
    private InitGFX initGFX;

    /**
     * Compares the render priority of this entity to another entity.
     * @param entity the other entity to compare to.
     * @return less than 1 if this entity has a higher render priority,
     * 0 if they have the same prioriy, and 1 if the other entity has a
     * higher priority.
     */
    @Override
    public int compareTo(Entity entity) {
        return this.renderPriority - entity.renderPriority;
    }
}
