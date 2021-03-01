package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.component.RenderRequestProcessor;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.BiConsumer;

public class RenderRequest {
    @Getter(AccessLevel.NONE)
    private BiConsumer<RenderRequestProcessor, ScreenBuffer> renderRequest;
    
    public RenderRequest(BiConsumer<RenderRequestProcessor, ScreenBuffer> renderRequest) {
        this.renderRequest = renderRequest;
    }
    
    public void process(RenderRequestProcessor processor, ScreenBuffer screenBuffer) {
        renderRequest.accept(processor, screenBuffer);
    }
}
