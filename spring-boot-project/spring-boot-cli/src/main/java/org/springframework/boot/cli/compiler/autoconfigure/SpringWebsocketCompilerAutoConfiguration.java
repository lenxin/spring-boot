package org.springframework.boot.cli.compiler.autoconfigure;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import org.springframework.boot.cli.compiler.AstUtils;
import org.springframework.boot.cli.compiler.CompilerAutoConfiguration;
import org.springframework.boot.cli.compiler.DependencyCustomizer;

/**
 * {@link CompilerAutoConfiguration} for Spring Websocket.
 *

 * @since 1.0.0
 */
public class SpringWebsocketCompilerAutoConfiguration extends CompilerAutoConfiguration {

	@Override
	public boolean matches(ClassNode classNode) {
		return AstUtils.hasAtLeastOneAnnotation(classNode, "EnableWebSocket", "EnableWebSocketMessageBroker");
	}

	@Override
	public void applyDependencies(DependencyCustomizer dependencies) {
		dependencies.ifAnyMissingClasses("org.springframework.web.socket.config.annotation.EnableWebSocket")
				.add("spring-boot-starter-websocket").add("spring-messaging");
	}

	@Override
	public void applyImports(ImportCustomizer imports) {
		imports.addStarImports("org.springframework.messaging.handler.annotation",
				"org.springframework.messaging.simp.config", "org.springframework.web.socket.handler",
				"org.springframework.web.socket.sockjs.transport.handler",
				"org.springframework.web.socket.config.annotation")
				.addImports("org.springframework.web.socket.WebSocketHandler");
	}

}
