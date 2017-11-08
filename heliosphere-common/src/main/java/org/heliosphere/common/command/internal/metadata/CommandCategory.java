package org.heliosphere.common.command.internal.metadata;

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
}
