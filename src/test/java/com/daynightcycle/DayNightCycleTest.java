package com.daynightcycle;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DayNightCycleTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DayNightCyclePlugin.class);
		RuneLite.main(args);
	}
}