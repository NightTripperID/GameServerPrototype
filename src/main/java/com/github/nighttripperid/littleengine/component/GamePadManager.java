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
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class GamePadManager {
    private static final ControllerManager controllerManager = new ControllerManager();
    private static final Map<UUID, GamePad> gamePads = new HashMap<>();
    private static final Map<UUID, GamePad> availablePads = new HashMap<>();
    public static final float CALIBRATION = 0.15f;

    public GamePadManager() {
        log.info("initializing GamePadManager");
        controllerManager.initSDLGamepad();
        log.info("GamePadManager successfully initialized.");
        for (int i = 0; i < controllerManager.getNumControllers(); i++) {
            GamePad gamePad = new GamePad(UUID.randomUUID(), i, controllerManager.getState(i));
            gamePads.put(gamePad.getId(), gamePad);
            availablePads.put(gamePad.getId(), gamePad);
        }

        log.info("number of game pads is: {}", controllerManager.getNumControllers());
        gamePads.values().forEach(gamePad ->
                log.info("game pad {}: {}", gamePad.getId(), gamePad.state.controllerType));

        if (gamePads.isEmpty()) {
            log.info("no game pads found");
        }
    }

    public  void update() {
        gamePads.values().forEach(gamePad -> {
            gamePad.state = controllerManager.getState(gamePad.index);

            for (Analog analog : gamePad.analogHeld.keySet())
                gamePad.analogLast.put(analog, gamePad.analogHeld.get(analog));

            updateAnalogHeld(gamePad.analogHeld, gamePad.state);
            updateAnalogStarted(gamePad.analogStarted, gamePad.analogLast, gamePad.state);

            for (Button button : gamePad.buttonsHeld.keySet())
                gamePad.buttonsLast.put(button, gamePad.buttonsHeld.get(button));

            updateButtonsHeld(gamePad.buttonsHeld, gamePad.state);
            updateButtonsPressed(gamePad.buttonsPressed, gamePad.state);

            gamePad.buttonsReleased.replaceAll((b, v) -> !gamePad.buttonsHeld.get(b) && gamePad.buttonsLast.get(b));
        });
    }

    public static void doVibration(UUID padId, float leftMagnitude, float rightMagnitude, int duration_ms) {
        controllerManager.doVibration(gamePads.get(padId).index, leftMagnitude, rightMagnitude, duration_ms);
    }

    GamePad checkOutGamePad() {
        Map.Entry<UUID, GamePad> entry = availablePads.entrySet().iterator().next();
        GamePad gamePad = entry.getValue();
        availablePads.remove(entry.getKey());
        return gamePad;
    }

    void returnGamePad(GamePad gamePad) {
        // create a new GamePad with a fresh id so caller's reference to GamePad id is no longer valid
        GamePad newGamePad = new GamePad(UUID.randomUUID(), gamePad.index,  gamePad.state);

        // set state to null to forcibly eliminate caller's reference to ControllerState.
        // this ensures that a controller state is not accidentally referenced by multiple objects
        gamePad.state = null;

        gamePads.remove(gamePad.id);
        gamePads.put(newGamePad.id, newGamePad);
        availablePads.put(newGamePad.id, newGamePad);
    }

    static void deInit() {
        log.info("de-initializing GamePadManager");
        controllerManager.quitSDLGamepad();
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

    private static void updateButtonsHeld(Map<Button, Boolean> buttonMap, ControllerState controllerState) {
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

    private static void updateButtonsPressed(Map<Button, Boolean> buttonMap, ControllerState controllerState) {
        buttonMap.put(Button.LS_CLICK, controllerState.leftStickJustClicked);
        buttonMap.put(Button.RS_CLICK, controllerState.rightStickJustClicked);
        buttonMap.put(Button.A, controllerState.aJustPressed);
        buttonMap.put(Button.B, controllerState.bJustPressed);
        buttonMap.put(Button.X, controllerState.xJustPressed);
        buttonMap.put(Button.Y, controllerState.yJustPressed);
        buttonMap.put(Button.LB, controllerState.lbJustPressed);
        buttonMap.put(Button.RB, controllerState.rbJustPressed);
        buttonMap.put(Button.START, controllerState.startJustPressed);
        buttonMap.put(Button.BACK, controllerState.backJustPressed);
        buttonMap.put(Button.DPAD_U, controllerState.dpadUpJustPressed);
        buttonMap.put(Button.DPAD_D, controllerState.dpadDownJustPressed);
        buttonMap.put(Button.DPAD_L, controllerState.dpadLeftJustPressed);
        buttonMap.put(Button.DPAD_R, controllerState.dpadRightJustPressed);
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

    public static class GamePad {
        @Getter
        private final UUID id;
        private final int index;
        private final Map<Button, Boolean> buttonsLast = new EnumMap<>(Button.class);
        private final Map<Button, Boolean> buttonsHeld = new EnumMap<>(Button.class);
        private final Map<Button, Boolean> buttonsPressed = new EnumMap<>(Button.class);
        private final Map<Button, Boolean> buttonsReleased = new EnumMap<>(Button.class);
        private final Map<Analog, Float> analogLast = new EnumMap<>(Analog.class);
        private final Map<Analog, Float> analogHeld = new EnumMap<>(Analog.class);
        private final Map<AnalogStarted, Boolean> analogStarted = new EnumMap<>(AnalogStarted.class);
        private ControllerState state;

        private GamePad(UUID id, int index, ControllerState state) {
            this.id = id;
            this.index = index;
            this.state = state;

            new ArrayList<>(Arrays.asList(Button.values())).forEach(value -> {
                this.buttonsPressed.put(value, false);
                this.buttonsHeld.put(value, false);
                this.buttonsLast.put(value, false);
                this.buttonsReleased.put(value, false);
            });
            new ArrayList<>(Arrays.asList(Analog.values())).forEach(value -> {
                this.analogLast.put(value, 0.0f);
                this.analogHeld.put(value, 0.0f);
            });
            new ArrayList<>(Arrays.asList(AnalogStarted.values())).forEach(value ->
                    this.analogStarted.put(value, false));
        }

        public boolean pressed(Button button) {
            return buttonsPressed.get(button);
        }
        public boolean released(Button button) {
            return buttonsReleased.get(button);
        }
        public boolean held(Button button) {
            return buttonsHeld.get(button);
        }
        public boolean analogStarted(AnalogStarted as) {
            return analogStarted.get(as);
        }
        public float analog(Analog analog) {
            return analogLast.get(analog);
        }
    }
}
