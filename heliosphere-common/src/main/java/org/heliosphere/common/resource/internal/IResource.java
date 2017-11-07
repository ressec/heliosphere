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
import java.io.Serializable;

/**
 * Defines the behavior of a {@code Resource} ; typically a file.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
public interface IResource extends Serializable
{
	/**
	 * Returns the file object representing the resource on the file system.
	 * <hr>
	 * @return {@link File}.
	 */
	File getFile();
}
