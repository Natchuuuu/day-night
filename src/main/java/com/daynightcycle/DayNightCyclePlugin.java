package com.daynightcycle;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;

import javax.inject.Inject;
import java.time.LocalTime;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
		name = "Day/Night Cycle",
		description = "Creates a day/night cycle by changing your 117 HD plugin settings, graphical settings, and skybox.",
		tags = { "day", "night", "cycle", "time", "ambience" },
		conflicts = "Skybox"
)
public class DayNightCyclePlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private DayNightCycleConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private PluginManager pluginManager;

	private ScheduledExecutorService executorService;

	private static final String HD_PLUGIN_CONFIG_GROUP = "hd";


	@Override
	protected void startUp() throws Exception {
		updateEnvironmentSettings();
		//Check each minute to update settings based on time.
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(this::updateEnvironmentSettings, 0, 1, TimeUnit.MINUTES);
	}

	@Override
	protected void shutDown() throws Exception {
		// Restore the  settings to their default values.
		client.setSkyboxColor(0);
		if (isHdPluginEnabled()) {
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "brightness2", 20);
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "defaultSkyColor", "DEFAULT");
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "overrideSky", false);
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "experimentalDecoupleWaterFromSkyColor", false);
		}
		// End time check
		if (executorService != null && !executorService.isShutdown()) {
			executorService.shutdown();
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOADING) {
			updateEnvironmentSettings();
		}
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			if (!isHdPluginEnabled()) {
				if(config.startupMessage()) {
					client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Day/Night Cycle: 117 HD plugin not enabled, please enable 117HD with dynamic lights for full range of effects.", null);
				}
			}
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged) {
		if (configChanged.getGroup().equals("dayNightCycle")) {
			// Refresh Sky
			updateEnvironmentSettings();
		}
	}

	private void updateEnvironmentSettings() {

		switch (config.timeMode()) {
			case NIGHT:
				setNightEnvironment();
				break;
			case DAY:
				setDayEnvironment();
				break;
			case AUTOMATIC:
			default:
				updateAutomaticEnvironment();
				break;
		}

	}

	private void updateAutomaticEnvironment() {
		LocalTime now = LocalTime.now();
		LocalTime nightStart = LocalTime.of(config.nightTimeStart(), 0);
		LocalTime dayStart = LocalTime.of(config.dayTimeStart(), 0);

		if (!now.isBefore(nightStart) || now.isBefore(dayStart)) {
			setNightEnvironment();
		} else {
			setDayEnvironment();
		}
	}

	private void setNightEnvironment() {
		client.setSkyboxColor(config.nightColor().getRGB());
		if (isHdPluginFound()){
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "brightness2", config.nightBrightness());
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "defaultSkyColor", "RUNELITE");
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "overrideSky", true);
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "experimentalDecoupleWaterFromSkyColor", false);
		}
	}

	private void setDayEnvironment() {
		client.setSkyboxColor(config.dayColor().getRGB());
		if (isHdPluginFound()) {
			configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "brightness2", config.dayBrightness());
			if(config.dynamicDaytime()) {
				configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "defaultSkyColor", "DEFAULT");
				configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "overrideSky", false);
			}
			else{
				configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "defaultSkyColor", "RUNELITE");
				configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "overrideSky", true);
				configManager.setConfiguration(HD_PLUGIN_CONFIG_GROUP, "experimentalDecoupleWaterFromSkyColor", false);
			}
		}
	}

	private boolean isHdPluginEnabled() {
		Collection<Plugin> plugins = pluginManager.getPlugins();
		for (Plugin plugin : plugins) {
			if (plugin.getName().equals("117 HD")) {
				return pluginManager.isPluginEnabled(plugin);
			}
		}
		return false;
	}
	private boolean isHdPluginFound() {
		Collection<Plugin> plugins = pluginManager.getPlugins();
		boolean hdFound = false;
		for (Plugin plugin : plugins) {

			if (plugin.getName().equals("117 HD")) {
				return true;
			}
		}
		return hdFound;
	}

	@Provides
	DayNightCycleConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(DayNightCycleConfig.class);
	}
}
