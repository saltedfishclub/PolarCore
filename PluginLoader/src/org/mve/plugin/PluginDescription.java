package org.mve.plugin;

import org.mve.plugin.java.JavaPlugin;

public class PluginDescription
{
	private final String name;
	private final String version;
	private final Class<? extends JavaPlugin> main;

	public PluginDescription(String name, String version, Class<? extends JavaPlugin> main)
	{
		this.name = name;
		this.version = version;
		this.main = main;
	}

	public String getName()
	{
		return name;
	}

	public String getVersion()
	{
		return version;
	}

	public Class<? extends JavaPlugin> getMain()
	{
		return main;
	}
}
