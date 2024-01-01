package com.daynightcycle;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("dayNightCycle")
public interface DayNightCycleConfig extends Config {
    @ConfigItem(
            keyName = "nightTimeStart",
            name = "Night Start",
            description = "The hour when night begins (24-hour format)"
    )
    @Range(
            min = 12,
            max = 23
    )
    default int nightTimeStart() {
        return 20;
    }
    @ConfigItem(
            keyName = "dayTimeStart",
            name = "Day Start",
            description = "The hour when day begins (24-hour format)"
    )
    @Range(
            min = 0,
            max = 11
    )
    default int dayTimeStart() {
        return 8;
    }

    @ConfigItem(
            keyName = "nightBrightness",
            name = "Night Brightness",
            description = "Normal brightness level during the night."
    )
    @Range(
            min = 1,
            max = 50
    )
    default int nightBrightness() {
        return 2;
    }

    @ConfigItem(
            keyName = "dayBrightness",
            name = "Day Brightness",
            description = "Normal brightness level during day."
    )
    @Range(
            min = 1,
            max = 50
    )
    default int dayBrightness() {
        return 20;
    }
    enum TimeMode {
        AUTOMATIC,
        DAY,
        NIGHT
    }

    @ConfigItem(
            keyName = "timeMode",
            name = "Time",
            description = "Set the time mode to Automatic, Day, or Night."
    )
    default TimeMode timeMode() {
        return TimeMode.AUTOMATIC;
    }
}
