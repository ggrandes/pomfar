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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PomFar {
	/**
	 * Enable Quiet Mode (default: false)
	 */
	public static final String PROP_QUIET = "pomfar.quiet";
	/**
	 * What file want? (default: pom.properties)
	 */
	public static final String PROP_FILE = "pomfar.file";

	private static final boolean quiet = Boolean.valueOf(System.getProperty(PROP_QUIET, "false"));
	private static final String theFile = System.getProperty(PROP_FILE, "pom.properties");
	private static final String theFileA = '/' + theFile;
	private static final String theFileB = '\\' + theFile;

	private static boolean isTheFile(final String name) {
		return name.endsWith(theFileA) || name.endsWith(theFileB);
	}

	public static void main(final String[] args) throws Throwable {
		for (final String file : args) {
			if (!quiet) {
				System.out.println("### Scanning: " + file);
			}
			ZipFile zf = null;
			try {
				zf = new ZipFile(file);
				final Enumeration<? extends ZipEntry> ze = zf.entries();
				while (ze.hasMoreElements()) {
					final ZipEntry e = ze.nextElement();
					if (!e.isDirectory() && isTheFile(e.getName())) {
						if (!quiet) {
							System.out.println("### Found: " + e.getName());
						}
						final InputStream is = zf.getInputStream(e);
						final BufferedReader in = new BufferedReader(new InputStreamReader(is));
						String line = null;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
						is.close();
					}
				}
			} finally {
				if (zf != null) {
					zf.close();
				}
			}
		}
	}
}
