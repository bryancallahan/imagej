/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2014 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package imagej.updater.core;

import static imagej.updater.core.UpdaterTestUtils.assertCount;
import static imagej.updater.core.UpdaterTestUtils.addUpdateSite;
import static imagej.updater.core.UpdaterTestUtils.cleanup;
import static imagej.updater.core.UpdaterTestUtils.initialize;
import static imagej.updater.core.UpdaterTestUtils.main;
import static imagej.updater.core.UpdaterTestUtils.writeFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import imagej.updater.util.StderrProgress;

import org.junit.After;
import org.junit.Test;

/**
 * Verifies that multiple update sites are handled correctly.
 * 
 * @author Johannes Schindelin
 */
public class MultipleSitesTest {
	protected FilesCollection files;
	protected StderrProgress progress = new StderrProgress();

	@After
	public void after() {
		if (files != null) cleanup(files);
	}

	@Test
	public void testThreeSites() throws Exception {
		final String macro = "macros/macro.ijm";
		files = initialize(macro);

		final String check1 = files.get(macro).getChecksum();

		File ijRoot = files.prefix("");
		writeFile(new File(ijRoot, macro), "Egads!");
		addUpdateSite(files, "second");
		files = main(files, "upload", "--force-shadow", "--update-site", "second", macro);
		final String check2 = files.get(macro).getChecksum();

		writeFile(new File(ijRoot, macro), "Narf!");
		addUpdateSite(files, "third");
		files = main(files, "upload", "--force-shadow", "--update-site", "third", macro);
		final String check3 = files.get(macro).getChecksum();

		assertCount(1, files);
		assertEquals(check3, files.get(macro).getChecksum());

		files.removeUpdateSite("third");
		assertCount(1, files);
		assertEquals(check2, files.get(macro).getChecksum());

		files.removeUpdateSite("second");
		assertCount(1, files);
		assertEquals(check1, files.get(macro).getChecksum());

		files.removeUpdateSite(FilesCollection.DEFAULT_UPDATE_SITE);
		assertCount(0, files);
		assertNull(files.get(macro));
	}

	//@Test
	public void testThreeSites2() throws Exception {
		final String installed = "macros/installed.ijm";
		final String shadowed = "macros/shadowed.ijm";
		files = initialize(shadowed, installed);

		File ijRoot = files.prefix("");
		writeFile(new File(ijRoot, shadowed), "Narf!");
		final String onlyOnSecond = "macros/only-on-second.ijm";
		writeFile(new File(ijRoot, onlyOnSecond), "Egads!");
		addUpdateSite(files, "second");
		files = main(files, "upload", "--force-shadow", "--update-site", "second", shadowed, onlyOnSecond);

		assertCount(3, files);
		assertCount(3, files.installed());
		assertCount(2, files.forUpdateSite("second"));

		files.removeUpdateSite("second");
		assertCount(2, files.updateable(false));
	}
}
