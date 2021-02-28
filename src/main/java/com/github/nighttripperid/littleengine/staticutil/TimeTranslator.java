package com.github.nighttripperid.littleengine.staticutil;

public class TimeTranslator {
    private TimeTranslator() {
    }
    public static int secondsToFrames(int seconds) {
        return seconds * 60;
    }
    public static int minutesToFrames(int minutes) {
        return minutes * 3600;
    }
    public static int hoursToFrames(int hours) {
        return hours * 216000;
    }
}
