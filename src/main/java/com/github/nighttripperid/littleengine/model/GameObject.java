package com.github.nighttripperid.littleengine.model;

import com.github.nighttripperid.littleengine.model.entity.*;
import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class GameObject {

    private String gfxKey;
    private Sprite sprite;
    private AnimationReel animationReel = new AnimationReel();
    private Rect hitBox = new Rect();
    private List<RenderTask> renderTasks = new ArrayList<>();
    private BehaviorScript behaviorScript;
    private Animation animation;
    private InitGfxRoutine initGfxRoutine;
    private SceneTransition sceneTransition;
}