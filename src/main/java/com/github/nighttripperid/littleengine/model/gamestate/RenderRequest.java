package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.component.RenderRequestProcessor;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.BiConsumer;

public class RenderRequest {
    @Getter
    private final int renderLayer;
    @Getter(AccessLevel.NONE)
    private final BiConsumer<RenderRequestProcessor, ScreenBuffer> renderRequest;
    
    public RenderRequest(int renderLayer, BiConsumer<RenderRequestProcessor, ScreenBuffer> renderRequest) {
        this.renderLayer = renderLayer;
        this.renderRequest = renderRequest;
    }
    
    public void process(RenderRequestProcessor processor, ScreenBuffer screenBuffer) {
        renderRequest.accept(processor, screenBuffer);
    }
}
