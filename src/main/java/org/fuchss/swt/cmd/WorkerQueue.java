package org.fuchss.swt.cmd;

public interface WorkerQueue {
	WorkerQueue queue = new WorkerQueueImpl();

	void put(Job j);

	void quit();

	Job nextJob();

	void reduceIfPossible(Job job);

	boolean jobIsReduced();
}
