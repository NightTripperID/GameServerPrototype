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
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class GamePad {
    private static final ControllerManager controllerManager;
    private static final List<Pad> pads = new ArrayList<>();
    public static final float CALIBRATION = 0.15f;

    static {
        controllerManager = new ControllerManager();
        log.info("Initializing GamePad controller manager");
        controllerManager.initSDLGamepad();
        log.info("Controller manager successfully initialized.");
        for (int i = 0; i < controllerManager.getNumControllers(); i++)
            pads.add(new Pad(controllerManager.getState(i)));

        log.info("Number of controllers is: {}", controllerManager.getNumControllers());
        log.info("Controller types are:");
        for (int i = 0; i < controllerManager.getNumControllers(); i++)
            log.info(pads.get(i).state.controllerType);

        pads.forEach(pad -> {
            new ArrayList<>(Arrays.asList(Button.values())).forEach(value -> {
                pad.buttonsPressed.put(value, false);
                pad.buttonsHeld.put(value, false);
                pad.buttonsLast.put(value, false);
                pad.buttonsReleased.put(value, false);
            });
            new ArrayList<>(Arrays.asList(Analog.values())).forEach(value -> {
                pad.analogLast.put(value, 0.0f);
                pad.analogHeld.put(value, 0.0f);
            });

            new ArrayList<>(Arrays.asList(AnalogStarted.values())).forEach(value ->
                    pad.analogStarted.put(value, false));
        });

    }
    private GamePad() {
    }

    public static void update() {
        for (int i = 0; i < controllerManager.getNumControllers(); i++) {
            Pad pad = pads.get(i);

            pad.state = controllerManager.getState(i);

            for (Analog analog : pad.analogHeld.keySet())
                pad.analogLast.put(analog, pad.analogHeld.get(analog));

            updateAnalogHeld(pad.analogHeld, pad.state);
            updateAnalogStarted(pad.analogStarted, pad.analogLast, pad.state);

            for (Button button : pad.buttonsHeld.keySet())
                pad.buttonsLast.put(button, pad.buttonsHeld.get(button));

            updateButtonMap(pad.buttonsHeld, pad.state);

            for (Button button : pad.buttonsHeld.keySet()) {
                pad.buttonsPressed.put(button, pad.buttonsHeld.get(button) && !pad.buttonsLast.get(button));
                pad.buttonsReleased.put(button, !pad.buttonsHeld.get(button) && pad.buttonsLast.get(button));
            }
        }
    }

    public static boolean pressed(int padNum, Button button) {
        return pads.get(padNum).buttonsPressed.get(button);
    }

    public static boolean released(int padNum, Button button) {
        return pads.get(padNum).buttonsReleased.get(button);
    }

    public static boolean held(int padNum, Button button) {
        return pads.get(padNum).buttonsHeld.get(button);
    }

    public static boolean analogStarted(int padNum, AnalogStarted as) {
        return pads.get(padNum).analogStarted.get(as);
    }

    public static float analog(int padNum, Analog analog) {
        return pads.get(padNum).analogLast.get(analog);
    }

    public static boolean doVibration(int padNum, float leftMagnitude, float rightMagnitude, int duration_ms) {
        return controllerManager.doVibration(padNum, leftMagnitude, rightMagnitude, duration_ms);
    }

    public static void deInit() {
        log.info("De-initializing GamePad controller manager");
        controllerManager.quitSDLGamepad();
        log.info("GamePad controller manager successfully de-initialized");
    }

    public static enum Button {
        LS_CLICK,
        RS_CLICK,
        A,
        B,
        X,
        Y,
        LB,
        RB,
        START,
        BACK,
        DPAD_U,
        DPAD_D,
        DPAD_L,
        DPAD_R;
    }

    public static enum Analog {
        LS_X,
        RS_X,
        LS_Y,
        RS_Y,
        LS_MAG,
        RS_MAG,
        LS_ANGLE,
        RS_ANGLE,
        LT,
        RT;
    }

    public static enum AnalogStarted {
        LS_UP,
        LS_DOWN,
        LS_LEFT,
        LS_RIGHT,
        RS_UP,
        RS_DOWN,
        RS_LEFT,
        RS_RIGHT,
        LS_MAG,
        RS_MAG,
        LT,
        RT;
    }

    private static void updateButtonMap(Map<Button, Boolean> buttonMap, ControllerState controllerState) {
        buttonMap.put(Button.LS_CLICK, controllerState.leftStickClick);
        buttonMap.put(Button.RS_CLICK, controllerState.rightStickClick);
        buttonMap.put(Button.A, controllerState.a);
        buttonMap.put(Button.B, controllerState.b);
        buttonMap.put(Button.X, controllerState.x);
        buttonMap.put(Button.Y, controllerState.y);
        buttonMap.put(Button.LB, controllerState.lb);
        buttonMap.put(Button.RB, controllerState.rb);
        buttonMap.put(Button.START, controllerState.start);
        buttonMap.put(Button.BACK, controllerState.back);
        buttonMap.put(Button.DPAD_U, controllerState.dpadUp);
        buttonMap.put(Button.DPAD_D, controllerState.dpadDown);
        buttonMap.put(Button.DPAD_L, controllerState.dpadLeft);
        buttonMap.put(Button.DPAD_R, controllerState.dpadRight);
    }

    private static void updateAnalogHeld(Map<Analog, Float> analogHeld, ControllerState controllerState) {
        analogHeld.put(Analog.LS_X, controllerState.leftStickX);
        analogHeld.put(Analog.LS_Y, controllerState.leftStickY);
        analogHeld.put(Analog.RS_X, controllerState.rightStickX);
        analogHeld.put(Analog.RS_Y, controllerState.rightStickY);
        analogHeld.put(Analog.LS_MAG, controllerState.leftStickMagnitude);
        analogHeld.put(Analog.RS_MAG, controllerState.rightStickMagnitude);
        analogHeld.put(Analog.LS_ANGLE, controllerState.leftStickAngle);
        analogHeld.put(Analog.RS_ANGLE, controllerState.rightStickAngle);
        analogHeld.put(Analog.LT, controllerState.leftTrigger);
        analogHeld.put(Analog.RT, controllerState.rightTrigger);
    }

    private static void updateAnalogStarted(Map<AnalogStarted, Boolean> analogStarted, Map<Analog, Float> analogLast, ControllerState controllerState) {
        analogStarted.put(AnalogStarted.LS_UP, (controllerState.leftStickY > (0.0 + CALIBRATION)) && (analogLast.get(Analog.LS_Y) < 0.0 + CALIBRATION));
        analogStarted.put(AnalogStarted.LS_DOWN, (controllerState.leftStickY < (0.0 - CALIBRATION)) && (analogLast.get(Analog.LS_Y) > 0.0 - CALIBRATION));
        analogStarted.put(AnalogStarted.LS_LEFT, (controllerState.leftStickX < (0.0 - CALIBRATION)) && (analogLast.get(Analog.LS_X) > 0.0 - CALIBRATION));
        analogStarted.put(AnalogStarted.LS_RIGHT, (controllerState.leftStickX > (0.0 + CALIBRATION)) && (analogLast.get(Analog.LS_X) < 0.0 + CALIBRATION));
        analogStarted.put(AnalogStarted.RS_UP, (controllerState.rightStickY > (0.0 + CALIBRATION)) && (analogLast.get(Analog.RS_Y) < 0.0 + CALIBRATION));
        analogStarted.put(AnalogStarted.RS_DOWN, (controllerState.rightStickY < (0.0 - CALIBRATION)) && (analogLast.get(Analog.RS_Y) > 0.0 - CALIBRATION));
        analogStarted.put(AnalogStarted.RS_LEFT, (controllerState.rightStickX < (0.0 - CALIBRATION)) && (analogLast.get(Analog.RS_Y) > 0.0 - CALIBRATION));
        analogStarted.put(AnalogStarted.RS_RIGHT, (controllerState.rightStickX > (0.0 + CALIBRATION)) && (analogLast.get(Analog.RS_Y) < 0.0 + CALIBRATION));
        analogStarted.put(AnalogStarted.LS_MAG, (controllerState.leftStickMagnitude > 0.0 + CALIBRATION) && (analogLast.get(Analog.RS_MAG) < 0.0 + CALIBRATION));
        analogStarted.put(AnalogStarted.RS_MAG, (controllerState.rightStickMagnitude > 0.0 + CALIBRATION) && (analogLast.get(Analog.RS_MAG) < 0.0 + CALIBRATION));
        analogStarted.put(AnalogStarted.LT, ((controllerState.leftTrigger > 0.0f) && (analogLast.get(Analog.RT) == 0.0f)));
        analogStarted.put(AnalogStarted.RT, ((controllerState.rightTrigger > 0.0f) && (analogLast.get(Analog.RT) == 0.0f)));
    }

    @AllArgsConstructor
    private static class Pad {
        private final Map<Button, Boolean> buttonsLast = new EnumMap<>(Button.class);
        private final Map<Button, Boolean> buttonsHeld = new EnumMap<>(Button.class);
        private final Map<Button, Boolean> buttonsPressed = new EnumMap<>(Button.class);
        private final Map<Button, Boolean> buttonsReleased = new EnumMap<>(Button.class);
        private final Map<Analog, Float> analogLast = new EnumMap<>(Analog.class);
        private final Map<Analog, Float> analogHeld = new EnumMap<>(Analog.class);
        private final Map<AnalogStarted, Boolean> analogStarted = new EnumMap<>(AnalogStarted.class);
        private ControllerState state;
    }
}
