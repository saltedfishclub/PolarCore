package org.mve.plugin.java;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.mve.plugin.InvalidPluginException;
import org.mve.plugin.PluginDescription;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class PluginClassLoader extends URLClassLoader
{
	private final PluginLoader loader;
	private final JarFile jar;
	private final JavaPlugin plugin;
	private final PluginDescription description;

	@SuppressWarnings("all")
	public PluginClassLoader(File file, PluginLoader loader) throws IOException
	{
		super(new URL[]{file.toURI().toURL()});
		this.loader = loader;
		this.jar = new JarFile(file);
		JarEntry pluginJsonEntry = jar.getJarEntry("plugin.json");
		if (pluginJsonEntry == null) throw new InvalidPluginException("No plugin.json exists in plugin file "+file.getAbsolutePath());
		InputStream in = this.jar.getInputStream(pluginJsonEntry);
		JsonObject json = new JsonParser().parse(new JsonReader(new InputStreamReader(in))).getAsJsonObject();
		in.close();
		JsonElement nameElement = json.get("name");
		JsonElement versionElement = json.get("version");
		JsonElement mainElement = json.get("main");
		if (nameElement == null) throw new InvalidPluginException("No entry \"name\" in plugin.json in plugin: "+file.getAbsolutePath());
		if (versionElement == null) throw new InvalidPluginException("No entry \"version\" in plugin.json in plugin: "+file.getAbsolutePath());
		if (mainElement == null) throw new InvalidPluginException("No entry \"main\" in plugin.json in plugin: "+file.getAbsolutePath());
		String name = nameElement.getAsString();
		String version = versionElement.getAsString();
		String main = mainElement.getAsString();
		Class<?> clazz;
		JavaPlugin plugin;
		try
		{
			clazz = Class.forName(main, true, this);
			plugin = (JavaPlugin) clazz.getDeclaredConstructor().newInstance();
		}
		catch (Throwable e) {
			throw new InvalidPluginException("Could not load plugin " + file.getAbsolutePath(), e);
		}
		this.description = new PluginDescription(name, version, (Class<? extends JavaPlugin>) clazz);
		this.plugin = plugin;
	}

	public JavaPlugin getPlugin()
	{
		return plugin;
	}

	public PluginDescription getDescription()
	{
		return description;
	}

	public Class<? extends JavaPlugin> getMainClass()
	{
		return description.getMain();
	}

	public PluginLoader getLoader()
	{
		return loader;
	}

	@Override
	protected void finalize() throws Throwable
	{
		this.jar.close();
		super.finalize();
	}
}
