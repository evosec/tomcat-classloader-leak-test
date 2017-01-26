package de.evosec.leaktest;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

final class DestroyListener implements LifecycleListener {

	private boolean destroyed = false;
	private boolean stopped = false;

	public boolean isStopped() {
		return stopped;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		if (Lifecycle.AFTER_DESTROY_EVENT.equals(event.getType())) {
			destroyed = true;
		} else if (Lifecycle.AFTER_STOP_EVENT.equals(event.getType())) {
			stopped = true;
		}
	}

}
