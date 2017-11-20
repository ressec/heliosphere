create schema public;
grant all on schema public to postgres;
grant all on schema public to public;

create type day_of_week as enum ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY');

/* ADDRESS */
create table address(uid serial not null constraint address_pkey	 primary key, unformatted varchar(255) not null, is_fake boolean, formatted varchar(255), type varchar(100), locale varchar(10), region_full varchar(100), region_short varchar(100), street_number varchar(10), route varchar(100), sublocality varchar(100), locality varchar(100), political varchar(100), political_short varchar(100), city varchar(100), city_short varchar(100), postal_code varchar(10), country varchar(100), country_short varchar(100), result varchar(100), timestamp time);

create unique index address_uid_uindex on address (uid);
comment on table address is 'Table containing the addresses information.';
comment on column address.uid is 'Address unique identifier.';
comment on column address.unformatted is 'Unformatted address.';
comment on column address.is_fake is 'Does this unformatted address is a fake one?';
comment on column address.formatted is 'Formatted address after geo-location.';
comment on column address.type is 'Address type.';
comment on column address.locale is 'Locale for this address.';
comment on column address.street_number is 'Street number of the address.';
comment on column address.route is 'Route of the address.';
comment on column address.sublocality is 'Sub-locality of the address.';
comment on column address.locality is 'Locality of the address.';
comment on column address.political is 'Political (full) of the address.';
comment on column address.political_short is 'Political (short) of the address.';
comment on column address.city is 'City (full) of the address.';
comment on column address.city_short is 'City (short) of the address.';
comment on column address.postal_code is 'Postal code of the address.';
comment on column address.country is 'Country (full) of the address.';
comment on column address.country_short is 'Country (short) of the address.';
comment on column address.result is 'Result of the geolocation of this address.';
comment on column address.timestamp is 'Time stamp of the geolocation of this address.';
comment on column address.region_full is 'Full region name.';
comment on column address.region_short is 'Short region name.';