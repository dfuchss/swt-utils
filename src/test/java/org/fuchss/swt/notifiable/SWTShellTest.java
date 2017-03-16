package org.fuchss.swt.notifiable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.fuchss.swt.notifiable.SWTNotifiableShell;

public class SWTShellTest extends SWTNotifiableShell<String> {
    protected SWTNotifiableButton<String> btnTest;

    public SWTShellTest(Display display, int style) {
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

        this.btnTest = new SWTNotifiableButton<>(this, SWT.NONE);
        this.btnTest.setText("Test");
    }

    @Override
    public void inform(String info) {
        System.out.println(info);
    }

}
