package org.springframework.boot.build.bom;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.tasks.TaskAction;

import org.springframework.boot.build.bom.Library.Group;
import org.springframework.boot.build.bom.Library.Module;
import org.springframework.boot.build.bom.bomr.version.DependencyVersion;

/**
 * Checks the validity of a bom.
 *

 */
public class CheckBom extends DefaultTask {

	private final BomExtension bom;

	@Inject
	public CheckBom(BomExtension bom) {
		this.bom = bom;
	}

	@TaskAction
	void checkBom() {
		for (Library library : this.bom.getLibraries()) {
			for (Group group : library.getGroups()) {
				for (Module module : group.getModules()) {
					if (!module.getExclusions().isEmpty()) {
						checkExclusions(group.getId(), module, library.getVersion());
					}
				}
			}
		}
	}

	private void checkExclusions(String groupId, Module module, DependencyVersion version) {
		Set<String> resolved = getProject().getConfigurations()
				.detachedConfiguration(
						getProject().getDependencies().create(groupId + ":" + module.getName() + ":" + version))
				.getResolvedConfiguration().getResolvedArtifacts().stream()
				.map((artifact) -> artifact.getModuleVersion().getId())
				.map((id) -> id.getGroup() + ":" + id.getModule().getName()).collect(Collectors.toSet());
		Set<String> exclusions = module.getExclusions().stream()
				.map((exclusion) -> exclusion.getGroupId() + ":" + exclusion.getArtifactId())
				.collect(Collectors.toSet());
		Set<String> unused = new TreeSet<>();
		for (String exclusion : exclusions) {
			if (!resolved.contains(exclusion)) {
				if (exclusion.endsWith(":*")) {
					String group = exclusion.substring(0, exclusion.indexOf(':') + 1);
					if (resolved.stream().noneMatch((candidate) -> candidate.startsWith(group))) {
						unused.add(exclusion);
					}
				}
				else {
					unused.add(exclusion);
				}
			}
		}
		exclusions.removeAll(resolved);
		if (!unused.isEmpty()) {
			throw new InvalidUserDataException(
					"Unnecessary exclusions on " + groupId + ":" + module.getName() + ": " + exclusions);
		}
	}

}
