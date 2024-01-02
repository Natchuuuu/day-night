package com.daynightcycle;

import java.awt.Color;
import net.runelite.client.config.*;

@ConfigGroup("dayNightCycle")
public interface DayNightCycleConfig extends Config {
    @ConfigSection(
            name = "General",
            description = "General settings, for the entire range effects to be visible 117 HD must be enabled.",
            position = 0
    )
    String generalSettings = "generalSettings";
    @ConfigSection(
            name = "Colors",
            description = "Change the color properties of various times of day.",
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

    @ConfigItem(
            keyName = "startupMessage",
            name = "Startup Message",
            description = "If checked, sends a game-message if you don't have 117 HD enabled on start-up.",
            section = "generalSettings",
            position = 5
    )
    default boolean startupMessage() {
        return true;
    }

    // Color Settings
    @ConfigItem(
            keyName = "dayColor",
            name = "Day Sky Color",
            description = "Sets the color of the sky during day, is overridden for 117 HD if Use Dynamic Sky is checked",
            section = "colorSettings",
            position = 0
    )
    default Color dayColor(){return Color.LIGHT_GRAY;}

    @ConfigItem(
            keyName = "nightColor",
            name = "Night Sky Color",
            description = "Sets the color of the sky during night.",
            section = "colorSettings",
            position = 1
    )
    default Color nightColor(){return Color.BLACK;}

    @ConfigItem(
            keyName = "dynamicDaytime",
            name = "Use Dynamic Daytime Sky",
            description = "If checked, use the dynamic 117HD sky for daytime when possible. Uses your custom sky color when disabled.",
            section = "colorSettings",
            position = 3
    )
    default boolean dynamicDaytime() {
        return true;
    }
}
