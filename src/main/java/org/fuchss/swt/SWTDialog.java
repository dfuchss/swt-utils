package org.fuchss.swt;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Extends to classical {@link Dialog} of SWT.
 *
 * @author Dominik Fuchss
 *
 */
public abstract class SWTDialog extends Dialog {

	private Shell shell;

	/**
	 * Create a dialog by parent and style.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	protected SWTDialog(Shell parent, int style) {
		super(parent, style);
	}

	public final void open() {
		this.shell = new Shell(this.getParent(), this.getStyle());
		this.createContents();
		this.shell.open();
		this.shell.layout();
		Display display = this.getParent().getDisplay();
		while (!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				Thread.yield();
			}
		}
		this.cleanup();
	}

	/**
	 * Create contents of the dialog.
	 */
	protected abstract void createContents();

	/**
	 * Cleanup some stuff. Will be invoked at the end of {@link #open()}.
	 */
	protected void cleanup() {
	}

	/**
	 * Set position to center.
	 */
	public final void centerMe() {
		Shell parent = this.getParent();
		int newLeftPos = (parent.getBounds().width - this.shell.getSize().x) / 2;
		int newTopPos = (parent.getBounds().height - this.shell.getSize().y) / 2;
		var relative = this.getParent().getLocation();
		this.shell.setLocation(relative.x + newLeftPos, relative.y + newTopPos);
	}

	@Override
	protected final void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
