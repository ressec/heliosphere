package org.heliosphere.common.command.internal.metadata;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a command group definition.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
@ToString
public final class CommandGroup
{
	/**
	 * Group name.
	 */
	@Getter
	private String name;

	/**
	 * Group description.
	 */
	@Getter
	@Setter
	private String description;

	/**
	 * Creates a new command group.
	 * <p>
	 * @param name Group name.
	 */
	public CommandGroup(final @NonNull String name)
	{
		this.name = name;
	}
}
