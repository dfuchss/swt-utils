package org.fuchss.swt.notifiable;

import org.eclipse.swt.widgets.Button;
import org.fuchss.swt.notifiable.SWTNotifiable;
import org.fuchss.swt.notifiable.SWTNotifiableShell;

public class SWTNotifiableButton<INFO> extends Button implements SWTNotifiable<INFO> {

	public SWTNotifiableButton(SWTNotifiableShell<INFO> parent, int style) {
		super(parent, style);
	}

	@Override
	public void inform(INFO info) {
		if (info.getClass() == String.class)
			this.setText((String) info);

	}

	@Override
	protected final void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
