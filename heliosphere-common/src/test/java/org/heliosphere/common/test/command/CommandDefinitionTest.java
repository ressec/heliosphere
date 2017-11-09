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

import org.heliosphere.common.command.CommandManager;
import org.heliosphere.common.command.exception.CommandManagerException;
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
	public final void registerCommandUsingPropertiesFile()
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
