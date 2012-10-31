/**
 * JWatch - Quartz Monitor: http://code.google.com/p/jwatch/
 * Copyright (C) 2011 Roy Russo and the original author or authors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 **/
package org.jwatch.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jwatch.listener.settings.JWatchConfig;
import org.jwatch.listener.settings.QuartzConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * loads, edits, deleted settings file containing quartz instance connection data.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 6, 2011 4:57:49 PM
 */
public class SettingsUtil
{
    static Logger log = Logger.getLogger(SettingsUtil.class);

    /**
     * Returns FULL drive path to config file that contains a JSON representation of
     * all Quartz instances.
     *
     * @return
     */
    public static String getConfigPath()
    {
        String userHome = (String) System.getProperties().get("user.home");
        String cfgFile = userHome + File.separator + JWatchConfig.QUARTZ_INSTANCE_FILE;

        File file = new File(cfgFile);

        if (!file.canRead())
        {
            log.error("Cannot read JWatch config at: " + cfgFile);
        }

        if (!file.canWrite())
        {
            log.error("Cannot write to JWatch config at: " + cfgFile);
        }

        return cfgFile;
    }

    public static void loadProperties()
    {
        Properties props = new Properties();
        try
        {
            props.load(new FileInputStream(getPropertiesPath()));
        }
        catch (FileNotFoundException fnfe)
        {
            props.setProperty("dbdebug", "true");
            props.setProperty("maxshowevents", "20");
            try
            {
                props.store(new FileOutputStream(getPropertiesPath()), null);
            }
            catch (Throwable throwable)
            {

            }
        }
        catch (Exception e)
        {

        }
    }

    public static List loadConfig()
    {
        List instances = new ArrayList();
        String configPath = SettingsUtil.getConfigPath();
        InputStream inputStream = null;
        try
        {
            File configFile = new File(configPath);
            if (!configFile.exists())
            {
                log.info("Settings file not found! Creating new file at " + configPath);
                FileUtils.touch(configFile);
                log.info("Settings file created at " + configPath);
            }
            else
            {
                inputStream = new FileInputStream(configFile);
            }

            if (configFile.length() > 0)
            {
                XStream xStream = new XStream(new JettisonMappedXmlDriver());
                xStream.setMode(XStream.NO_REFERENCES);
                xStream.alias(GlobalConstants.JSON_DATA_ROOT_KEY, ArrayList.class);
                instances = ((List) xStream.fromXML(inputStream));
            }
        }
        catch (Throwable t)
        {
            log.error(t);
        }
        finally
        {
            try
            {
                if (inputStream != null)
                {
                    inputStream.close();
                }
            }
            catch (IOException ioe)
            {
                log.error("Unable to close config file handle at " + configPath, ioe);
            }
        }
        return instances;
    }

    public static void saveConfig(List<QuartzConfig> configs, String configFilePath)
    {
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(configFilePath);

            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.setMode(XStream.NO_REFERENCES);
            xStream.alias(GlobalConstants.JSON_DATA_ROOT_KEY, ArrayList.class);
            xStream.toXML(configs, fileOutputStream);
        }
        catch (Throwable t)
        {
            log.error(t);
        }
        finally
        {
            try
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
            }
            catch (IOException ioe)
            {
                log.error(GlobalConstants.MESSAGE_ERR_CLOSE_CONFIG + " " + configFilePath, ioe);
            }
        }
    }

    public static void saveConfig(QuartzConfig quartzConfig)
    {
        List instances = loadConfig();
        instances.add(quartzConfig);
        saveConfig(instances, getConfigPath());
    }

    /**
     * Returns FULL drive path to the DB files
     *
     * @return
     */
    public static String getDBFilePath()
    {
        String userHome = (String) System.getProperties().get("user.home");
        String cfgFile = userHome + File.separator + JWatchConfig.DB_FILE_PREFIX;

        File file = new File(cfgFile);

/*        if (!file.canRead())
        {
            log.error("Cannot read JWatch config at: " + cfgFile);
        }

        if (!file.canWrite())
        {
            log.error("Cannot write to JWatch config at: " + cfgFile);
        }*/
        return cfgFile;
    }

    public static String getPropertiesPath()
    {
        String userHome = (String) System.getProperties().get("user.home");
        String cfgFile = userHome + File.separator + JWatchConfig.PROPS_FILE_NAME;

        File file = new File(cfgFile);
        return cfgFile;
    }
}
