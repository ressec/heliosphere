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
package org.heliosphere.common.resource.bundle.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.heliosphere.common.test.resource.bundle.HonorificType;

/**
 * This annotation is used to associate resource bundles to a resource bundle enumeration.
 * <p>
 * This annotation is intended to be placed on methods that you want to get an automatic binding between enumerated values and given resource bundle
 * entries in a resource bundle file.
 * <p>
 * For an example on how to use it, take a look at the {@link HonorificType} enumeration.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BundleEnum
{
	/**
	 * Relative path name of the resource bundle file without extension and language extension.
	 * <p>
	 * Folders must be separated by a '.' (dot) character such as in the following example:
	 * <p>
	 * <b>Example:</b>
	 * <p>
	 * If the resource bundles are located in the 'src/main/resources/bundle/type/resource-bundle-file_**.properties' 
	 * resource files then the 'file' property will have the value:
	 * <p>
	 * <code>file="bundle.type.resource-bundle-file"</code>
	 * <hr>
	 * @return Resource bundle file name.
	 */
	String file();

	/**
	 * Path of the resource key.
	 * <p>
	 * <b>Example:</b>
	 * <p>
	 * <code>path="path.to.the.resource.string.key"</code>
	 * <hr>
	 * @return Path of the resource string to use.
	 */
	String path();
}
