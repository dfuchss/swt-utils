package org.fuchss.swt.callable.cmd;

public class Worker implements Runnable {

	private final WorkerQueue queue;
	private boolean running = true;

	public Worker(WorkerQueue queue) {
		this.queue = queue;
	}

	public void stop() {
		this.running = false;
		this.queue.put(null);
	}

	@Override
	public void run() {
		Job job = null;
		while (this.running) {
			job = this.queue.nextJob();
			if (job == null) {
				continue;
			}
			if (job.waitMsec() == 0) {
				job.execute();
			} else {
				this.queue.reduceIfPossible(job);
				try {
					Thread.sleep(job.waitMsec());
					if (!this.queue.jobIsReduced()) {
						job.execute();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
