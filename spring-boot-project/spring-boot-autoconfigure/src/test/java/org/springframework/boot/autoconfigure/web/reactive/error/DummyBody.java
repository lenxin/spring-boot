package org.springframework.boot.autoconfigure.web.reactive.error;

import javax.validation.constraints.NotNull;

public class DummyBody {

	@NotNull
	private String content;

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
