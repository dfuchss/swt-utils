package org.fuchss.swt.simple;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.fuchss.swt.SWTShell;

/**
 * This class realizes a {@link Shell} which is able to run {@link Command
 * Commands} from outside the SWT-Thread.
 *
 * @author Dominik Fuchss
 * @see #queue(Command)
 *
 */
public abstract class SWTExecutorShell extends SWTShell {
    /**
     * The upcoming commands.
     */
    private final Queue<Command> commandQueue = new ArrayDeque<>();
    /**
     * The lock to synchronize {@link #commandQueue}.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Create a {@link Shell}.
     *
     * @param display
     *            the display
     * @param style
     *            the style
     * @see Shell#Shell(Display, int)
     */
    protected SWTExecutorShell(Display display, int style) {
        super(display, style);
    }

    /**
     * Create a {@link Shell}.
     *
     * @param shell
     *            the shell
     * @param style
     *            the style
     * @see Shell#Shell(Shell, int)
     */
    protected SWTExecutorShell(Shell shell, int style) {
        super(shell, style);
    }

    @Override
    public final void startEventLoop() {
        this.open();
        this.layout();
        Display display = this.getDisplay();
        while (!this.isDisposed()) {
            if (!display.readAndDispatch()) {
                this.executeBySWT();
                Thread.yield();
            }
        }
    }

    /**
     * Queue a new {@link Command} to execute in SWT-Thread.
     *
     * @param command
     *            the command
     */
    public final void queue(Command command) {
        this.lock.lock();
        if (command != null) {
            this.commandQueue.add(command);
        }
        this.lock.unlock();
    }

    /**
     * This will invoke all commands in {@link #commandQueue} (and clear that).
     */
    private final void executeBySWT() {
        this.lock.lock();
        this.commandQueue.forEach(c -> c.execute());
        this.commandQueue.clear();
        this.lock.unlock();
    }

    /**
     * This interface represents a Command which can be executed in the
     * SWT-Thread.
     *
     * @author Dominik Fuchss
     *
     */
    @FunctionalInterface
    public static interface Command {
        /**
         * Execute the command.
         */
        void execute();
    }

}
