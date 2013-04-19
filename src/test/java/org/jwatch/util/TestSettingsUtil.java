/**
 * 
 */
package org.jwatch.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.jwatch.listener.settings.QuartzConfig;

/**
 * @author jkienitz
 *
 */
public class TestSettingsUtil {

	String testHost = "localhost";
	
	int testPort = 8080;
	
	/**
	 * Test method for {@link org.jwatch.util.SettingsUtil#getConfigPath()}.
	 */
	@Test
	public void testGetConfigPath() {
		String configPath = SettingsUtil.getConfigPath();
		assertNotNull("Invalid properties file", configPath);
	}

// currently has no effect.	
//	/**
//	 * Test method for {@link org.jwatch.util.SettingsUtil#loadProperties()}.
//	 */
//	@Test
//	public void testLoadProperties() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link org.jwatch.util.SettingsUtil#loadConfig()}.
	 */
	@Test
	public void testLoadConfig() {
        List<QuartzConfig> instances = SettingsUtil.loadConfig();
		assertNotNull("Cannot load configuration file", instances);
	}


	/**
	 * Test method for {@link org.jwatch.util.SettingsUtil#saveConfig(org.jwatch.listener.settings.QuartzConfig)}.
	 * Test method for {@link org.jwatch.util.SettingsUtil#deleteConfig(org.jwatch.listener.settings.QuartzConfig)}.
	 */
	@Test
	public void testSaveDeleteConfig() {
        QuartzConfig quartzConfig = new QuartzConfig(Tools.generateUUID(), testHost, testPort, null, null);
		SettingsUtil.saveConfig(quartzConfig);
		// now delete it
		SettingsUtil.deleteConfig(quartzConfig);
	}

	/**
	 * Test method for {@link org.jwatch.util.SettingsUtil#getDBFilePath()}.
	 */
	@Test
	public void testGetDBFilePath() {
		String dbFile = SettingsUtil.getDBFilePath();
		assertNotNull("Invalid properties file", dbFile);
	}

	/**
	 * Test method for {@link org.jwatch.util.SettingsUtil#getPropertiesPath()}.
	 */
	@Test
	public void testGetPropertiesPath() {
		String propertiesFile = SettingsUtil.getPropertiesPath();
		assertNotNull("Invalid properties file", propertiesFile);
	}

	/**
	 * Test method for {@link org.jwatch.util.SettingsUtil#getBasePath()}.
	 */
	@Test
	public void testGetBasePath() {
		String basePath = SettingsUtil.getBasePath();
		assertNotNull("Invalid base path", basePath);
	}

	/**
	 * Test that we cannot add a duplicate configuration to the file
	 */
	@Test
	public void testCannotAddDuplicates() {
        QuartzConfig quartzConfig = new QuartzConfig(Tools.generateUUID(), testHost, testPort, null, null);
		SettingsUtil.saveConfig(quartzConfig);
        List<QuartzConfig> instances = SettingsUtil.loadConfig();
        // try to add it a second time
		SettingsUtil.saveConfig(quartzConfig);
        List<QuartzConfig> instancesAfter = SettingsUtil.loadConfig();
		// now delete it
		SettingsUtil.deleteConfig(quartzConfig);
		assertEquals("Duplicate configuration was added!", instances.size(), instancesAfter.size());
	}
	
//	@Test
//	public void testSaveConfig() {
//        QuartzConfig quartzConfig = new QuartzConfig(Tools.generateUUID(), testHost, testPort, null, null);
//		SettingsUtil.saveConfig(quartzConfig);
//	}

}
