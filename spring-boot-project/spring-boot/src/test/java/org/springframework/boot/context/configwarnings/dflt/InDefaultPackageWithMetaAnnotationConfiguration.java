package org.springframework.boot.context.configwarnings.dflt;

import org.springframework.boot.context.configwarnings.annotation.MetaComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@MetaComponentScan
public class InDefaultPackageWithMetaAnnotationConfiguration {

}
