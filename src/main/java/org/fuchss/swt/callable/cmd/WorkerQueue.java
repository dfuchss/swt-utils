package org.fuchss.swt.callable.cmd;

public interface WorkerQueue {
	void put(Job j);

	void quit();

	Job nextJob();

	void reduceIfPossible(Job job);

	boolean jobIsReduced();
}
