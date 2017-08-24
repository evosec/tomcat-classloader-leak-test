package de.evosec.leaktest;

import java.net.URL;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.webresources.StandardRoot;

public class CustomContextConfig extends ContextConfig {

	private final URL contextConfig;
	private final int port;
	private final String contextPath;

	public CustomContextConfig(URL contextConfig, int port,
	        String contextPath) {
		this.contextConfig = contextConfig;
		this.port = port;
		this.contextPath = contextPath;
	}

	@Override
	protected void init() {
		context.addParameter("server.port", "" + port);
		context.addParameter("server.context-path", contextPath);

		if (contextConfig != null) {
			context.setConfigFile(contextConfig);
		}

		StandardRoot resources = new StandardRoot(context);
		resources.setCachingAllowed(false);
		context.setResources(resources);

		if (context instanceof StandardContext) {
			StandardContext standardContext = (StandardContext) context;
			standardContext.setClearReferencesHttpClientKeepAliveThread(true);
			standardContext.setClearReferencesStopThreads(true);
			standardContext.setClearReferencesStopTimerThreads(true);
		}

		super.init();
	}

}
