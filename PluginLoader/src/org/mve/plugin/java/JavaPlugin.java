package org.mve.plugin.java;

public abstract class JavaPlugin
{
	private boolean enabled = false;
	private final PluginLoader loader;

	public JavaPlugin()
	{
		this.loader = ((PluginClassLoader) this.getClass().getClassLoader()).getLoader();
	}

	public void onLoad(){}

	public void onEnable(){}

	public void onDisable(){}

	public final void setEnabled(boolean enabled)
	{
		if (enabled == this.enabled) return;
		this.enabled = enabled;
		if (enabled) this.onEnable();
		else this.onDisable();
	}

	public final boolean isEnabled()
	{
		return enabled;
	}

	public final PluginLoader getPluginLoader()
	{
		return loader;
	}
}
