package org.springframework.boot.test.mock.mockito.example;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Custom qualifier for testing.
 *

 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomQualifier {

}
