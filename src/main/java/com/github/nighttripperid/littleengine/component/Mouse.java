/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.nighttripperid.littleengine.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse extends MouseAdapter {

    private static boolean button1, button2, button3;
    public static boolean button1Held, button2Held, button3Held;
    public static boolean button1Pressed, button2Pressed, button3Pressed;
    public static boolean button1Released, button2Released, button3Released;

    public static int mouseX, mouseY;

    public static boolean dragged;

    private final int screenScale;

    public Mouse(int screenScale) {
        if(screenScale < 1)
            throw new IllegalArgumentException("Screen scale must be greater than 0");

        this.screenScale = screenScale;
    }

    public void update() {

        boolean button1Last, button2Last, button3Last;

        button1Last = button1Held;
        button2Last = button2Held;
        button3Last = button3Held;

        button1Held = button1;
        button2Held = button2;
        button3Held = button3;

        button1Pressed = button1Held && !button1Last;
        button2Pressed = button2Held && !button2Last;
        button3Pressed = button3Held && !button3Last;

        button1Released = !button1Held && button1Last;
        button2Released = !button2Held && button2Last;
        button3Released = !button3Held && button3Last;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        int button = mouseEvent.getButton();

        if(button == MouseEvent.BUTTON1)
            button1 = true;
        if(button == MouseEvent.BUTTON2)
            button2 = true;
        if(button == MouseEvent.BUTTON3)
            button3 = true;

        dragged = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        int button = mouseEvent.getButton();

        if(button == MouseEvent.BUTTON1)
            button1 = false;
        if(button == MouseEvent.BUTTON2)
            button2 = false;
        if(button == MouseEvent.BUTTON3)
            button3 = false;

        dragged = false;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX() / screenScale;
        mouseY = mouseEvent.getY() / screenScale;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX() / screenScale;
        mouseY = mouseEvent.getY() / screenScale;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
    }
}
