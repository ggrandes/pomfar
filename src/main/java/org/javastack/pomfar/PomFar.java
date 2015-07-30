/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.javastack.pomfar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PomFar {
	/**
	 * Enable Quiet Mode (default: false)
	 */
	public static final String PROP_QUIET = "pomfar.quiet";
	/**
	 * Get pom.xml? (default: false)
	 */
	public static final String PROP_XML = "pomfar.get.xml";
	/**
	 * Get pom.properties? (default: true)
	 */
	public static final String PROP_PROPERTIES = "pomfar.get.properties";

	private static final boolean quiet = Boolean.valueOf(System.getProperty(PROP_QUIET, "false"));
	private static final boolean getXml = Boolean.valueOf(System.getProperty(PROP_XML, "false"));
	private static final boolean getProperties = Boolean.valueOf(System.getProperty(PROP_PROPERTIES, "true"));

	private static boolean isPom(final String name) {
		return ((getProperties && name.endsWith("pom.properties")) || (getXml && name.endsWith("pom.xml")));
	}

	public static void main(final String[] args) throws Throwable {
		for (final String file : args) {
			ZipInputStream is = null;
			try {
				is = new ZipInputStream(new FileInputStream(file));
				if (!quiet) {
					System.out.println("### Scanning: " + file);
				}
				ZipEntry e = null;
				while ((e = is.getNextEntry()) != null) {
					if (!e.isDirectory() && isPom(e.getName())) {
						if (!quiet) {
							System.out.println("### Found: " + e.getName());
						}
						final BufferedReader in = new BufferedReader(new InputStreamReader(is));
						String line = null;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
					}
					is.closeEntry();
				}
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
	}
}
