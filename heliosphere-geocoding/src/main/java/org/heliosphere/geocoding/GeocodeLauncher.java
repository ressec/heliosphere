/*
 * Copyright(c) 2017-2017 Heliosphere Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the Heliosphere's project which is licensed under the
 * Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 * 
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.geocoding;

import org.heliosphere.geocoding.persistence.Address;

public class GeocodeLauncher
{
	public static void main(String[] args)
	{
		Geocoder geocoder = new Geocoder();
		geocoder.resolve(new Address());
	}
}
