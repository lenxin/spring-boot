package org.springframework.boot.cli.compiler.grape;

import java.io.File;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.internal.impl.SimpleLocalRepositoryManagerFactory;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link GrapeRootRepositorySystemSessionAutoConfiguration}
 *

 */
@ExtendWith(MockitoExtension.class)
class GrapeRootRepositorySystemSessionAutoConfigurationTests {

	private DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

	@Mock
	private RepositorySystem repositorySystem;

	@Test
	void noLocalRepositoryWhenNoGrapeRoot() {
		new GrapeRootRepositorySystemSessionAutoConfiguration().apply(this.session, this.repositorySystem);
		verify(this.repositorySystem, never()).newLocalRepositoryManager(eq(this.session), any(LocalRepository.class));
		assertThat(this.session.getLocalRepository()).isNull();
	}

	@Test
	void grapeRootConfiguresLocalRepositoryLocation() {
		given(this.repositorySystem.newLocalRepositoryManager(eq(this.session), any(LocalRepository.class)))
				.willAnswer(new LocalRepositoryManagerAnswer());

		System.setProperty("grape.root", "foo");
		try {
			new GrapeRootRepositorySystemSessionAutoConfiguration().apply(this.session, this.repositorySystem);
		}
		finally {
			System.clearProperty("grape.root");
		}

		verify(this.repositorySystem, times(1)).newLocalRepositoryManager(eq(this.session), any(LocalRepository.class));

		assertThat(this.session.getLocalRepository()).isNotNull();
		assertThat(this.session.getLocalRepository().getBasedir().getAbsolutePath())
				.endsWith(File.separatorChar + "foo" + File.separatorChar + "repository");
	}

	private class LocalRepositoryManagerAnswer implements Answer<LocalRepositoryManager> {

		@Override
		public LocalRepositoryManager answer(InvocationOnMock invocation) throws Throwable {
			LocalRepository localRepository = invocation.getArgument(1);
			return new SimpleLocalRepositoryManagerFactory()
					.newInstance(GrapeRootRepositorySystemSessionAutoConfigurationTests.this.session, localRepository);
		}

	}

}
