package home.fox.swt.callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;

public class SWTShellTest extends SWTCallableShell {
    protected Button btnTest;

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

        this.btnTest = new Button(this, SWT.NONE);
        this.btnTest.setText("Test");
    }

}
