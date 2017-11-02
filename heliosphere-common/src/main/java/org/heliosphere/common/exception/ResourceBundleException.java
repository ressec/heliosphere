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
package org.heliosphere.common.exception;

import org.heliosphere.common.resource.ResourceException;
import org.heliosphere.common.resource.bundle.IBundle;

/**
 * Unchecked exception thrown to indicate an error occurred while processing a resource bundle.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
public class ResourceBundleException extends ResourceException
{
	/**
	 * Serialization identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception thrown to indicate an error occurred while processing a resource bundle.
	 */
	public ResourceBundleException()
	{
		super();
	}

	/**
	 * Exception thrown to indicate an error occurred while processing a resource bundle.
	 * <p>
	 * @param key Resource bundle key (enumerated value coming from an
	 * enumeration implementing the {@link IBundle} interface).
	 */
	public ResourceBundleException(final Enum<? extends IBundle> key)
	{
		super(key);
	}

	/**
	 * Exception thrown to indicate an error occurred while processing a resource bundle.
	 * <p>
	 * @param key Exception key (enumerated value coming from an enumeration
	 * implementing the {@link IExceptionType} interface).
	 * @param parameters List of parameters used to populate the exception
	 * message.
	 */
	public ResourceBundleException(final Enum<?> key, final Object... parameters)
	{
		super(key, parameters);
	}

	/**
	 * Exception thrown to indicate an error occurred while processing a resource bundle.
	 * <p>
	 * @param exception Parent exception.
	 */
	public ResourceBundleException(final Exception exception)
	{
		super(exception);
	}

	/**
	 * Exception thrown to indicate an error occurred while processing a resource bundle.
	 * <p>
	 * @param message Message describing the error being the cause of the raised
	 * exception.
	 */
	public ResourceBundleException(final String message)
	{
		super(message);
	}

	/**
	 * Exception thrown to indicate an error occurred while processing a resource bundle.
	 * <p>
	 * @param message Message describing the error being the cause of the raised
	 * exception.
	 * @param exception Parent exception.
	 */
	public ResourceBundleException(final String message, final Exception exception)
	{
		super(message, exception);
	}
}
