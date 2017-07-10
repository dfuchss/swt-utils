package org.fuchss.swt.simple;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.fuchss.swt.simple.SWTExecutorShell;

public class SWTShellExecTest extends SWTExecutorShell {
    protected Button btnTest;

    public SWTShellExecTest(Display display, int style) {
        super(display, style);
    }

    /**
     * Create contents of the shell.
     */
    @Override
    protected void createContents() {
        this.setText("SWT Application");
        this.setSize(450, 300);
        this.setLayout(new GridLayout(1, false));

        this.btnTest = new Button(this, SWT.NONE);
        this.btnTest.setText("Test");
    }

}
