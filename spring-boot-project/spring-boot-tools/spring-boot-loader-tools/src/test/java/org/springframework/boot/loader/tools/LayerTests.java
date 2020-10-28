package org.springframework.boot.loader.tools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link Layer}.
 *


 */
class LayerTests {

	@Test
	void createWhenNameIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Layer(null)).withMessage("Name must not be empty");
	}

	@Test
	void createWhenNameIsEmptyThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Layer("")).withMessage("Name must not be empty");
	}

	@Test
	void createWhenNameContainsBadCharsThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Layer("bad!name"))
				.withMessage("Malformed layer name 'bad!name'");
	}

	@Test
	void equalsAndHashCode() {
		Layer layer1 = new Layer("testa");
		Layer layer2 = new Layer("testa");
		Layer layer3 = new Layer("testb");
		assertThat(layer1.hashCode()).isEqualTo(layer2.hashCode());
		assertThat(layer1).isEqualTo(layer1).isEqualTo(layer2).isNotEqualTo(layer3);
	}

	@Test
	void toStringReturnsName() {
		assertThat(new Layer("test")).hasToString("test");
	}

	@Test
	void createWhenUsingReservedNameThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Layer("ext"))
				.withMessage("Layer name 'ext' is reserved");
		assertThatIllegalArgumentException().isThrownBy(() -> new Layer("ExT"))
				.withMessage("Layer name 'ExT' is reserved");
		assertThatIllegalArgumentException().isThrownBy(() -> new Layer("springbootloader"))
				.withMessage("Layer name 'springbootloader' is reserved");
	}

}
