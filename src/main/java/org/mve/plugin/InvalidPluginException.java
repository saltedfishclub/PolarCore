package org.mve.plugin;

import java.io.IOException;

public class InvalidPluginException extends IOException
{
	public InvalidPluginException(String msg)
	{
		super(msg);
	}

	public InvalidPluginException(String msg, Throwable caused)
	{
		super(msg, caused);
	}
}
