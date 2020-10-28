package org.springframework.boot.autoconfigure.data.elasticsearch.city;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "city", shards = 1, replicas = 0, refreshInterval = "-1")
public class City implements Serializable {

	private static final long serialVersionUID = 1L;

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
