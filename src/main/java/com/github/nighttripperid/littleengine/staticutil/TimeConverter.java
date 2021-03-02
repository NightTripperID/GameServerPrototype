package com.github.nighttripperid.littleengine.staticutil;

public class TimeConverter {
    private TimeConverter() {
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
    public static double framesToSeconds(int frames) {
        return frames / 60D;
    }
    public static double framesToMinutes(int frames) {
        return frames / 3600D;
    }
    public static double framesToHours(int frames) {
        return frames /216000D;
    }
}
