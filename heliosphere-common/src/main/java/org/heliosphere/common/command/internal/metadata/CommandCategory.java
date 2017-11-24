/*
 * Copyright(c) 2017-2017 Heliosphere Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the Heliosphere's project which is licensed under the
 * Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 * 
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.common.command.internal.metadata;

import org.heliosphere.common.command.exception.CommandCategoryException;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a command category definition.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
@ToString
public class CommandCategory
{
	/**
	 * Category name.
	 */
	@Getter
	private String name;

	/**
	 * Category description.
	 */
	@Getter
	@Setter
	private String description;

	/**
	 * Category prefix.
	 */
	@Getter
	@Setter
	private String prefix;

	/**
	 * Creates a new command category.
	 * <p>
	 * @param name Category name.
	 */
	public CommandCategory(final @NonNull String name)
	{
		this.name = name;
	}

	/**
	 * Checks if the command category is valid.
	 * <p>
	 * @throws CommandCategoryException Thrown in case an error occurred while validating the command category.
	 */
	@SuppressWarnings("nls")
	public void checkValidity() throws CommandCategoryException
	{
		if (getName() == null || getPrefix() == null)
		{
			throw new CommandCategoryException(String.format("Command category: %1s is not valid. Some required fields are missing!", getName()));
		}
	}
}
