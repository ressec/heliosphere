/*
 * Copyright(c) 2016 - Heliosphere Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the Heliosphere's project which is licensed under the
 * Apache license version 2 and use is subject to license terms. You should have
 * received a copy of the license with the project's artifact binaries and/or
 * sources.
 *
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.common.test.command;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.heliosphere.common.command.CommandManager;
import org.heliosphere.common.command.exception.CommandManagerException;
import org.heliosphere.common.command.internal.metadata.CommandCategory;
import org.heliosphere.common.command.internal.metadata.CommandDomain;
import org.heliosphere.common.command.internal.metadata.CommandGroup;
import org.heliosphere.common.command.internal.metadata.CommandMetadata;
import org.heliosphere.common.command.internal.metadata.CommandParameterMetadata;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test case for the {@link CommandManager} class and services.
 * <hr>
 * @author Resse Christophe, Heliosphere Corp. 2017
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public final class CommandDefinitionTest
{
	/**
	 * Initialization of the test cases.
	 * <p>
	 * @throws Exception In case an error occurs during the initialization.
	 */
	@BeforeClass
	public static final void setUpBeforeClass() throws Exception
	{
		// Empty.
	}

	/**
	 * Finalization of the test cases.
	 * <p>
	 * @throws Exception In case an error occurs during the finalization.
	 */
	@AfterClass
	public static final void tearDownAfterClass() throws Exception
	{
		// Empty
	}

	/**
	 * Sets up the fixture.
	 * <p>
	 * @throws Exception In case an error occurs during the setup phase.
	 */
	@Before
	public final void setUp() throws Exception
	{
		// Empty
	}

	/**
	 * Tears down the fixture.
	 * <p>
	 * @throws Exception In case an error occurs during the tear down phase.
	 */
	@After
	public final void tearDown() throws Exception
	{
		// Empty
	}

	/**
	 * Test the registration of a set of command definitions using a .properties file.
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public final void testRegisterFromPropertiesFile()
	{
		/**
		 * Expected number of registered command definitions.
		 */
		final int EXPECTED_COUNT = 3;

		try
		{
			CommandManager.registerFromFile("./command/definition/command-system.properties");

			int count = CommandManager.getCommandCount();
			Assert.assertTrue(
					String.format("Expected number of registered commands is %1d but returned number is %2d", EXPECTED_COUNT, count),
					CommandManager.getCommandCount() == EXPECTED_COUNT);
		}
		catch (CommandManagerException e)
		{
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test the registration of the command categories.
	 */
	@SuppressWarnings("static-method")
	@Test
	public final void testCommandCategory()
	{
		// Command categories.
		String[] names = new String[] { "debug", "normal", "administration", "system", "power" };

		try
		{
			CommandManager.registerFromFile("./command/definition/command-system.properties");
			List<CommandCategory> categories = CommandManager.getCategories();

			for (CommandCategory category : categories)
			{
				Assert.assertTrue(
						String.format("Excepted category names: %1s but found value was: %2s", names, category.getName()),
						Arrays.asList(names).contains(category.getName()));
			}
		}
		catch (CommandManagerException e)
		{
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test the registration of the command domains.
	 */
	@SuppressWarnings("static-method")
	@Test
	public final void testCommandDomain()
	{
		// Command domains.
		String[] names = new String[] { "application" };

		try
		{
			CommandManager.registerFromFile("./command/definition/command-system.properties");
			List<CommandDomain> domains = CommandManager.getDomains();

			for (CommandDomain domain : domains)
			{
				Assert.assertTrue(
						String.format("Excepted domain names are: %1s but extracted value was: %2s", names, domain.getName()),
						Arrays.asList(names).contains(domain.getName()));
			}
		}
		catch (CommandManagerException e)
		{
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test the registration of the command groups.
	 */
	@SuppressWarnings("static-method")
	@Test
	public final void testCommandGroup()
	{
		// Command groups.
		String[] names = new String[] { "chat", "system" };

		try
		{
			CommandManager.registerFromFile("./command/definition/command-system.properties");
			List<CommandGroup> groups = CommandManager.getGroups();

			for (CommandGroup group : groups)
			{
				Assert.assertTrue(
						String.format("Excepted group names are: %1s but extracted value was: %2s", names, group.getName()),
						Arrays.asList(names).contains(group.getName()));
			}
		}
		catch (CommandManagerException e)
		{
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test the registration of the commands.
	 */
	@SuppressWarnings("static-method")
	@Test
	public final void testCommand()
	{
		// Commands.
		String[] names = new String[] { "quit", "say", "afk" };

		try
		{
			CommandManager.registerFromFile("./command/definition/command-system.properties");
			List<CommandMetadata> commands = CommandManager.getCommands();

			for (CommandMetadata command : commands)
			{
				Assert.assertTrue(
						String.format("Excepted command names are: %1s but extracted value was: %2s", names, command.getName()),
						Arrays.asList(names).contains(command.getName()));
			}
		}
		catch (CommandManagerException e)
		{
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test the registration of a command attributes.
	 */
	@SuppressWarnings("static-method")
	@Test
	public final void testCommandAttributes()
	{
		String   COMMAND_NAME = "say";
		String   COMMAND_DESCRIPTION = "Sends a message to another user.";
		String   COMMAND_PROCESSOR = "org.heliosphere.common.command.processor.default.chat.SayCommandProcessor";
		String[] COMMAND_ALIASES = {"tell"};
		String[] COMMAND_EXAMPLES = {"/say -to Tommy Lee Jones -text Hi Tom ... nice to see you again ;)"};

		try
		{
			CommandManager.registerFromFile("./command/definition/command-system.properties");
			CommandMetadata command = CommandManager.getCommand(COMMAND_NAME);

			Assert.assertTrue(
					String.format("Command: %1$s has not been found!", COMMAND_NAME),
					command != null);

			Assert.assertTrue(
					String.format("Command: %1$s has not been found!", COMMAND_NAME),
					command.getName().equals(COMMAND_NAME));

			Assert.assertTrue(
					String.format("Command: %1$s expected description: %2$s but was: %3$s", COMMAND_NAME, COMMAND_DESCRIPTION, command.getDescription()),
					command.getDescription().equals(COMMAND_DESCRIPTION));

			for (String alias : command.getAliases())
			{
				Assert.assertTrue(
						String.format("Command: %1$s expected aliases: %2$s but found alias: %3$s", COMMAND_NAME, COMMAND_ALIASES, alias),
						Arrays.asList(COMMAND_ALIASES).contains(alias));
			}

			for (String example : command.getExamples())
			{
				Assert.assertTrue(
						String.format("Command: %1$s expected examples: %2$s but found example: %3$s", COMMAND_NAME, COMMAND_EXAMPLES, example),
						Arrays.asList(COMMAND_EXAMPLES).contains(example));
			}

			Assert.assertTrue(
					String.format("Command: %1$s expected vaidator: %2$s but was: %3$s", COMMAND_NAME, COMMAND_PROCESSOR, command.getProcessor()),
					command.getProcessor().equals(COMMAND_PROCESSOR));
		}
		catch (CommandManagerException e)
		{
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test the registration of the command parameters.
	 */
	@SuppressWarnings("static-method")
	@Test
	public final void testCommandParameter()
	{
		String   COMMAND_NAME = "say";
		String   COMMAND_PARAMETER_NAME = "recipient";
		String   COMMAND_PARAMETER_DESCRIPTION = "The recipient of the message.";
		String   COMMAND_PARAMETER_VALIDATOR = "org.heliosphere.common.parameter.validator.default.StringParameterValidator";
		String   COMMAND_PARAMETER_FORMATTER = "None | Lower | Upper | Capitalize | <Custom Formatter Class Path Name>";
		String   COMMAND_PARAMETER_SYNTAX = "(-to)\\s++([a-zA-Z-_]+\\s?)+";
		String   COMMAND_PARAMETER_TYPE = "String";
		String   COMMAND_PARAMETER_MINIMUM = "1";
		String   COMMAND_PARAMETER_MAXIMUM = "20";

		try
		{
			CommandManager.registerFromFile("./command/definition/command-system.properties");
			CommandMetadata command = CommandManager.getCommand(COMMAND_NAME);

			Assert.assertTrue(
					String.format("Command: %1$s has not been found!", COMMAND_NAME),
					command != null);

			CommandParameterMetadata parameter = command.getParameter(COMMAND_PARAMETER_NAME);

			Assert.assertTrue(
					String.format("Command %1$s expected parameter: %2$s has not been found!", COMMAND_NAME, COMMAND_PARAMETER_NAME),
					parameter != null);

			Assert.assertTrue(
					String.format("Command %1$s expected parameter name: %2$s but found %3$s", COMMAND_NAME, COMMAND_PARAMETER_NAME, parameter.getName()),
					parameter.getName().equals(COMMAND_PARAMETER_NAME));

			Assert.assertTrue(
					String.format("Command: %1$s expected parameter description: %2$s but found: %3$s", COMMAND_NAME, COMMAND_PARAMETER_DESCRIPTION, parameter.getDescription()),
					parameter.getDescription().equals(COMMAND_PARAMETER_DESCRIPTION));

			Assert.assertTrue(
					String.format("Command: %1$s, parameter %2$s: expected validator was %3$s but found: %4$s", command.getName(), parameter.getName(), COMMAND_PARAMETER_VALIDATOR, parameter.getValidator()),
					COMMAND_PARAMETER_VALIDATOR.equals(parameter.getValidator()));

			Assert.assertTrue(
					String.format("Command: %1$s, parameter %2$s: expected formatter value was %3$s but found value: %4$s", command.getName(), parameter.getName(), COMMAND_PARAMETER_FORMATTER, parameter.getFormatter()),
					COMMAND_PARAMETER_FORMATTER.equals(parameter.getFormatter()));

			Assert.assertTrue(
					String.format("Command: %1$s, parameter %2$s: expected syntax value was %3$s but found value: %4$s", command.getName(), parameter.getName(), COMMAND_PARAMETER_SYNTAX, parameter.getSyntax()),
					COMMAND_PARAMETER_SYNTAX.equals(parameter.getSyntax()));

			Assert.assertTrue(
					String.format("Command: %1$s, parameter %2$s: expected type value was %3$s but found value: %4$s", command.getName(), parameter.getName(), COMMAND_PARAMETER_TYPE, parameter.getType()),
					COMMAND_PARAMETER_TYPE.equalsIgnoreCase(parameter.getType()));

			Assert.assertTrue(
					String.format("Command: %1$s, parameter %2$s: expected minimum value was %3$s but found value: %4$s", command.getName(), parameter.getName(), COMMAND_PARAMETER_MINIMUM, parameter.getMinimum()),
					COMMAND_PARAMETER_MINIMUM.equals(parameter.getMinimum()));

			Assert.assertTrue(
					String.format("Command: %1$s, parameter %2$s: expected maximum value was %3$s but found value: %4$s", command.getName(), parameter.getName(), COMMAND_PARAMETER_MAXIMUM, parameter.getMaximum()),
					COMMAND_PARAMETER_MAXIMUM.equals(parameter.getMaximum()));
		}
		catch (CommandManagerException e)
		{
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test a non existing .properties file.
	 * @throws CommandManagerException Thrown in case an error occurred while loading the command definitions file.
	 */
	@SuppressWarnings("static-method")
	@Test(expected = CommandManagerException.class )
	public final void testNonExistingFile() throws CommandManagerException
	{
		CommandManager.registerFromFile("./command/definition/command-system-shadow.properties");
	}
}
