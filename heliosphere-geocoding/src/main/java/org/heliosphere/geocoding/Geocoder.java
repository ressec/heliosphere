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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.heliosphere.geocoding.persistence.Address;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;

import lombok.NonNull;

public final class Geocoder
{
	//	/**
	//	 * Persistence unit name.
	//	 */
	//	@SuppressWarnings("nls")
	//	private final static String PERSISTENCE_UNIT = "org.geocode.jpa";

	/**
	 * Google Maps API key.
	 */
	@SuppressWarnings("nls")
	private final static String GOOGLE_API_KEY = "AIzaSyDJsE-0pbKHV2NU1golO1VVZz5OZBnInTk";

	/**
	 * Entity manager factory.
	 */
	private EntityManagerFactory factory = null;

	/**
	 * Entity manager.
	 */
	private EntityManager manager = null;

	/**
	 * Are the entity managers initialized?
	 */
	private static boolean INITIALIZED = false;

	/**
	 * Geocoding results.
	 */
	private GeocodingResult[] results;

	/**
	 * Geocoding API context.
	 */
	private GeoApiContext context = null;

	/**
	 * Fake data generator.
	 */
	private 	Faker faker = Faker.instance(Locale.FRENCH);


	/**
	 * Creates a new geocoder.
	 */
	public Geocoder()
	{
		if (!INITIALIZED)
		{
			initialize();
		}
	}

	/**
	 * Resolves a list of addresses using the Google geocoding API.
	 * <p>
	 * @param addresses List of {@link Address} to resolve.
	 */
	public final void resolve(@NonNull List<Address> addresses)
	{
		for (Address address : addresses)
		{
			resolve(address);
		}
	}

	/**
	 * Resolves an address using the Google Geocoding API.
	 * <p>
	 * @param address {@link Address} to resolve.
	 * @return Resolved address.
	 */
	@SuppressWarnings("nls")
	public final Address resolve(@NonNull Address address)
	{
		Gson gson = null;
		GeocodingResult result = null;
		AddressType addressType = null;
		AddressComponent component = null;
		AddressComponentType type = null;

		try
		{
			results = GeocodingApi.geocode(context, address.getUnformatted()).await();
			gson = new GsonBuilder().setPrettyPrinting().create();

			if (results.length != 0)
			{
				if (results.length > 1)
				{
					System.out.println("More than 1 result for: " + address.getUnformatted());
				}

				address.setResult(GeocodeResult.FOUND.name());

				for (GeocodingResult result2 : results)
				{
					result = result2;

					for (AddressType type2 : result.types)
					{
						address.setType(Arrays.toString(result.types));
					}

					address.setFormatted(results[0].formattedAddress);

					for (AddressComponent addressComponent : result.addressComponents)
					{
						component = addressComponent;

						for (AddressComponentType type2 : component.types)
						{
							type = type2;
							if (type == AddressComponentType.STREET_NUMBER)
							{
								address.setStreetNumber(component.longName);
							}
							else if (type == AddressComponentType.ROUTE)
							{
								address.setRoute(component.longName);
							}
							else if (type == AddressComponentType.COUNTRY)
							{
								address.setFullCountry(component.longName);
								address.setShortCountry(component.shortName);
							}
							else if (type == AddressComponentType.LOCALITY)
							{
								address.setLocality(component.longName);
							}
							else if (type == AddressComponentType.POSTAL_CODE)
							{
								address.setPostalCode(component.longName);
							}
							else if (type == AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1)
							{
								address.setFullPolitical(component.longName);
								address.setShortPolitical(component.shortName);
							}
							else if (type == AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2)
							{
								address.setFullRegion(component.longName);
								address.setShortRegion(component.shortName);
							}
						}
					}
				}
			}
			else
			{
				address.setResult(GeocodeResult.NOTFOUND.name());
			}
		}
		catch (ApiException e)
		{
			// ApiException and its descendants represent an error returned by the remote API.
			// API errors are determined by the status field returned in any of the Geo API responses.
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return address;
	}

	/**
	 * Initializes the entity manager & factory.
	 */
	private final void initialize()
	{
		//		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		//		manager = factory.createEntityManager();

		context = new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();

		INITIALIZED = true;
	}

	/**
	 * Generates some fake addresses in a given locale and inject them in the database.
	 * <p>
	 * @param count Number of fake addresses to generate.
	 * @param locale Locale to use.
	 */
	@SuppressWarnings("nls")
	private final void generateFakeAddress(final int count, final @NonNull Locale locale)
	{
		Address address = null;
		StringBuilder text = null;

		try
		{
			if (!manager.getTransaction().isActive())
			{
				manager.getTransaction().begin();

				for (int i = 0; i < count; i++)
				{
					text = new StringBuilder(faker.address().streetAddress())
							.append(" ")
							.append(faker.address().zipCode())
							.append(" ")
							.append(faker.address().city())
							.append(" ")
							//.append(faker.address().country());
							.append("France");

					address = new Address(text.toString(), true);
					address.setLocale("FR");

					manager.persist(address);
				}

				manager.getTransaction().commit();
			}
		}
		catch (Exception e)
		{
			manager.getTransaction().rollback();
			// TODO Log the exception!
		}
	}
}
