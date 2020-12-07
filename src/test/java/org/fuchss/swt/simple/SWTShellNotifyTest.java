package org.fuchss.swt.simple;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;

public class SWTShellNotifyTest extends SWTNotifiableShell<String> {
	protected SWTNotifiableButton<String> btnTest;

	public SWTShellNotifyTest(Display display, int style) {
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
