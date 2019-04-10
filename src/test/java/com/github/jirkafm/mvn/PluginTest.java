package com.github.jirkafm.mvn;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.slf4j.LoggerFactory;

public class PluginTest extends AbstractMojoTestCase {

	/**
	 * @return
	 * @throws Exception if any
	 */
	protected Plugin loadPlugin() throws Exception {
		final File pom = getTestFile("src/test/resources/unit/deploy-project/pom.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		final Plugin myPlugin = (Plugin) lookupMojo("deploy", pom);
		return myPlugin;
	}

	/**
	 * @throws Exception if any
	 */
	public void testExecution() throws Exception {
		try {
			final Plugin myPlugin = loadPlugin();
			assertNotNull("Null Plugin", myPlugin);
			setPluginFieldValueReflectively(myPlugin, "fileset", null);

//			Integration test - select different fileset of files to be uploaded			
//			FileSet fileset = new FileSet();
//			fileset.setDirectory("src/test/resources");
//			fileset.addInclude("*.zip");					
//			setPluginFieldValueReflectively(myPlugin, "fileset", fileset);

//			Integration test - select different authentication method for artifactory			
//			setPluginFieldValueReflectively(myPlugin, "password", "differentPassword");	
//			setPluginFieldValueReflectively(myPlugin, "apiKey", "apiKeyvalue");
//			setPluginFieldValueReflectively(myPlugin, "accessToken", "accessTokenValue");

			myPlugin.execute();
		} catch (final InvocationTargetException ex) {
			System.out.println("oops!" + ex.getCause().toString());
		}

	}

	private void setPluginFieldValueReflectively(Object instance, String fieldName, Object value) throws Exception {
		final Field filesetField = instance.getClass().getDeclaredField(fieldName);
		final boolean accessible = filesetField.isAccessible();

		if (!accessible) {
			filesetField.setAccessible(true);
		}
		filesetField.set(instance, value);
		filesetField.setAccessible(accessible);
	}

	private Object getPluginFieldValueReflectively(Object instance, String fieldName) throws Exception {
		final Field filesetField = instance.getClass().getDeclaredField(fieldName);
		final boolean accessible = filesetField.isAccessible();

		if (!accessible) {
			filesetField.setAccessible(true);
		}
		final Object object = filesetField.get(instance);
		filesetField.setAccessible(accessible);
		return object;
	}

	/**
	 * @throws Exception if any
	 */
	public void testUrl() throws Exception {
		try {
			final Plugin myPlugin = loadPlugin();
			final String url = "http://localhost:8081/artifactory/generic-local";
			final String objectUrl = getPluginFieldValueReflectively(myPlugin, "url").toString();
			assertNotNull("Null Plugin", myPlugin);
			assertNotNull("Null", objectUrl);
			assertTrue("Expected [" + url + "] Not equal to:[" + objectUrl + "]", objectUrl.equals(url));
		} catch (final InvocationTargetException ex) {
			LoggerFactory.getLogger(getClass()).error("test", ex);
		}

	}

	/**
	 * @throws Exception if any
	 */
	public void testFileset() throws Exception {
		try {
			final Plugin myPlugin = loadPlugin();
			assertNotNull("Null Plugin", myPlugin);
			assertNotNull("Null Fileset", getPluginFieldValueReflectively(myPlugin, "fileset").toString());
		} catch (final InvocationTargetException ex) {
			LoggerFactory.getLogger(getClass()).error("test", ex);
		}
	}

}
