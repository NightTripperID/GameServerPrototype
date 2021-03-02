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
    
    public RenderRequest(BiConsumer<RenderRequestProcessor, ScreenBuffer> renderRequest, int renderLayer) {
        this.renderRequest = renderRequest;
        this.renderLayer = renderLayer;
    }
    
    public void process(RenderRequestProcessor processor, ScreenBuffer screenBuffer) {
        renderRequest.accept(processor, screenBuffer);
    }
}
