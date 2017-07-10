package org.fuchss.swt.simple;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class TestNotify {

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String args[]) {
        Display display = Display.getDefault();
        SWTShellNotifyTest shell = new SWTShellNotifyTest(display, SWT.SHELL_TRIM);
        new Thread() {
            @Override
            public void run() {
                TestNotify.sleep();
                shell.inform("Hello", shell.btnTest);
                TestNotify.sleep();
                shell.informAll("H");

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
