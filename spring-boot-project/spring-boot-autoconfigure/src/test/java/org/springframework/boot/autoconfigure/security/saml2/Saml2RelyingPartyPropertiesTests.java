package org.springframework.boot.autoconfigure.security.saml2;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.Saml2MessageBinding;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Saml2RelyingPartyProperties}.
 *

 */
class Saml2RelyingPartyPropertiesTests {

	private final Saml2RelyingPartyProperties properties = new Saml2RelyingPartyProperties();

	@Deprecated
	@Test
	void customizeSsoUrlDeprecated() {
		bind("spring.security.saml2.relyingparty.registration.simplesamlphp.identity-provider.sso-url",
				"https://simplesaml-for-spring-saml/SSOService.php");
		assertThat(
				this.properties.getRegistration().get("simplesamlphp").getIdentityprovider().getSinglesignon().getUrl())
						.isEqualTo("https://simplesaml-for-spring-saml/SSOService.php");
	}

	@Test
	void customizeSsoUrl() {
		bind("spring.security.saml2.relyingparty.registration.simplesamlphp.identity-provider.single-sign-on.url",
				"https://simplesaml-for-spring-saml/SSOService.php");
		assertThat(
				this.properties.getRegistration().get("simplesamlphp").getIdentityprovider().getSinglesignon().getUrl())
						.isEqualTo("https://simplesaml-for-spring-saml/SSOService.php");
	}

	@Test
	void customizeSsoBindingDefaultsToRedirect() {
		this.properties.getRegistration().put("simplesamlphp", new Saml2RelyingPartyProperties.Registration());
		assertThat(this.properties.getRegistration().get("simplesamlphp").getIdentityprovider().getSinglesignon()
				.getBinding()).isEqualTo(Saml2MessageBinding.REDIRECT);
	}

	@Test
	void customizeSsoBinding() {
		bind("spring.security.saml2.relyingparty.registration.simplesamlphp.identity-provider.single-sign-on.binding",
				"post");
		assertThat(this.properties.getRegistration().get("simplesamlphp").getIdentityprovider().getSinglesignon()
				.getBinding()).isEqualTo(Saml2MessageBinding.POST);
	}

	@Test
	void customizeSsoSignRequests() {
		bind("spring.security.saml2.relyingparty.registration.simplesamlphp.identity-provider.single-sign-on.sign-request",
				"false");
		assertThat(this.properties.getRegistration().get("simplesamlphp").getIdentityprovider().getSinglesignon()
				.isSignRequest()).isEqualTo(false);
	}

	@Test
	void customizeSsoSignRequestsIsTrueByDefault() {
		this.properties.getRegistration().put("simplesamlphp", new Saml2RelyingPartyProperties.Registration());
		assertThat(this.properties.getRegistration().get("simplesamlphp").getIdentityprovider().getSinglesignon()
				.isSignRequest()).isEqualTo(true);
	}

	@Test
	void customizeRelyingPartyEntityId() {
		bind("spring.security.saml2.relyingparty.registration.simplesamlphp.entity-id",
				"{baseUrl}/saml2/custom-entity-id");
		assertThat(this.properties.getRegistration().get("simplesamlphp").getEntityId())
				.isEqualTo("{baseUrl}/saml2/custom-entity-id");
	}

	@Test
	void customizeRelyingPartyEntityIdDefaultsToServiceProviderMetadata() {
		assertThat(RelyingPartyRegistration.withRegistrationId("id")).extracting("entityId")
				.isEqualTo(new Saml2RelyingPartyProperties.Registration().getEntityId());
	}

	@Test
	void customizeIdentityProviderMetadataUri() {
		bind("spring.security.saml2.relyingparty.registration.simplesamlphp.identityprovider.metadata-uri",
				"https://idp.example.org/metadata");
		assertThat(this.properties.getRegistration().get("simplesamlphp").getIdentityprovider().getMetadataUri())
				.isEqualTo("https://idp.example.org/metadata");
	}

	private void bind(String name, String value) {
		bind(Collections.singletonMap(name, value));
	}

	private void bind(Map<String, String> map) {
		ConfigurationPropertySource source = new MapConfigurationPropertySource(map);
		new Binder(source).bind("spring.security.saml2.relyingparty", Bindable.ofInstance(this.properties));
	}

}
