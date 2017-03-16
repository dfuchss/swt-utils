package org.fuchss.swt.callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class Test {

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String args[]) {
        Display display = Display.getDefault();
        SWTShellTest shell = new SWTShellTest(display, SWT.SHELL_TRIM);
        new Thread() {
            @Override
            public void run() {
                Test.sleep();
                shell.queue(() -> shell.btnTest.setText("Hello"));
                Test.sleep();
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
