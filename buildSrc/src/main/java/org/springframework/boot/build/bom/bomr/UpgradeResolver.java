package org.springframework.boot.build.bom.bomr;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.build.bom.Library;

/**
 * Resolves upgrades for the libraries in a bom.
 *

 */
interface UpgradeResolver {

	/**
	 * Resolves the upgrades to be applied to the given {@code libraries}.
	 * @param libraries the libraries
	 * @return the upgrades
	 */
	List<Upgrade> resolveUpgrades(Collection<Library> libraries);

}
