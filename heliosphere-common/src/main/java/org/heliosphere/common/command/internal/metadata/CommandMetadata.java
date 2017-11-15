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
package org.heliosphere.common.command.internal.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a command metadata (a command definition).
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
@ToString
public class CommandMetadata
{
	/**
	 * Command category.
	 */
	@Getter
	@Setter
	private String category;

	/**
	 * Command domain.
	 */
	@Getter
	@Setter
	private String domain;

	/**
	 * Command group.
	 */
	@Getter
	@Setter
	private String group;

	/**
	 * Command processor class name.
	 */
	@Getter
	@Setter
	private String processor;

	/**
	 * Command name.
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * Command description.
	 */
	@Getter
	@Setter
	private String description;

	/**
	 * Command format.
	 */
	@Getter
	@Setter
	private String format;

	/**
	 * Collection of command aliases.
	 */
	@Getter
	private List<String> aliases;

	/**
	 * Collection of command examples.
	 */
	@Getter
	private List<String> examples;

	/**
	 * Collection of command parameters.
	 */
	@Getter
	private Map<String, CommandParameterMetadata> parameters = new HashMap<>();

	/**
	 * Creates a new command metadata (definition).
	 * <hr>
	 * @param category Command category.
	 * @param domain Command domain.
	 * @param group Command group.
	 * @param name Command name.
	 */
	public CommandMetadata(final @NonNull String category, final @NonNull String domain, final @NonNull String group, final @NonNull String name)
	{
		this.category = category;
		this.domain = domain;
		this.group = group;
		this.name = name;
	}

	/**
	 * Adds a parameter to the list of parameters.
	 * <p>
	 * @param name Parameter name.
	 * @param parameter Parameter.
	 */
	public final void addParameter(final @NonNull String name, final @NonNull CommandParameterMetadata parameter)
	{
		if (!parameters.containsKey(name))
		{
			parameters.put(name, parameter);
		}
	}

	/**
	 * Adds an alias to the list of aliases.
	 * <p>
	 * @param alias Alias to add.
	 */
	public final void addAlias(final @NonNull String alias)
	{
		if (aliases == null)
		{
			aliases = new ArrayList<>();
		}
		
		if (!aliases.contains(alias))
		{
			aliases.add(alias);
		}
	}

	/**
	 * Adds an example to the list of examples.
	 * <p>
	 * @param example Example to add.
	 */
	public final void addExample(final @NonNull String example)
	{
		if (examples == null)
		{
			examples = new ArrayList<>();
		}

		if (!examples.contains(example))
		{
			examples.add(example);
		}
	}
}