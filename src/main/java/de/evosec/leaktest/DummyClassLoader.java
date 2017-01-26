package de.evosec.leaktest;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class DummyClassLoader extends ClassLoader {

	public static DummyClassLoader newInstance() {
		return AccessController
		    .doPrivileged(new PrivilegedAction<DummyClassLoader>() {

			    @Override
			    public DummyClassLoader run() {
				    return new DummyClassLoader();
			    }

		    });
	}

}
