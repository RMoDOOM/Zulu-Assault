package logic;

import levels.LevelHandler;

public class TimeManager {

    public static final String TEXT_TOTAL_TIME = "TOTAL", TEXT_LEVEL_TIME = "LEVEL";
    private static long timeInLevelMillis, timeTotalMillis;
    private static boolean displayTime;

    static {
        String value = SettingsManager.loadSetting("show_time");
        if (value.isEmpty()) {
            // setting doesn't exist -> store new one and use default
            SettingsManager.storeSetting(new SettingsManager.Property("show_time", "false"));
            // displayTime = false; // -> redundant
        } else {
            displayTime = value.equals("true");
        }
    }

    public static void init() {
        timeInLevelMillis = 0;
    }

    public static void reset() {
        timeTotalMillis = 0;
    }

    public static void finishLevel() {
        if (LevelHandler.playerIsInPlayThrough())
            timeTotalMillis += timeInLevelMillis;
    }

    public static long getTimeInLevel() {
        return timeInLevelMillis;
    }

    public static long getTotalTime() {
        return timeTotalMillis + timeInLevelMillis;
    }

    public static void toggleTimeDisplay() {
        displayTime = !displayTime;
    }

    public static boolean displayTime() {
        return displayTime;
    }

    public static void update(int deltaTime) {
        timeInLevelMillis += deltaTime;
    }
}
