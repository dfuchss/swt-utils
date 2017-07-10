package org.fuchss.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class MultilineButton extends Button {

	public MultilineButton(Composite parent, int style) {
		super(parent, style | SWT.WRAP | SWT.PUSH);
	}

	@Override
	protected final void checkSubclass() {
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point size = super.computeSize(wHint, hHint, changed);
		GC gc = new GC(this);

		String text = this.getText();
		Point mlSize = gc.textExtent(text, SWT.DRAW_DELIMITER);
		Point simpleSize = gc.textExtent(text.replace('\n', ' '));

		gc.dispose();

		size.x -= simpleSize.x - mlSize.x;
		size.y += mlSize.y - simpleSize.y;

		return size;
	}
}