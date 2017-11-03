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
package org.heliosphere.common.annotation;

import eu.infomas.annotation.AnnotationDetector.TypeReporter;

/**
 * Extend the {@link TypeReporter} interface to be able to construct by reflection
 * classes implementing the {@link IAnnotationVisitor} interface.
 * <hr>
 * @author  <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
public interface IAnnotationVisitor extends TypeReporter
{
	/**
	 * Delegates registration of annotated classes.
	 * <hr>
	 * @throws ClassNotFoundException Thrown in case an error occurred while trying to 
	 * delegate the registration of the annotated class.
	 */
	public void delegateRegistration() throws ClassNotFoundException;
}
