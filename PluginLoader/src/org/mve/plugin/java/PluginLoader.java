package org.mve.plugin.java;

import org.mve.plugin.PluginManager;

import java.io.File;
import java.io.IOException;

public final class PluginLoader
{
	private final PluginManager manager;
	private final JavaPlugin plugin;
	private final Class<? extends JavaPlugin> mainClass;
	private final PluginClassLoader classLoader;

	public PluginLoader(File file, PluginManager manager) throws IOException
	{
		this.manager = manager;
		this.classLoader = new PluginClassLoader(file, this);
		this.plugin = classLoader.getPlugin();
		this.mainClass = classLoader.getMainClass();
	}

	public JavaPlugin getPlugin()
	{
		return this.plugin;
	}

	public PluginManager getPluginManager()
	{
		return manager;
	}

	public Class<? extends JavaPlugin> getMainClass()
	{
		return mainClass;
	}

	public PluginClassLoader getPluginClassLoader()
	{
		return classLoader;
	}
}
