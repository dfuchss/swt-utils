package org.fuchss.swt.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.fuchss.swt.callable.cmd.WorkerQueue;
import org.fuchss.swt.callable.cmd.WorkerQueueImpl;

public abstract class SWTCallableShell<Info> extends Shell implements SWTCallable<Info> {

	private WorkerQueue queue = new WorkerQueueImpl();

	private boolean isCalled = false;
	private Semaphore callMutex = new Semaphore(1);
	private List<Info> nextArgs = new ArrayList<>();
	private List<SWTCallable<Info>> nextCalledObjects = new ArrayList<>();
	private List<Info> currentArgs = null;
	private List<SWTCallable<Info>> currentCalledObjects = null;

	protected SWTCallableShell(Display display, int style) {
		super(display, style);
		this.addListener(SWT.Dispose, new DisposeController());
	}

	void decouple(Info arg, SWTCallable<Info> target) {
		try {
			this.callMutex.acquire();
		} catch (InterruptedException e) {
			return;
		}
		this.isCalled = true;
		this.nextArgs.add(arg);
		this.nextCalledObjects.add(target);
		this.callMutex.release();
	}

	public void startEventLoop() {
		this.open();
		this.layout();
		Display display = this.getDisplay();
		while (!this.isDisposed()) {
			if (!display.readAndDispatch()) {
				this.forwardCall();
				Thread.yield();
			}
		}
	}

	private void forwardCall() {
		if (this.checkCall()) {
			for (SWTCallable<Info> comp : this.currentCalledObjects) {
				comp.receiveInfo(this.currentArgs.remove(0));
			}
		}
	}

	private boolean checkCall() {
		try {
			this.callMutex.acquire();
			boolean result;
			if (result = this.isCalled) {
				this.moveNext2Current();
				this.isCalled = false;
			}
			this.callMutex.release();
			return result;
		} catch (InterruptedException e) {
			return false;
		}
	}

	private void moveNext2Current() {
		this.currentArgs = this.nextArgs;
		this.currentCalledObjects = this.nextCalledObjects;
		this.nextArgs = new ArrayList<>();
		this.nextCalledObjects = new ArrayList<>();

	}

	@Override
	protected void finalize() {
		this.queue.quit();
	}

	WorkerQueue getQueue() {
		return this.queue;
	}

	protected void sendInfo(Info info) {
		this.decouple(info, this);
	}

	private class DisposeController implements Listener {

		@Override
		public void handleEvent(Event event) {
			if (event.widget == SWTCallableShell.this) {
				SWTCallableShell.this.queue.quit();
			}
		}

	}

	@Override
	protected final void checkSubclass() {
	}
}
