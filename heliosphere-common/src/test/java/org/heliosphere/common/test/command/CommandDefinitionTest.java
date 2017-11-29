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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
	public final void testCommandCategorie()
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
	 * Test the registration of the command parameters.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Ignore
	public final void testCommandParameter()
	{
		fail("Not implemented!");
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
