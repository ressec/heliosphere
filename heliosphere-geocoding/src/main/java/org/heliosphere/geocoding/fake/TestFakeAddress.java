/*
 * Copyright(c) 2017 - Heliosphere Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the Heliosphere's project which is licensed under the 
 * Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 * 
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.geocoding.fake;

import java.util.Locale;

import com.github.javafaker.Faker;

/**
 * A sample Java application that generates a set of fake addresses in a pre-defined language.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
public class TestFakeAddress
{	
	/**
	 * Java application main-entry point.
	 * <p>
	 * @param arguments Arguments passed on the command line.
	 */
	@SuppressWarnings("nls")
	public static void main(String[] arguments)
	{
		Faker faker = new Faker(Locale.FRENCH);
		StringBuilder address = null;
		
		for (int i = 0; i < 50; i++)
		{
			address = new StringBuilder(faker.address().streetAddress())
					.append(" ")
					.append(faker.address().zipCode())
					.append(" ")
					.append(faker.address().city())
					.append(" ")
					//.append(faker.address().country()); // The Faker API has a bug!
					.append("France");
			
			System.out.println(String.format("Address: %1s", address.toString()));
		}
	}
}
