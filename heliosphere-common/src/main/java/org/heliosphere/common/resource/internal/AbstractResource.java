/*
 * Copyright(c) 2016 - Heliosphere Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the Heliosphere's project which is licensed under the 
 * Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 * 
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.common.resource.internal;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.heliosphere.common.resource.ResourceException;

/**
 * Provides an abstract implementation of a resource.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
public abstract class AbstractResource implements IResource
{
	/**
	 * Default serialization identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Underlying file object.
	 */
	private File file = null;

	/**
	 * Creates a new abstract resource given a pathname.
	 * <hr>
	 * @param pathname Resource pathname.
	 * @throws ResourceException Thrown in case an error occurred while trying to access the resource.
	 */
	@SuppressWarnings("nls")
	public AbstractResource(String pathname) throws ResourceException
	{
		if (!loadAbsolute(pathname))
		{
			if (!loadRelative(pathname))
			{
				if (!loadRelativeSeparator(pathname))
				{
					throw new ResourceException(String.format("The resource specified by the path name: %1s can not be found", pathname));
				}
			}
		}
	}

	@Override
	public File getFile()
	{
		return file;
	}

	/**
	 * Loads the file considering its pathname is absolute.
	 * <hr>
	 * @param pathname Resource path name.
	 * @return {@code True} if the resource can be loaded, {@code false} otherwise.
	 */
	@SuppressWarnings("unused")
	private final boolean loadAbsolute(String pathname)
	{
		this.file = new File(pathname);
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException ioe)
			{
				return false;
			}
		}

		//String fullPath = file.getAbsolutePath();

		return this.file.exists();
	}

	/**
	 * Loads the file considering its pathname is relative.
	 * <hr>
	 * @param pathname Resource path name.
	 * @return {@code True} if the resource can be loaded, {@code false} otherwise.
	 */
	@SuppressWarnings("unused")
	private final boolean loadRelative(String pathname)
	{
		URL url = null;

		try
		{
			// Maybe the file pathname is relative.
			url = Thread.currentThread().getContextClassLoader().getResource(pathname);

			this.file = new File(url.toURI());
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException ioe)
				{
					return false;
				}
			}
		}
		catch (Exception e)
		{
			// Try to get the parent directory of the file ... maybe the file does not exist!
			file = new File(pathname);
			String name = FilenameUtils.getName(pathname);
			File directory = file.getParentFile();
			try
			{
				url = Thread.currentThread().getContextClassLoader().getResource(file.getParent());

				try
				{
					file = new File(url.toURI());
					if (directory.exists() && directory.isDirectory())
					{
						// OK, found the parent directory, then create the file as it does not exist.
						file = new File(directory.getAbsolutePath() + File.separator + name);
						try
						{
							file.createNewFile();
						}
						catch (IOException e1)
						{
							return false;
						}
					}
					else 
					{
						return false;
					}
				}
				catch (URISyntaxException urie)
				{
					return false;
				}
			}
			catch (Exception e2)
			{
				return false;
			}
		}

		return this.file.exists();
	}

	/**
	 * Loads the file considering its pathname is relative.
	 * <hr>
	 * @param pathname Resource path name.
	 * @return {@code True} if the resource can be loaded, {@code false} otherwise.
	 */
	@SuppressWarnings({ "nls", "unused" })
	private final boolean loadRelativeSeparator(String pathname)
	{
		URL url = null;
		String path = null;

		// Try to remove the leading file separator, if one exist.
		if (pathname.startsWith(File.separator) || pathname.startsWith("."))
		{
			try
			{
				// Maybe the file pathname is relative.
				if (pathname.startsWith(File.separator))
				{
					path = pathname.replaceFirst(File.separator, "");
				}
				else 
				{
					path = pathname.replaceFirst("." + File.separator, "");
				}
				
				url = Thread.currentThread().getContextClassLoader().getResource(path);
				this.file = new File(url.toURI());
				if (!file.exists())
				{
					try
					{
						file.createNewFile();
					}
					catch (IOException ioe)
					{
						return false;
					}
				}
			}
			catch (Exception e)
			{
				// Try to get the parent directory of the file ... maybe the file does not exist!
				file = new File(pathname);
				String name = FilenameUtils.getName(pathname);
				File directory = file.getParentFile();
				try
				{
					url = Thread.currentThread().getContextClassLoader().getResource(file.getParent().replaceFirst(File.separator, ""));

					try
					{
						directory = new File(url.toURI());
						if (directory.getAbsoluteFile().exists() && directory.getAbsoluteFile().isDirectory())
						{
							// OK, found the parent directory, then create the file as it does not exist.
							file = new File(directory.getAbsolutePath() + File.separator + name);
							try
							{
								file.createNewFile();
							}
							catch (IOException e1)
							{
								return false;
							}
						}
					}
					catch (URISyntaxException urie)
					{
						return false;
					}
				}
				catch (Exception e2)
				{
					return false;
				}
			}
		}

		if (file == null)
		{
			return false;
		}

		return this.file.exists();
	}
}
