package org.mve.plugin.java;

import lombok.Getter;

import java.io.File;

public abstract class JavaPlugin {
	@Getter
	private final PluginLoader loader;
	/**
	 * get datafolder
	 */
	private final File DataFolder;
	/**
	 * @return plugin status
	 * @deprecated
	 */
	private boolean enabled = false;

	//todo read and write config.
	public JavaPlugin() {
		this.loader = ((PluginClassLoader) this.getClass().getClassLoader()).getLoader();
		DataFolder = new File("./plugins/config");
	}

	public void onLoad() {
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public final void setEnabled(boolean enabled) {
		if (enabled == this.enabled) return;
		this.enabled = enabled;
		if (enabled) this.onEnable();
		else this.onDisable();
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final PluginLoader getPluginLoader() {
		return loader;
	}

	public final File getDataFolder() {
		return DataFolder;
	}
}

