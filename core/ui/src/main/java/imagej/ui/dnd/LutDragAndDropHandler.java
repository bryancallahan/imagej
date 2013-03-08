package imagej.ui.dnd;

import imagej.display.Display;

import org.scijava.plugin.Plugin;

@Plugin(type = DragAndDropHandler.class)
public class LutDragAndDropHandler extends AbstractDragAndDropHandler {

	public static final String MIME_TYPE =
		"application/imagej-lut; class=java.util.String; charset=Unicode";

	@Override
	public boolean isCompatible(Display<?> display, DragAndDropData data) {
		for (final String mimeType : data.getMimeTypes()) {
			if (MIME_TYPE.equals(mimeType)) return true;
		}
		return false;
	}

	@Override
	public boolean drop(Display<?> display, DragAndDropData data) {
		System.out.println("NOW READ LUT" + data.getData(MIME_TYPE));
		return false;
	}

}
