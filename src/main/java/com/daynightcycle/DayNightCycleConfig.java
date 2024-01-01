package com.daynightcycle;

import java.awt.Color;
import net.runelite.client.config.*;

@ConfigGroup("dayNightCycle")
public interface DayNightCycleConfig extends Config {
    @ConfigSection(
            name = "General",
            description = "Please ensure you have 117 HD installed with dynamic lights enabled for all effects.",
            position = 0
    )
    String generalSettings = "generalSettings";
    @ConfigSection(
            name = "Colors",
            description = "Change the colors of various times of day.",
            position = 1
    )
    String colorSettings = "colorSettings";
    @ConfigItem(
            keyName = "nightTimeStart",
            name = "Night Start (Hour)",
            description = "The hour when night begins (24-hour format)",
            section = "generalSettings",
            position = 2
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
            name = "Day Start (Hour)",
            description = "The hour when day begins (24-hour format)",
            section = "generalSettings",
            position = 1
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
            description = "Brightness level during the night, between 0 and 50",
            section = "generalSettings",
            position = 4
    )
    @Range(
            min = 0,
            max = 50
    )
    default int nightBrightness() {
        return 2;
    }

    @ConfigItem(
            keyName = "dayBrightness",
            name = "Day Brightness",
            description = "Brightness level during day, between 0 and 50",
            section = "generalSettings",
            position = 3
    )
    @Range(
            min = 0,
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
            description = "Set the time mode to Automatic, Day, or Night.",
            section = "generalSettings",
            position = 0
    )
    default TimeMode timeMode() {
        return TimeMode.AUTOMATIC;
    }
    // Color Settings
    @ConfigItem(
            keyName = "dayColor",
            name = "Day Sky Color (Read desc)",
            description = "Sets the color of the sky during day, is overridden by 117 HD.",
            section = "colorSettings",
            position = 0
    )
    default Color dayColor(){return Color.LIGHT_GRAY;}
    @ConfigItem(
            keyName = "nightColor",
            name = "Night Sky Color",
            description = "Sets the color of the sky during night. 117 HD nighttime compatible.",
            section = "colorSettings",
            position = 1
    )
    default Color nightColor(){return Color.BLACK;}
}
