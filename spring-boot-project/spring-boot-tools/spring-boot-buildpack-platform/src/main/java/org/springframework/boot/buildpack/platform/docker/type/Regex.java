package org.springframework.boot.buildpack.platform.docker.type;

import java.util.regex.Pattern;

/**
 * Regular Expressions for image names and references based on those found in the Docker
 * codebase.
 *


 * @see <a href=
 * "https://github.com/docker/distribution/blob/master/reference/reference.go">Docker
 * grammar reference</a>
 * @see <a href=
 * "https://github.com/docker/distribution/blob/master/reference/regexp.go">Docker grammar
 * implementation</a>
 * @see <a href=
 * "https://stackoverflow.com/questions/37861791/how-are-docker-image-names-parsed">How
 * are Docker image names parsed?</a>
 */
final class Regex implements CharSequence {

	private static final Regex DOMAIN;
	static {
		Regex component = Regex.oneOf("[a-zA-Z0-9]", "[a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]");
		Regex dotComponent = Regex.group("[.]", component);
		Regex colonPort = Regex.of("[:][0-9]+");
		Regex dottedDomain = Regex.group(component, dotComponent.oneOrMoreTimes());
		Regex dottedDomainAndPort = Regex.group(component, dotComponent.oneOrMoreTimes(), colonPort);
		Regex nameAndPort = Regex.group(component, colonPort);
		DOMAIN = Regex.oneOf(dottedDomain, nameAndPort, dottedDomainAndPort, "localhost");
	}

	private static final Regex PATH_COMPONENT;
	static {
		Regex segment = Regex.of("[a-z0-9]+");
		Regex separator = Regex.group("[._]|__|[-]*");
		Regex separatedSegment = Regex.group(separator, segment).oneOrMoreTimes();
		PATH_COMPONENT = Regex.of(segment, Regex.group(separatedSegment).zeroOrOnce());
	}

	private static final Regex PATH;
	static {
		Regex component = PATH_COMPONENT;
		Regex slashComponent = Regex.group("[/]", component);
		Regex slashComponents = Regex.group(slashComponent.oneOrMoreTimes());
		PATH = Regex.of(component, slashComponents.zeroOrOnce());
	}

	static final Regex IMAGE_NAME;
	static {
		Regex domain = DOMAIN.capturedAs("domain");
		Regex domainSlash = Regex.group(domain, "[/]");
		Regex path = PATH.capturedAs("path");
		Regex optionalDomainSlash = domainSlash.zeroOrOnce();
		IMAGE_NAME = Regex.of(optionalDomainSlash, path);
	}

	private static final Regex TAG_REGEX = Regex.of("[\\w][\\w.-]{0,127}");

	private static final Regex DIGEST_REGEX = Regex
			.of("[A-Za-z][A-Za-z0-9]*(?:[-_+.][A-Za-z][A-Za-z0-9]*)*[:][[A-Fa-f0-9]]{32,}");

	static final Regex IMAGE_REFERENCE;
	static {
		Regex tag = TAG_REGEX.capturedAs("tag");
		Regex digest = DIGEST_REGEX.capturedAs("digest");
		Regex atDigest = Regex.group("[@]", digest);
		Regex colonTag = Regex.group("[:]", tag);
		IMAGE_REFERENCE = Regex.of(IMAGE_NAME, colonTag.zeroOrOnce(), atDigest.zeroOrOnce());
	}

	private final String value;

	private Regex(CharSequence value) {
		this.value = value.toString();
	}

	private Regex oneOrMoreTimes() {
		return new Regex(this.value + "+");
	}

	private Regex zeroOrOnce() {
		return new Regex(this.value + "?");
	}

	private Regex capturedAs(String name) {
		return new Regex("(?<" + name + ">" + this + ")");
	}

	Pattern compile() {
		return Pattern.compile("^" + this.value + "$");
	}

	@Override
	public int length() {
		return this.value.length();
	}

	@Override
	public char charAt(int index) {
		return this.value.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.value.subSequence(start, end);
	}

	@Override
	public String toString() {
		return this.value;
	}

	private static Regex of(CharSequence... expressions) {
		return new Regex(String.join("", expressions));
	}

	private static Regex oneOf(CharSequence... expressions) {
		return new Regex("(?:" + String.join("|", expressions) + ")");
	}

	private static Regex group(CharSequence... expressions) {
		return new Regex("(?:" + String.join("", expressions) + ")");
	}

}
