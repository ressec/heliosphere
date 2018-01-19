/*
 * Copyright(c) 2017-2018 Heliosphere Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the Heliosphere's project which is licensed under the
 * Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 * 
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.terminal;

import org.heliosphere.common.command.ICommandCoordinator;

import lombok.NonNull;

/**
 * A terminal provides a dedicated window with an input and output allowing to
 * display messages and to enter commands.
 * <hr>
 * @author  <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
public interface ITerminal
{
	/**
	 * Registers a command coordinator that will be responsible to handle execution of commands.
	 * <p>
	 * @param coordinator Command coordinator to register.
	 */
	void registerCommandCoordinator(@NonNull ICommandCoordinator coordinator);
}
