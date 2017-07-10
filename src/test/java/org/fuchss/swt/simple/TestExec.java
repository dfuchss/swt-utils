package org.fuchss.swt.simple;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class TestExec {

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String args[]) {
        Display display = Display.getDefault();
        SWTShellExecTest shell = new SWTShellExecTest(display, SWT.SHELL_TRIM);
        new Thread() {
            @Override
            public void run() {
                TestExec.sleep();
                shell.queue(() -> shell.btnTest.setText("Hello"));
                TestExec.sleep();
                shell.queue(() -> shell.btnTest.setText("H"));

            }
        }.start();

        shell.startEventLoop();
    }

    static final void sleep() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
        }

    }
}
