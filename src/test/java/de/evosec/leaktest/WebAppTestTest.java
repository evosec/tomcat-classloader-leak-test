package de.evosec.leaktest;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class WebAppTestTest {

	@Test
	public void testSuccessful() throws Exception {
		Path warPath = getClassPathResource("webapp-test-working.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test
	public void testSuccessfulKeyStore() throws Exception {
		Path warPath = getClassPathResource("webapp-test-keystore.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test
	public void testSuccessfulWithContextInWar() throws Exception {
		Path warPath = getClassPathResource("webapp-test-working-context.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test
	public void testSuccessfulWithContextXml() throws Exception {
		Path warPath = getClassPathResource("webapp-test-working.war");
		URL contextPath = getClassPathUrl("tomcat-context-working.xml");
		new WebAppTest().warPath(warPath).contextConfig(contextPath).run();
	}

	@Test(expected = WebAppTestException.class)
	public void testFailingWithTimeout() throws Exception {
		Path warPath = getClassPathResource("webapp-test-working.war");
		new WebAppTest().warPath(warPath).pingEndPoint("index.html").run();
	}

	@Test(expected = WebAppTestException.class)
	public void testFailingWithContextXml() throws Exception {
		Path warPath = getClassPathResource("webapp-test-working.war");
		URL contextPath = getClassPathUrl("tomcat-context-bad.xml");
		new WebAppTest().warPath(warPath).contextConfig(contextPath).run();
	}

	@Test(expected = WebAppTestException.class)
	public void testFailingBadWebXML() throws Exception {
		Path warPath = getClassPathResource("webapp-test-bad-web-xml.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test(expected = WebAppTestException.class)
	public void testFailingBadContext() throws Exception {
		Path warPath = getClassPathResource("webapp-test-bad-context.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test
	public void testSpringBoot() throws Exception {
		Path warPath = getClassPathResource("spring-boot.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test
	public void testSpringBootClassLoaderLeakPreventor3_2_4_0()
	        throws Exception {
		Path warPath = getClassPathResource(
		    "spring-boot-classloader-leak-preventor3-2.4.0.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test
	public void testSpringBootClassLoaderLeakPreventor_2_4_0()
	        throws Exception {
		Path warPath = getClassPathResource(
		    "spring-boot-classloader-leak-preventor-2.4.0.war");
		new WebAppTest().warPath(warPath).run();
	}

	@Test
	public void testWebappClassLoaderLeakPreventor3_2_4_0() throws Exception {
		Path warPath = getClassPathResource(
		    "webapp-classloader-leak-preventor3-2.4.0.war");
		new WebAppTest().warPath(warPath).run();
	}

	public Path getClassPathResource(String path) throws Exception {
		return Paths.get(getClassPathUrl(path).toURI());
	}

	public URL getClassPathUrl(String path) throws Exception {
		ClassLoader contextClassLoader =
		        Thread.currentThread().getContextClassLoader();
		return contextClassLoader.getResource(path);
	}

}
