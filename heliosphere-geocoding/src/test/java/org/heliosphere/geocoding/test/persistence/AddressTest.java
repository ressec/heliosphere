/*
 * Copyright(c) 2017 - Trias Group Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the eMeal's project which is licensed under the
 * Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 * 
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.geocoding.test.persistence;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.heliosphere.geocoding.Geocoder;
import org.heliosphere.geocoding.persistence.Address;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.javafaker.Faker;

/**
 * Test class for the {@link Address} persistence class.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Resse Christophe - Heliosphere</a>
 * @version 1.0.0
 */
public class AddressTest
{
	/**
	 * JPA persistence unit to use.
	 */
	@SuppressWarnings("nls")
	private final static String PERSISTENCE_UNIT = "org.geocode.jpa.test";

	/**
	 * Factory manager.
	 */
	private static EntityManagerFactory factory = null;

	/**
	 * Persistence entity manager.
	 */
	private static EntityManager manager = null;

	/**
	 * Initialization of the test cases.
	 * <p>
	 * @throws Exception In case an error occurs during the initialization.
	 */
	@BeforeClass
	public static final void setUpBeforeClass() throws Exception
	{
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		manager = factory.createEntityManager();
	}

	/**
	 * Finalization of the test cases.
	 * <p>
	 * @throws Exception In case an error occurs during the finalization.
	 */
	@AfterClass
	public static final void tearDownAfterClass() throws Exception
	{
		if (manager != null)
		{
			manager.close();
		}

		if (factory != null)
		{
			factory.close();
		}
	}

	/**
	 * Sets up the fixture.
	 * <p>
	 * @throws Exception In case an error occurs during the setup phase.
	 */
	@Before
	public final void setUp() throws Exception
	{
		// Empty.
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
	 * Test the {@code Address.findAll} named query.
	 */
	@SuppressWarnings({ "static-method", "nls" })
	@Test
	public final void testFindAll()
	{
		final int COUNT = 90;

		try
		{
			List<Address> elements = manager.createNamedQuery("Address.findAll").getResultList();
			Assert.assertTrue(String.format("findAll() should have returned %d elements, but it returned: %d", Integer.valueOf(COUNT), Integer.valueOf(elements.size())), elements.size() == COUNT);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * Test the geocoder for one of the address using the Google geocoding API.
	 */
	@SuppressWarnings({ "static-method", "nls" })
	@Test
	public final void testGeocoder()
	{
		String value = "JU";
		Geocoder geocoder = new Geocoder();

		try
		{
			// Get all addresses having as locale 'JU' for JUnit tests.
			List<Address> addresses = manager.createNamedQuery("Address.findByLocale").setParameter("locale", value).getResultList();
			if (addresses.isEmpty())
			{
				fail("Should have returned at least one address addresses with locale set to: " + value);
			}

			for (Address address : addresses)
			{
				geocoder.resolve(address);

				Assert.assertTrue(
						String.format("Geolocation result for address: %1s should have returned: FOUND but has returned %2s",
								address.getUnformatted(), address.getResult()),
						address.getResult().equals("FOUND"));

				try
				{
					if (!manager.getTransaction().isActive())
					{
						manager.getTransaction().begin();
						manager.persist(address);
						manager.getTransaction().commit();
					}
				}
				catch (Exception e)
				{
					manager.getTransaction().rollback();
					fail(e.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	/**
	 * Test the creation of fake unformatted addresses for France.
	 */
	@SuppressWarnings({ "static-method", "nls" })
	@Test
	public final void testFakeAddressForFrance()
	{
		StringBuilder text = null;
		Address address = null;

		Faker faker = new Faker(Locale.FRANCE);

		try
		{
			if (!manager.getTransaction().isActive())
			{
				manager.getTransaction().begin();

				for (int i = 0; i < 10; i++)
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
			fail(e.getMessage());
		}
	}

	/**
	 * Test the creation of fake unformatted addresses for Italy.
	 */
	@SuppressWarnings({ "static-method", "nls" })
	@Test
	public final void testFakeAddressForItaly()
	{
		StringBuilder text = null;
		Address address = null;

		Faker faker = new Faker(Locale.ITALY);

		try
		{
			if (!manager.getTransaction().isActive())
			{
				manager.getTransaction().begin();

				for (int i = 0; i < 10; i++)
				{
					text = new StringBuilder(faker.address().streetAddress())
							.append(" ")
							.append(faker.address().zipCode())
							.append(" ")
							.append(faker.address().city())
							.append(" ")
							//.append(faker.address().country());
							.append("Italia");

					address = new Address(text.toString(), true);
					address.setLocale("IT");
					manager.persist(address);
				}

				manager.getTransaction().commit();
			}
		}
		catch (Exception e)
		{
			manager.getTransaction().rollback();
			fail(e.getMessage());
		}
	}
}
