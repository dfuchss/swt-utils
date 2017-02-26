package home.fox.swt.notifiable;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import home.fox.swt.SWTShell;

/**
 * This class realizes a {@link Shell} which is able to receive data from
 * outside the SWT-Thread. It can also manage a set of {@link SWTNotifiable} to
 * inform them too.
 *
 * @author Dominik Fuchss
 *
 * @param <INFO>
 *            the type of data to receive
 * @see #addObserver(SWTNotifiable)
 * @see #inform(Object, SWTNotifiable)
 */
public abstract class SWTNotifiableShell<INFO> extends SWTShell implements SWTNotifiable<INFO> {
    /**
     * The queue of info-data. This queue is correlated to {@link #targetQueue}.
     */
    private final Queue<INFO> infoQueue = new ArrayDeque<>();
    /**
     * The queue of targets ({@link SWTNotifiable}) . This queue is correlated
     * to {@link #infoQueue}.
     */
    private final Queue<SWTNotifiable<INFO>> targetQueue = new ArrayDeque<>();
    /**
     * All known observers. These will be informed by invoking
     * {@link #informAll(Object)}.
     */
    private final Set<SWTNotifiable<INFO>> knownObservers = new HashSet<>();
    /**
     * The lock to synchronize access to queues and observers.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Create a new {@link SWTNotifiableShell}.
     *
     * @param display
     *            the display
     * @param style
     *            the style
     * @see Shell#Shell(Display, int)
     */
    protected SWTNotifiableShell(Display display, int style) {
        super(display, style);
        this.addObserver(this);
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
    protected SWTNotifiableShell(Shell shell, int style) {
        super(shell, style);
        this.addObserver(this);
    }

    @Override
    public final void startEventLoop() {
        this.open();
        this.layout();
        Display display = this.getDisplay();
        while (!this.isDisposed()) {
            if (!display.readAndDispatch()) {
                this.notifyObservers();
                Thread.yield();
            }
        }
    }

    /**
     * Inform all known observers about info (async).
     *
     * @param info
     *            the info
     */
    public final void informAll(INFO info) {
        this.inform(info, SWTNotifiable.getAllID());
    }

    /**
     * Inform a specific observer about info (async).
     *
     * @param info
     *            the info
     * @param target
     *            the observer
     */
    public final void inform(INFO info, SWTNotifiable<INFO> target) {
        if (target == null) {
            return;
        }
        this.lock.lock();
        this.knownObservers.add(target);
        this.infoQueue.add(info);
        this.targetQueue.add(target);
        this.lock.unlock();
    }

    /**
     * Add an observer to the set of known observers.
     *
     * @param o
     *            the observer
     */
    public final void addObserver(SWTNotifiable<INFO> o) {
        this.lock.lock();
        this.knownObservers.add(o);
        this.lock.unlock();
    }

    /**
     * Remove an observer from the set of known observers.
     *
     * @param o
     *            the observer
     */
    public final void removeObserver(SWTNotifiable<INFO> o) {
        this.lock.lock();
        this.knownObservers.remove(o);
        this.lock.unlock();
    }

    /**
     * Notify observers. Work on {@link #infoQueue} and {@link #targetQueue}.
     */
    private final void notifyObservers() {
        this.lock.lock();
        SWTNotifiable<INFO> target = null;
        INFO info = null;
        while (!this.infoQueue.isEmpty() && !this.targetQueue.isEmpty()) {
            target = this.targetQueue.poll();
            info = this.infoQueue.poll();
            if (target == SWTNotifiable.ALL) {
                // if all -> notify all
                final INFO i = info;
                this.knownObservers.forEach(e -> e.inform(i));
            } else {
                target.inform(info);
            }
        }
        this.lock.unlock();
    }

}
