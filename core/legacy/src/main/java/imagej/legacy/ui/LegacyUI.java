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

package imagej.legacy.ui;

import ij.IJ;
import ij.ImageJ;
import imagej.display.Display;
import imagej.legacy.DefaultLegacyService;
import imagej.legacy.IJ1Helper;
import imagej.legacy.LegacyService;
import imagej.ui.ApplicationFrame;
import imagej.ui.Desktop;
import imagej.ui.DialogPrompt;
import imagej.ui.DialogPrompt.MessageType;
import imagej.ui.DialogPrompt.OptionType;
import imagej.ui.StatusBar;
import imagej.ui.SystemClipboard;
import imagej.ui.ToolBar;
import imagej.ui.UserInterface;
import imagej.ui.common.awt.AWTClipboard;
import imagej.ui.viewer.DisplayWindow;

import java.beans.PropertyChangeListener;
import java.io.File;

import org.scijava.plugin.AbstractRichPlugin;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = UserInterface.class)
public class LegacyUI extends AbstractRichPlugin implements UserInterface {

	@Parameter
	private LegacyService legacyService;

	private IJ1Helper ij1Helper;
	private Desktop desktop;
	private ApplicationFrame applicationFrame;
	private ToolBar toolBar;

	private StatusBar statusBar;

	private SystemClipboard systemClipboard;

	private IJ1Helper ij1Helper() {
		if (legacyService instanceof DefaultLegacyService) {
			return ((DefaultLegacyService) legacyService).getIJ1Helper();
		}
		return null;
	}

	@Override
	public void dispose() {
		// do nothing
	}

	@Override
	public void show() {
		if (ij1Helper() != null) ij1Helper.setVisible(true);
	}

	@Override
	public boolean isVisible() {
		return ij1Helper() != null && ij1Helper.isVisible();
	}

	@Override
	public void show(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show(String name, Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show(Display<?> display) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized Desktop getDesktop() {
		if (desktop != null) return desktop;
		desktop = new Desktop() {

			@Override
			public void setArrangement(Arrangement newValue) {
			}

			@Override
			public Arrangement getArrangement() {
				return null;
			}

			@Override
			public void addPropertyChangeListener(PropertyChangeListener l) {
			}

			@Override
			public void removePropertyChangeListener(PropertyChangeListener l) {
			}
			
		};
		return desktop;
	}

	@Override
	public synchronized ApplicationFrame getApplicationFrame() {
		if (applicationFrame != null) return applicationFrame;
		if (ij1Helper() == null) return null;
		applicationFrame = new ApplicationFrame() {
			
			@Override
			public void setVisible(boolean visible) {
				final ImageJ ij = IJ.getInstance();
				if (ij != null) {
					ij.setVisible(visible);
				}
			}
			
			@Override
			public void setLocation(int x, int y) {
				final ImageJ ij = IJ.getInstance();
				if (ij != null) {
					ij.setLocation(x, y);
				}
			}

			@Override
			public int getLocationX() {
				final ImageJ ij = IJ.getInstance();
				if (ij == null) return 0;
				return ij.getX();
			}

			@Override
			public int getLocationY() {
				final ImageJ ij = IJ.getInstance();
				if (ij == null) return 0;
				return ij.getY();
			}

			@Override
			public void activate() {
				setVisible(true);
			}

		};
		return applicationFrame;
	}

	@Override
	public synchronized ToolBar getToolBar() {
		if (toolBar != null) return toolBar;
		toolBar = new ToolBar() {
		};
		return toolBar;
	}

	@Override
	public synchronized StatusBar getStatusBar() {
		if (statusBar != null) return statusBar;
		if (ij1Helper() == null) return null;
		statusBar = new StatusBar() {
			
			@Override
			public void setStatus(String message) {
				ij1Helper.setStatus(message);
			}
			
			@Override
			public void setProgress(int val, int max) {
				ij1Helper.setProgress(val, max);
			}
		};
		return statusBar;
	}

	@Override
	public synchronized SystemClipboard getSystemClipboard() {
		if (systemClipboard != null) return systemClipboard;
		systemClipboard = new AWTClipboard();
		return systemClipboard;
	}

	@Override
	public DisplayWindow createDisplayWindow(Display<?> display) {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public DialogPrompt dialogPrompt(String message, String title,
			MessageType messageType, OptionType optionType) {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public File chooseFile(File file, String style) {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public void showContextMenu(String menuRoot, Display<?> display, int x,
			int y) {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public void saveLocation() {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public void restoreLocation() {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public boolean requiresEDT() {
		return true;
	}
}
