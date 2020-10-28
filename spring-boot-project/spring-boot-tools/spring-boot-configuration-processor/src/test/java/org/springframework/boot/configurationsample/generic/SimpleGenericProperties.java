package org.springframework.boot.configurationsample.generic;

import java.time.Duration;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Simple properties with resolved generic information.
 *

 */
@ConfigurationProperties("generic")
public class SimpleGenericProperties extends AbstractIntermediateGenericProperties<Duration> {

}
