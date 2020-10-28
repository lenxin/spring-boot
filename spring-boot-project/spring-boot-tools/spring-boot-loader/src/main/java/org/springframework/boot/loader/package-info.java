/**
 * System that allows self-contained JAR/WAR archives to be launched using
 * {@code java -jar}. Archives can include nested packaged dependency JARs (there is no
 * need to create shade style jars) and are executed without unpacking. The only
 * constraint is that nested JARs must be stored in the archive uncompressed.
 *
 * @see org.springframework.boot.loader.JarLauncher
 * @see org.springframework.boot.loader.WarLauncher
 */
package org.springframework.boot.loader;
