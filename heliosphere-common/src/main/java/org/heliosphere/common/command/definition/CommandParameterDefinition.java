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
package org.heliosphere.common.command.definition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a command parameter metadata (a parameter definition).
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
@ToString
public class CommandParameterDefinition
{
	/**
	 * Command parameter name.
	 */
	@Getter
	private String name;

	/**
	 * Command parameter description.
	 */
	@Getter
	@Setter
	private String description;

	/**
	 * Command parameter type.
	 */
	@Getter
	@Setter
	private String type;

	/**
	 * Command parameter minimum value.
	 */
	@Getter
	@Setter
	private String minimum;

	/**
	 * Command parameter maximum value.
	 */
	@Getter
	@Setter
	private String maximum;

	/**
	 * Command parameter syntax (regular expression).
	 */
	@Getter
	@Setter
	private String syntax;

	/**
	 * Command parameter formatter class name.
	 */
	@Getter
	@Setter
	private String formatter;

	/**
	 * Command parameter validator class name.
	 */
	@Getter
	@Setter
	private String validator;

	/**
	 * Collection of examples.
	 */
	@Getter
	private List<String> examples;

	/**
	 * Creates a new command parameter metadata (definition).
	 * <hr>
	 * @param name Command parameter name.
	 */
	public CommandParameterDefinition(final @NonNull String name)
	{
		this.name = name;
	}

	/**
	 * Adds an example to the list.
	 * <hr>
	 * @param example Example to add.
	 */
	public final void addExample(final @NonNull String example)
	{
		if (examples == null)
		{
			examples = new ArrayList<>();
		}

		examples.add(example);
	}

	/**
	 * Sets the example list.
	 * <hr>
	 * @param examples List of examples to add.
	 */
	public final void setExamples(final @NonNull List<String> examples)
	{
		if (this.examples == null)
		{
			this.examples = examples;
		}
		else
		{
			this.examples.addAll(examples);
		}
	}
}
