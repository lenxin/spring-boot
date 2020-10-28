package org.springframework.boot.autoconfigure.packagestest.two;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.AutoConfigurationPackagesTests;
import org.springframework.context.annotation.Configuration;

/**
 * Sample configuration used in {@link AutoConfigurationPackagesTests}.
 *

 */
@Configuration(proxyBeanMethods = false)
@AutoConfigurationPackage
public class SecondConfiguration {

}
