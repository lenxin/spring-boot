package org.springframework.boot.convert;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.TypeDescriptor;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Create a mock {@link TypeDescriptor} with optional {@link PeriodUnit @PeriodUnit} and
 * {@link PeriodFormat @PeriodFormat} annotations.
 *


 */
public final class MockPeriodTypeDescriptor {

	private MockPeriodTypeDescriptor() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TypeDescriptor get(ChronoUnit unit, PeriodStyle style) {
		TypeDescriptor descriptor = mock(TypeDescriptor.class);
		if (unit != null) {
			PeriodUnit unitAnnotation = AnnotationUtils.synthesizeAnnotation(Collections.singletonMap("value", unit),
					PeriodUnit.class, null);
			given(descriptor.getAnnotation(PeriodUnit.class)).willReturn(unitAnnotation);
		}
		if (style != null) {
			PeriodFormat formatAnnotation = AnnotationUtils
					.synthesizeAnnotation(Collections.singletonMap("value", style), PeriodFormat.class, null);
			given(descriptor.getAnnotation(PeriodFormat.class)).willReturn(formatAnnotation);
		}
		given(descriptor.getType()).willReturn((Class) Period.class);
		given(descriptor.getObjectType()).willReturn((Class) Period.class);
		return descriptor;
	}

}
