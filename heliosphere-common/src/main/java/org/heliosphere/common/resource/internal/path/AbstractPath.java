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
package org.heliosphere.common.resource.internal.path;

import java.util.ArrayList;
import java.util.List;

import org.heliosphere.common.resource.internal.IResource;

/**
 * Provides an abstract implementation of a path.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
public abstract class AbstractPath implements IPath
{
	/**
	 * Default serialization identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Resource representing this path.
	 */
	private IResource resource;

	/**
	 * List of resources contained in this path.
	 */
	private List<IResource> resources;

	@Override
	public final IResource getResource()
	{
		return resource;
	}

	@Override
	public final List<IResource> getResources()
	{
		return resources;
	}

	@Override
	public final void addResource(IResource resource)
	{
		if (resources == null)
		{
			resources = new ArrayList<>();
		}

		resources.add(resource);
	}
}
