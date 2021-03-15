package com.github.nighttripperid.littleengine.model.iscript;

import com.github.nighttripperid.littleengine.component.RenderTaskHandler;

public interface RenderTask extends Comparable<RenderTask> {
    void handle(RenderTaskHandler handler);
}
