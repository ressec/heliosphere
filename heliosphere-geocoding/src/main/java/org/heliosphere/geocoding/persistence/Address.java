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
package org.heliosphere.geocoding.persistence;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Address persistent entity.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
@Entity(name = "address")
@Table(schema = "public")
@NamedQueries({
	@NamedQuery(name = "Address.findAll", query = "select a from address a"),
	@NamedQuery(name = "Address.test.findIBMAddress", query = "select a from address a where a.unformatted = '25 rue eugène marziano 1227 geneva suisse'")
})

public class Address implements Serializable
{
	/**
	 * Default serialization identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Unique identifier of an address (primary key).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", insertable = true, updatable = false)
	@Getter
	private Long uid;

	/**
	 * Unformatted address.
	 */
	@Column(name = "unformatted", nullable = false, length = 255)
	@Getter
	@Setter
	private String unformatted;

	/**
	 * Formatted address (after geolocation).
	 */
	@Column(name = "formatted", nullable = true, length = 255)
	@Getter
	@Setter
	private String formatted;

	/**
	 * Does this address is a fake one?
	 */
	@Column(name = "is_fake", nullable = true)
	@Getter
	@Setter
	private Boolean isFake;

	/**
	 * Type (after geolocation).
	 */
	@Column(name = "type", nullable = true, length = 100)
	@Getter
	@Setter
	private String type;

	/**
	 * Locale.
	 */
	@Column(name = "locale", nullable = true, length = 10)
	@Getter
	@Setter
	private String locale;

	/**
	 * Street number.
	 */
	@Column(name = "street_number", nullable = true, length = 10)
	@Getter
	@Setter
	private String streetNumber;

	/**
	 * Route.
	 */
	@Column(name = "route", nullable = true, length = 100)
	@Getter
	@Setter
	private String route;

	/**
	 * Sub-locality.
	 */
	@Column(name = "sublocality", nullable = true, length = 100)
	@Getter
	@Setter
	private String subLocality;

	/**
	 * Locality.
	 */
	@Column(name = "locality", nullable = true, length = 100)
	@Getter
	@Setter
	private String locality;

	/**
	 * Political (full).
	 */
	@Column(name = "political", nullable = true, length = 100)
	@Getter
	@Setter
	private String fullPolitical;

	/**
	 * Political (short).
	 */
	@Column(name = "political_short", nullable = true, length = 100)
	@Getter
	@Setter
	private String shortPolitical;

	/**
	 * Region (full).
	 */
	@Column(name = "region_full", nullable = true, length = 100)
	@Getter
	@Setter
	private String fullRegion;

	/**
	 * Region (short).
	 */
	@Column(name = "region_short", nullable = true, length = 100)
	@Getter
	@Setter
	private String shortRegion;

	/**
	 * City (full).
	 */
	@Column(name = "city", nullable = true, length = 100)
	@Getter
	@Setter
	private String fullCity;

	/**
	 * City (short).
	 */
	@Column(name = "city_short", nullable = true, length = 100)
	@Getter
	@Setter
	private String shortCity;

	/**
	 * Postal code.
	 */
	@Getter
	@Setter
	@Column(name = "postal_code", nullable = true, length = 10)
	private String postalCode;

	/**
	 * Country (full).
	 */
	@Column(name = "country", nullable = true, length = 100)
	@Getter
	@Setter
	private String fullCountry;

	/**
	 * Country (short).
	 */
	@Column(name = "country_short", nullable = true, length = 100)
	@Getter
	@Setter
	private String shortCountry;

	/**
	 * Result of the geolocation.
	 */
	@Column(name = "result", nullable = true, length = 100)
	@Getter
	@Setter
	private String result;

	/**
	 * Time stamp of the geolocation.
	 */
	@Column(name = "timestamp", nullable = true)
	@Getter
	@Setter
	private Time timestamp;
	
	/**
	 * Creates a new empty address.
	 */
	public Address()
	{
		// Empty ; required by JPA.
	}

	/**
	 * Creates a new address.
	 * <hr>
	 * @param unformatted Unformatted address.
	 * @param isFake {@code True} if the address is a fake one, {@code false} otherwise.
	 */
	public Address(final @NonNull String unformatted, final boolean isFake)
	{
		this.unformatted = unformatted;
		this.isFake = Boolean.valueOf(isFake);
	}
}
