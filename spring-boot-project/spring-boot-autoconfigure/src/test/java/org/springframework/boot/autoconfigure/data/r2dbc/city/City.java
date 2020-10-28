package org.springframework.boot.autoconfigure.data.r2dbc.city;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("CITY")
public class City {

	@Id
	private Long id;

	private String name;

	private String state;

	private String country;

	private String map;

	protected City() {
	}

	public City(String name, String country) {
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public String getState() {
		return this.state;
	}

	public String getCountry() {
		return this.country;
	}

	public String getMap() {
		return this.map;
	}

	@Override
	public String toString() {
		return getName() + "," + getState() + "," + getCountry();
	}

}
