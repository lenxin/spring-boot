package org.springframework.boot.env;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.log.LogMessage;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * {@link PropertySource} that returns a random value for any property that starts with
 * {@literal "random."}. Where the "unqualified property name" is the portion of the
 * requested property name beyond the "random." prefix, this {@link PropertySource}
 * returns:
 * <ul>
 * <li>When {@literal "int"}, a random {@link Integer} value, restricted by an optionally
 * specified range.</li>
 * <li>When {@literal "long"}, a random {@link Long} value, restricted by an optionally
 * specified range.</li>
 * <li>Otherwise, a {@code byte[]}.</li>
 * </ul>
 * The {@literal "random.int"} and {@literal "random.long"} properties supports a range
 * suffix whose syntax is:
 * <p>
 * {@code OPEN value (,max) CLOSE} where the {@code OPEN,CLOSE} are any character and
 * {@code value,max} are integers. If {@code max} is provided then {@code value} is the
 * minimum value and {@code max} is the maximum (exclusive).
 *


 * @since 1.0.0
 */
public class RandomValuePropertySource extends PropertySource<Random> {

	/**
	 * Name of the random {@link PropertySource}.
	 */
	public static final String RANDOM_PROPERTY_SOURCE_NAME = "random";

	private static final String PREFIX = "random.";

	private static final Log logger = LogFactory.getLog(RandomValuePropertySource.class);

	public RandomValuePropertySource() {
		this(RANDOM_PROPERTY_SOURCE_NAME);
	}

	public RandomValuePropertySource(String name) {
		super(name, new Random());
	}

	@Override
	public Object getProperty(String name) {
		if (!name.startsWith(PREFIX)) {
			return null;
		}
		logger.trace(LogMessage.format("Generating random property for '%s'", name));
		return getRandomValue(name.substring(PREFIX.length()));
	}

	private Object getRandomValue(String type) {
		if (type.equals("int")) {
			return getSource().nextInt();
		}
		if (type.equals("long")) {
			return getSource().nextLong();
		}
		String range = getRange(type, "int");
		if (range != null) {
			return getNextIntInRange(range);
		}
		range = getRange(type, "long");
		if (range != null) {
			return getNextLongInRange(range);
		}
		if (type.equals("uuid")) {
			return UUID.randomUUID().toString();
		}
		return getRandomBytes();
	}

	private String getRange(String type, String prefix) {
		if (type.startsWith(prefix)) {
			int startIndex = prefix.length() + 1;
			if (type.length() > startIndex) {
				return type.substring(startIndex, type.length() - 1);
			}
		}
		return null;
	}

	private int getNextIntInRange(String range) {
		String[] tokens = StringUtils.commaDelimitedListToStringArray(range);
		int start = Integer.parseInt(tokens[0]);
		if (tokens.length == 1) {
			return getSource().nextInt(start);
		}
		return start + getSource().nextInt(Integer.parseInt(tokens[1]) - start);
	}

	private long getNextLongInRange(String range) {
		String[] tokens = StringUtils.commaDelimitedListToStringArray(range);
		if (tokens.length == 1) {
			return Math.abs(getSource().nextLong() % Long.parseLong(tokens[0]));
		}
		long lowerBound = Long.parseLong(tokens[0]);
		long upperBound = Long.parseLong(tokens[1]) - lowerBound;
		return lowerBound + Math.abs(getSource().nextLong() % upperBound);
	}

	private Object getRandomBytes() {
		byte[] bytes = new byte[32];
		getSource().nextBytes(bytes);
		return DigestUtils.md5DigestAsHex(bytes);
	}

	public static void addToEnvironment(ConfigurableEnvironment environment) {
		addToEnvironment(environment, logger);
	}

	static void addToEnvironment(ConfigurableEnvironment environment, Log logger) {
		MutablePropertySources sources = environment.getPropertySources();
		PropertySource<?> existing = sources.get(RANDOM_PROPERTY_SOURCE_NAME);
		if (existing != null) {
			logger.trace("RandomValuePropertySource already present");
			return;
		}
		RandomValuePropertySource randomSource = new RandomValuePropertySource(RANDOM_PROPERTY_SOURCE_NAME);
		if (sources.get(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME) != null) {
			sources.addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, randomSource);
		}
		else {
			sources.addLast(randomSource);
		}
		logger.trace("RandomValuePropertySource add to Environment");
	}

}
