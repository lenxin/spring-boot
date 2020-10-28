package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.NestedConfigurationProperty;

/**
 * Demonstrate inner classes end up in metadata regardless of position in hierarchy and
 * without the use of {@link NestedConfigurationProperty @NestedConfigurationProperty}.
 *

 */
@ConfigurationProperties(prefix = "config")
public class InnerClassHierarchicalProperties {

	private Foo foo;

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

	public static class Foo {

		private Bar bar;

		public Bar getBar() {
			return this.bar;
		}

		public void setBar(Bar bar) {
			this.bar = bar;
		}

		public static class Baz {

			private String blah;

			public String getBlah() {
				return this.blah;
			}

			public void setBlah(String blah) {
				this.blah = blah;
			}

		}

	}

	public static class Bar {

		private String bling;

		private Foo.Baz baz;

		public String getBling() {
			return this.bling;
		}

		public void setBling(String foo) {
			this.bling = foo;
		}

		public Foo.Baz getBaz() {
			return this.baz;
		}

		public void setBaz(Foo.Baz baz) {
			this.baz = baz;
		}

	}

}
