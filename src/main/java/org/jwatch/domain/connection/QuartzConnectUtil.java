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

import org.apache.commons.lang.StringUtils;
import org.jwatch.domain.adapter.QuartzJMXAdapter;
import org.jwatch.domain.instance.QuartzInstance;
import org.jwatch.listener.settings.QuartzConfig;

import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;
import java.net.MalformedURLException;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 12, 2011 3:37:09 PM
 */
public class QuartzConnectUtil
{
   /**
    * JMX connections take the form: service:jmx:rmi:///jndi/rmi://:2911/jmxrmi
    *
    * @param quartzConfig
    * @return
    */
   public static JMXServiceURL createQuartzInstanceConnection(QuartzConfig quartzConfig) throws MalformedURLException
   {
      StringBuffer stringBuffer = new StringBuffer().append("service:jmx:rmi:///jndi/rmi://")
            .append(quartzConfig.getHost()).append(":").append(quartzConfig.getPort())
            .append("/jmxrmi");
      JMXServiceURL jmxServiceURL = new JMXServiceURL(stringBuffer.toString());
      return jmxServiceURL;
   }

   /**
    * Determines whether the Quartz version is supported for monitoring.
    *
    * @param version
    */
   public static boolean isSupported(String version)
   {
      return StringUtils.trimToNull(version) != null && version.startsWith("2");
   }

   /**
    * util method for printing all methods an mbean has exposed.
    *
    * @param quartzInstance
    */
   public static void printMBeanProperties(QuartzInstance quartzInstance, ObjectName objectName)
   {
      try
      {
         QuartzJMXAdapter adapter = quartzInstance.getJmxAdapter();
         adapter.printAttributes(quartzInstance, objectName);
         adapter.printConstructors(quartzInstance, objectName);
         adapter.printOperations(quartzInstance, objectName);
         adapter.printNotifications(quartzInstance, objectName);
         adapter.printClassName(quartzInstance, objectName);
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }
   }

}
