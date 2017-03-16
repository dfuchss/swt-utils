package org.fuchss.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * Extends to classical {@link Shell} of SWT.
 *
 * @author Dominik Fuchss
 *
 */
public abstract class SWTShell extends Shell {
    /**
     * Create a {@link SWTShell}.
     *
     * @param display
     *            the display
     * @param style
     *            the style
     * @see Shell#Shell(Display, int)
     */
    protected SWTShell(Display display, int style) {
        super(display, style);
        this.createContents();
    }

    /**
     * Create a {@link SWTShell}.
     *
     * @param shell
     *            the shell
     * @param style
     *            the style
     * @see Shell#Shell(Shell, int)
     */
    protected SWTShell(Shell shell, int style) {
        super(shell, style);
    }

    /**
     * Will invoked once to create contents of the shell.
     */
    protected abstract void createContents();

    /**
     * Open Shell and update it periodically.
     */
    public void startEventLoop() {
        this.open();
        this.layout();
        Display display = this.getDisplay();
        while (!this.isDisposed()) {
            if (!display.readAndDispatch()) {
                Thread.yield();
            }
        }
    }

    /**
     * Set position to center.
     */
    public final void centerMe() {
        Monitor mon = Display.getDefault().getMonitors()[0];
        int newLeftPos = (mon.getBounds().width - this.getSize().x) / 2;
        int newTopPos = (mon.getBounds().height - this.getSize().y) / 2;
        this.setLocation(newLeftPos, newTopPos);

    }

    @Override
    protected final void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
