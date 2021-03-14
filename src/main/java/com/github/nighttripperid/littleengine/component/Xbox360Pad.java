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

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Xbox360Pad {
    private static final ControllerManager controllerManager;
    private static ControllerState currState;

    static {
        controllerManager = new ControllerManager();
        log.info("initializing XBox360 controller manager");
        controllerManager.initSDLGamepad();
        currState = controllerManager.getState(controllerManager.getNumControllers());
    }

    private static final boolean[] buttonsLast = new boolean[14];
    private static final boolean[] buttonsHeld = new boolean[14];
    private static final boolean[] buttonsPressed = new boolean[14];
    private static final boolean[] buttonsReleased = new boolean[14];

    private Xbox360Pad() {
    }

    public static void update() {
        currState = controllerManager.getState(0);

        System.arraycopy(buttonsHeld, 0, buttonsLast, 0, buttonsHeld.length);

        buttonsHeld[0] = currState.leftStickClick;
        buttonsHeld[1] = currState.rightStickClick;
        buttonsHeld[2] = currState.a;
        buttonsHeld[3] = currState.b;
        buttonsHeld[4] = currState.x;
        buttonsHeld[5] = currState.y;
        buttonsHeld[6] = currState.lb;
        buttonsHeld[7] = currState.rb;
        buttonsHeld[8] = currState.start;
        buttonsHeld[9] = currState.back;
        buttonsHeld[10] = currState.dpadUp;
        buttonsHeld[11] = currState.dpadDown;
        buttonsHeld[12] = currState.dpadLeft;
        buttonsHeld[13] = currState.dpadRight;

        buttonsPressed[0] = currState.leftStickJustClicked;
        buttonsPressed[1] = currState.rightStickJustClicked;
        buttonsPressed[2] = currState.aJustPressed;
        buttonsPressed[3] = currState.bJustPressed;
        buttonsPressed[4] = currState.xJustPressed;
        buttonsPressed[5] = currState.yJustPressed;
        buttonsPressed[6] = currState.lbJustPressed;
        buttonsPressed[7] = currState.rbJustPressed;
        buttonsPressed[8] = currState.startJustPressed;
        buttonsPressed[9] = currState.backJustPressed;
        buttonsPressed[10] = currState.dpadUpJustPressed;
        buttonsPressed[11] = currState.dpadDownJustPressed;
        buttonsPressed[12] = currState.dpadLeftJustPressed;
        buttonsPressed[13] = currState.dpadRightJustPressed;

        for (int i = 0; i < buttonsHeld.length; i++)
            buttonsReleased[i] = !buttonsHeld[i] && buttonsLast[i];
    }

    public static boolean pressed(Button button) {
        return buttonsPressed[button.value];
    }

    public static boolean released(Button button) {
        return buttonsReleased[button.value];
    }

    public static boolean held(Button button) {
        return buttonsHeld[button.value];
    }

    public static boolean doVibration(int index, float leftMagnitude, float rightMagnitude, int duration_ms) {
        return controllerManager.doVibration(index, leftMagnitude, rightMagnitude, duration_ms);
    }

    public static void deInit() {
        log.info("De-initializing XBox360 controller manager");
        controllerManager.quitSDLGamepad();
        log.info("XBox360 controller manager successfully de-initialized");
    }

    @AllArgsConstructor
    public static enum Button {
        LEFT_STICK(0),
        RIGHT_STICK(1),
        A(2),
        B(3),
        X(4),
        Y(5),
        LB(6),
        RB(7),
        START(8),
        BACK(9),
        DPAD_UP(10),
        DPAD_DOWN(11),
        DPAD_LEFT(12),
        DPAD_RIGHT(13);

        @Getter
        private int value;
    }

}
