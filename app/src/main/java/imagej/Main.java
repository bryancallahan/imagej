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

package imagej;

import imagej.data.Dataset;
import imagej.data.display.DataView;
import imagej.data.display.ImageDisplay;
import imagej.data.display.OverlayView;
import imagej.data.overlay.EllipseOverlay;
import imagej.data.overlay.RectangleOverlay;
import imagej.plugins.commands.assign.FillDataValues;

import java.util.Collections;

/**
 * Launches ImageJ.
 * 
 * @author Curtis Rueden
 */
public final class Main {

	private Main() {
		// prevent instantiation of utility class
	}

	/**
	 * Launches a new instance of ImageJ, displaying the default user interface.
	 * <p>
	 * This method is provided merely for convenience. If you do not want to
	 * display a user interface, construct the ImageJ instance directly instead:
	 * </p>
	 * {@code final ImageJ ij = new ImageJ();<br/>
	 * ij.console().processArgs(args); // if you want to pass any arguments
	 * }
	 * 
	 * @param args The arguments to pass to the new ImageJ instance.
	 * @return The newly launched ImageJ instance.
	 */
	public static ImageJ launch(final String... args) {
		final ImageJ ij = new ImageJ();

		// parse command line arguments
		ij.console().processArgs(args);

		// display the user interface
		ij.ui().showUI();

		return ij;
	}

	public static void main(final String... args) throws Exception {
		final ImageJ ij = launch(args);

		// load the dataset
		final Dataset dataset = ij.dataset().open("/Users/curtis/data/toucan.png");
		
		// create a display
		final ImageDisplay imageDisplay =
			(ImageDisplay) ij.display().createDisplay(dataset);
		ij.display().setActiveDisplay(imageDisplay);

		// add an overlay
		final EllipseOverlay overlay = new EllipseOverlay(ij.getContext());
		overlay.setOrigin(53, 0);
		overlay.setOrigin(75, 1);
		overlay.setRadius(52, 0);
		overlay.setRadius(35, 1);
		ij.log().info("image display = " + imageDisplay);
		ij.overlay().addOverlays(imageDisplay, Collections.singletonList(overlay));

		// select the overlay...
		for (final DataView view : imageDisplay) {
			if (view instanceof OverlayView) {
				view.setSelected(true);
				break;
			}
		}

		ij.command().run(FillDataValues.class, true);
	}

}
