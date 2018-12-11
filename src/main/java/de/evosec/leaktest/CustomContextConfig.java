package de.evosec.leaktest;

import java.net.URL;
import java.util.Map;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.webresources.StandardRoot;

public class CustomContextConfig extends ContextConfig {

	private final URL contextConfig;
	private final int port;
	private final String contextPath;
	private final Map<String, String> contextParameters;

	public CustomContextConfig(URL contextConfig, int port, String contextPath,
	        Map<String, String> contextParameters) {
		this.contextConfig = contextConfig;
		this.port = port;
		this.contextPath = contextPath;
		this.contextParameters = contextParameters;
	}

	@Override
	protected synchronized void init() {
		context.addParameter("server.port", "" + port);
		context.addParameter("server.context-path", contextPath);
		context.addParameter("server.servlet.context-path", contextPath);
		contextParameters.forEach((k, v) -> context.addParameter(k, v));

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
			standardContext.setAntiResourceLocking(true);
		}

		super.init();
	}

	@Override
	public synchronized void configureStop() {
		super.configureStop();
	}

	@Override
	public synchronized void destroy() {
		super.destroy();
	}

}
