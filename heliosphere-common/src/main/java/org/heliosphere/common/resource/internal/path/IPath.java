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

import java.io.Serializable;
import java.util.List;

import org.heliosphere.common.resource.internal.IResource;

/**
 * Defines the behavior of a {@code Path}.
 *
 * @author <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
public interface IPath extends Serializable
{
	/**
	 * Returns the resource representing this path.
	 * <hr>
	 * @return {@link IResource}.
	 */
	IResource getResource();

	/**
	 * Returns a list of resources representing the content of this path.
	 * <hr>
	 * @return List of {@link IResource}.
	 */
	List<IResource> getResources();

	/**
	 * Adds a resource to this path.
	 * <hr>
	 * @param resource Resource to add.
	 */
	void addResource(IResource resource);
}
