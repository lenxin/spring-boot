package org.springframework.boot.configurationsample.recursive;

import org.springframework.boot.configurationsample.ConfigurationProperties;

@ConfigurationProperties("prefix")
public class RecursiveProperties {

	private RecursiveProperties recursive;

	public RecursiveProperties getRecursive() {
		return this.recursive;
	}

	public void setRecursive(RecursiveProperties recursive) {
		this.recursive = recursive;
	}

}
