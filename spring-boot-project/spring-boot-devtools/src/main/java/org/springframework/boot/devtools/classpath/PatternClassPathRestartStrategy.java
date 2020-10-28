package org.springframework.boot.devtools.classpath;

import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

/**
 * Ant style pattern based {@link ClassPathRestartStrategy}.
 *

 * @since 1.3.0
 * @see ClassPathRestartStrategy
 */
public class PatternClassPathRestartStrategy implements ClassPathRestartStrategy {

	private final AntPathMatcher matcher = new AntPathMatcher();

	private final String[] excludePatterns;

	public PatternClassPathRestartStrategy(String[] excludePatterns) {
		this.excludePatterns = excludePatterns;
	}

	public PatternClassPathRestartStrategy(String excludePatterns) {
		this(StringUtils.commaDelimitedListToStringArray(excludePatterns));
	}

	@Override
	public boolean isRestartRequired(ChangedFile file) {
		for (String pattern : this.excludePatterns) {
			if (this.matcher.match(pattern, file.getRelativeName())) {
				return false;
			}
		}
		return true;
	}

}
