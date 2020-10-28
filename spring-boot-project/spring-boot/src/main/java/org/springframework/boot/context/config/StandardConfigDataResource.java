package org.springframework.boot.context.config;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * {@link ConfigDataResource} backed by a {@link Resource}.
 *


 * @since 2.4.0
 */
public class StandardConfigDataResource extends ConfigDataResource {

	private final StandardConfigDataReference reference;

	private final Resource resource;

	/**
	 * Create a new {@link StandardConfigDataResource} instance.
	 * @param reference the resource reference
	 * @param resource the underlying resource
	 */
	StandardConfigDataResource(StandardConfigDataReference reference, Resource resource) {
		Assert.notNull(reference, "Reference must not be null");
		Assert.notNull(resource, "Resource must not be null");
		this.reference = reference;
		this.resource = resource;
	}

	StandardConfigDataReference getReference() {
		return this.reference;
	}

	Resource getResource() {
		return this.resource;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		StandardConfigDataResource other = (StandardConfigDataResource) obj;
		return this.resource.equals(other.resource);
	}

	@Override
	public int hashCode() {
		return this.resource.hashCode();
	}

	@Override
	public String toString() {
		if (this.resource instanceof FileSystemResource || this.resource instanceof FileUrlResource) {
			try {
				return "file [" + this.resource.getFile().toString() + "]";
			}
			catch (IOException ex) {
			}
		}
		return this.resource.toString();
	}

}
