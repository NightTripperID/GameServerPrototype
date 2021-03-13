package com.github.nighttripperid.littleengine.model;

import com.github.nighttripperid.littleengine.model.physics.Rect;
import com.github.nighttripperid.littleengine.model.script.*;
import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.object.DynamicObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Actor implements Entity, DynamicObject, Eventable, Comparable<Actor> {
    private String gfxKey;
    private Sprite sprite;
    private AnimationReel animationReel = new AnimationReel();
    private Rect hitBox = new Rect();
    private List<RenderTask> renderTasks = new ArrayList<>();
    private BehaviorScript behaviorScript;
    private Animation animation;
    private InitGfxRoutine initGfxRoutine;
    private SceneTransition sceneTransition;
    private int renderPriority;
    private int renderLayer;
    private boolean isRemoved;
}