package org.fuchss.swt.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class WorkerQueueImpl implements WorkerQueue {

	private Semaphore read;

	private Semaphore reduce;
	private Job jobToReduce;

	private List<Job> jobList;
	private Worker worker;
	private Thread myWorker;

	public WorkerQueueImpl() {
		this.read = new Semaphore(0);
		this.reduce = new Semaphore(1);
		this.jobList = new ArrayList<>();
		this.worker = new Worker(this);
		this.myWorker = new Thread(this.worker);
		this.myWorker.setName("WorkerQueueThread");
		this.myWorker.start();
	}

	@Override
	public void put(Job job) {
		synchronized (this.jobList) {
			int idx;
			if (((idx = this.jobList.size()) > 0) && this.jobList.get(idx - 1).isReducedBy(job)) {
				this.jobList.remove(idx - 1);
				this.read.acquireUninterruptibly();
			} else {
				this.reduceJobInProgress(job);
			}
			this.jobList.add(job);
		}
		this.read.release();
	}

	@Override
	public Job nextJob() {
		Job j;
		this.read.acquireUninterruptibly();
		synchronized (this.jobList) {
			j = this.jobList.remove(0);
		}
		return j;
	}

	@Override
	public void quit() {
		this.worker.stop();
	}

	private void reduceJobInProgress(Job job) {
		this.reduce.acquireUninterruptibly();
		if ((this.jobToReduce != null) && this.jobToReduce.isReducedBy(job)) {
			this.jobToReduce = null;
		}
		this.reduce.release();
	}

	@Override
	public void reduceIfPossible(Job job) {
		this.reduce.acquireUninterruptibly();
		this.jobToReduce = job;
		this.reduce.release();
	}

	@Override
	public boolean jobIsReduced() {
		boolean result = false;
		try {
			this.reduce.acquire();
			result = this.jobToReduce == null;
			this.reduce.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
}