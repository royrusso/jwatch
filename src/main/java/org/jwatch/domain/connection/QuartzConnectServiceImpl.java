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
package org.jwatch.domain.connection;

import org.apache.log4j.Logger;
import org.jwatch.domain.adapter.QuartzJMXAdapter;
import org.jwatch.domain.adapter.QuartzJMXAdapterFactory;
import org.jwatch.domain.instance.QuartzInstance;
import org.jwatch.domain.quartz.Scheduler;
import org.jwatch.listener.notification.Listener;
import org.jwatch.listener.settings.QuartzConfig;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.*;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 9, 2011 9:56:21 AM
 */
public class QuartzConnectServiceImpl implements QuartzConnectService
{
   static Logger log = Logger.getLogger(QuartzConnectServiceImpl.class);

   /**
    * {@inheritDoc}
    */
   @Override
   public QuartzInstance initInstance(QuartzConfig config) throws Exception
   {
      // create url / add credentials map
      Map<String, String[]> env = new HashMap<String, String[]>();
      env.put(JMXConnector.CREDENTIALS, new String[]{config.getUserName(), config.getPassword()});
      JMXServiceURL jmxServiceURL = QuartzConnectUtil.createQuartzInstanceConnection(config);
      JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL, env);
      MBeanServerConnection connection = connector.getMBeanServerConnection();

      // test connection
      ObjectName mBName = new ObjectName("quartz:type=QuartzScheduler,*");
      Set<ObjectName> names = connection.queryNames(mBName, null);
      QuartzInstance quartzInstance = new QuartzInstance(config);
      quartzInstance.setMBeanServerConnection(connection);
      quartzInstance.setJmxConnector(connector);

      // build scheduler list
      List<Scheduler> schList = new ArrayList<Scheduler>();
      for (ObjectName objectName : names)   // for each scheduler.
      {
         QuartzJMXAdapter jmxAdapter = QuartzJMXAdapterFactory.initQuartzJMXAdapter(objectName, connection);
         quartzInstance.setJmxAdapter(jmxAdapter);

         Scheduler scheduler = jmxAdapter.populateScheduler(quartzInstance, objectName);
         schList.add(scheduler);

         // attach listener
         Listener listener = new Listener();
         listener.setUUID(scheduler.getUuidInstance());
         connection.addNotificationListener(objectName, listener, null, null);
         log.info("added listener " + objectName.getCanonicalName());
         QuartzInstance.putListener(listener);
      }
      quartzInstance.setSchedulerList(schList);
      return quartzInstance;
   }
}
