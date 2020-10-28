package org.springframework.boot.actuate.info;

import java.time.Instant;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.info.GitProperties;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * An {@link InfoContributor} that exposes {@link GitProperties}.
 *

 * @since 1.4.0
 */
public class GitInfoContributor extends InfoPropertiesInfoContributor<GitProperties> {

	public GitInfoContributor(GitProperties properties) {
		this(properties, Mode.SIMPLE);
	}

	public GitInfoContributor(GitProperties properties, Mode mode) {
		super(properties, mode);
	}

	@Override
	public void contribute(Info.Builder builder) {
		builder.withDetail("git", generateContent());
	}

	@Override
	protected PropertySource<?> toSimplePropertySource() {
		Properties props = new Properties();
		copyIfSet(props, "branch");
		String commitId = getProperties().getShortCommitId();
		if (commitId != null) {
			props.put("commit.id", commitId);
		}
		copyIfSet(props, "commit.time");
		return new PropertiesPropertySource("git", props);
	}

	/**
	 * Post-process the content to expose. By default, well known keys representing dates
	 * are converted to {@link Instant} instances.
	 * @param content the content to expose
	 */
	@Override
	protected void postProcessContent(Map<String, Object> content) {
		replaceValue(getNestedMap(content, "commit"), "time", getProperties().getCommitTime());
		replaceValue(getNestedMap(content, "build"), "time", getProperties().getInstant("build.time"));
	}

}
