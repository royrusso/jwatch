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
package org.jwatch.listener.settings;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jwatch.domain.instance.QuartzInstance;
import org.jwatch.domain.instance.QuartzInstanceService;
import org.jwatch.listener.notification.EventService;
import org.jwatch.persistence.ConnectionUtil;
import org.jwatch.util.SettingsUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * Config in web.xml. loads settings file on boot.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 7, 2011 1:34:12 PM
 */
public class SettingsLoaderListener implements ServletContextListener
{
    static Logger log = Logger.getLogger(SettingsLoaderListener.class);
    ConnectionUtil connectionUtil;

    public void contextInitialized(ServletContextEvent event)
    {
        try
        {
            log.debug("Starting Settings Load...");
            long start = Calendar.getInstance().getTimeInMillis();

            ServletContext sc = event.getServletContext();
            if (sc != null)
            {
                String sMaxEvents = sc.getInitParameter("maxevents");
                int maxEvents = NumberUtils.toInt(sMaxEvents, EventService.DEFAULT_MAX_EVENT_LIST_SIZE);
                sc.setAttribute("maxevents", maxEvents);      // expose to other servlets
                EventService.setMaxEventListSize(maxEvents);

                String sMaxShowEvents = sc.getInitParameter("maxshowevents");
                int maxShowEvents = NumberUtils.toInt(sMaxShowEvents, EventService.DEFAULT_MAX_SHOW_EVENT_LIST_SIZE);
                sc.setAttribute("maxshowevents", maxShowEvents);      // expose to other servlets
                EventService.setMaxShowEventListSize(maxShowEvents);
            }

            // load config file and instances
            QuartzInstanceService.initQuartzInstanceMap();

            SettingsUtil.loadProperties();

            // connect/start DB
            connectionUtil = new ConnectionUtil(SettingsUtil.getDBFilePath());
            StringBuffer jwatchLog = new StringBuffer();
            jwatchLog.append("CREATE TEXT TABLE IF NOT EXISTS JWATCHLOG (")
                    .append("id INTEGER IDENTITY,")
                    .append("CALENDARNAME VARCHAR(256),")
                    .append("JOBGROUP VARCHAR(256),")
                    .append("JOBNAME VARCHAR(256),")
                    .append("SCHEDULERNAME VARCHAR(256),")
                    .append("TRIGGERGROUP VARCHAR(256),")
                    .append("TRIGGERNAME VARCHAR(256),")
                    .append("FIRETIME DATE,")
                    .append("NEXTFIRETIME  DATE,")
                    .append("PREVIOUSFIRETIME  DATE,")
                    .append("SCHEDULEDFIRETIME  DATE,")
                    .append("RECOVERING BOOLEAN,")
                    .append("JOBRUNTIME BIGINT, ")
                    .append("REFIRECOUNT INTEGER, ")
                    .append("SCHEDULERID VARCHAR(256),")
                    .append("QUARTZINSTANCEID VARCHAR(256)")
                    .append(")");
            connectionUtil.update(jwatchLog.toString());

            long end = Calendar.getInstance().getTimeInMillis();
            log.debug("Settings startup completed in: " + (end - start) + " ms");
        }
        catch (Throwable t)
        {
            log.error("Failed to initialize Settings.", t);
        }
    }

    public void contextDestroyed(ServletContextEvent event)
    {
        log.debug("Shutting down SettingsLoaderListener service...");
        Map qMap = QuartzInstanceService.getQuartzInstanceMap();
        for (Iterator it = qMap.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            QuartzInstance quartzInstance = (QuartzInstance) qMap.get(k);
            try
            {
                quartzInstance.getJmxConnector().close();
            }
            catch (IOException e)
            {
                log.error("Failed to close Connection: " + quartzInstance, e);
            }
        }

        try
        {
            connectionUtil.shutdown();
        }
        catch (SQLException e)
        {
            log.error("Failed closing DB", e);
        }

        // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrt this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements())
        {
            Driver driver = drivers.nextElement();
            try
            {
                DriverManager.deregisterDriver(driver);
                log.debug(String.format("deregistering jdbc driver: %s", driver));
            }
            catch (SQLException e)
            {
                log.error(String.format("Error deregistering driver %s", driver), e);
            }
        }

    }
}
