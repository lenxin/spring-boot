package org.springframework.boot.web.servlet;

import javax.servlet.MultipartConfigElement;

import org.springframework.util.unit.DataSize;

/**
 * Factory that can be used to create a {@link MultipartConfigElement}.
 *

 * @since 1.4.0
 */
public class MultipartConfigFactory {

	private String location;

	private DataSize maxFileSize;

	private DataSize maxRequestSize;

	private DataSize fileSizeThreshold;

	/**
	 * Sets the directory location where files will be stored.
	 * @param location the location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Sets the maximum {@link DataSize size} allowed for uploaded files.
	 * @param maxFileSize the maximum file size
	 */
	public void setMaxFileSize(DataSize maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	/**
	 * Sets the maximum {@link DataSize} allowed for multipart/form-data requests.
	 * @param maxRequestSize the maximum request size
	 */
	public void setMaxRequestSize(DataSize maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}

	/**
	 * Sets the {@link DataSize size} threshold after which files will be written to disk.
	 * @param fileSizeThreshold the file size threshold
	 */
	public void setFileSizeThreshold(DataSize fileSizeThreshold) {
		this.fileSizeThreshold = fileSizeThreshold;
	}

	/**
	 * Create a new {@link MultipartConfigElement} instance.
	 * @return the multipart config element
	 */
	public MultipartConfigElement createMultipartConfig() {
		long maxFileSizeBytes = convertToBytes(this.maxFileSize, -1);
		long maxRequestSizeBytes = convertToBytes(this.maxRequestSize, -1);
		long fileSizeThresholdBytes = convertToBytes(this.fileSizeThreshold, 0);
		return new MultipartConfigElement(this.location, maxFileSizeBytes, maxRequestSizeBytes,
				(int) fileSizeThresholdBytes);
	}

	/**
	 * Return the amount of bytes from the specified {@link DataSize size}. If the size is
	 * {@code null} or negative, returns {@code defaultValue}.
	 * @param size the data size to handle
	 * @param defaultValue the default value if the size is {@code null} or negative
	 * @return the amount of bytes to use
	 */
	private long convertToBytes(DataSize size, int defaultValue) {
		if (size != null && !size.isNegative()) {
			return size.toBytes();
		}
		return defaultValue;
	}

}
