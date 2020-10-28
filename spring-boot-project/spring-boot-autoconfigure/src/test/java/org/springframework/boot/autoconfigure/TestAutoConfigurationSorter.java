package org.springframework.boot.autoconfigure;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.springframework.core.type.classreading.MetadataReaderFactory;

/**
 * Public version of {@link AutoConfigurationSorter} for use in tests.
 *

 */
public class TestAutoConfigurationSorter extends AutoConfigurationSorter {

	public TestAutoConfigurationSorter(MetadataReaderFactory metadataReaderFactory) {
		super(metadataReaderFactory, AutoConfigurationMetadataLoader.loadMetadata(new Properties()));
	}

	@Override
	public List<String> getInPriorityOrder(Collection<String> classNames) {
		return super.getInPriorityOrder(classNames);
	}

}
