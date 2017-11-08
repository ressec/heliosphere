package org.heliosphere.common.command.internal.metadata;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a command domain definition.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
@ToString
public class CommandDomain
{
	/**
	 * Domain name.
	 */
	@Getter
	private String name;

	/**
	 * Domain description.
	 */
	@Getter
	@Setter
	private String description;

	/**
	 * Creates a new command domain.
	 * <p>
	 * @param name Domain name.
	 */
	public CommandDomain(final @NonNull String name)
	{
		this.name = name;
	}
}
