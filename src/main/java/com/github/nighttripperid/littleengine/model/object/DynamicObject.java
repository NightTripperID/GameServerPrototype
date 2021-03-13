package com.github.nighttripperid.littleengine.model.object;

import com.github.nighttripperid.littleengine.model.script.*;
import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;

import java.util.List;

public interface DynamicObject extends BasicObject {
    String getGfxKey();
    AnimationReel getAnimationReel();
    List<RenderTask> getRenderTasks();
    BehaviorScript getBehaviorScript();
    Animation getAnimation();
    InitGfxRoutine getInitGfxRoutine();
    SceneTransition getSceneTransition();
}
