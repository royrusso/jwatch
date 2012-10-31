/*
 
*/
package org.jwatch;

import org.jwatch.domain.connection.QuartzConnectService;
import org.jwatch.domain.connection.QuartzConnectServiceImpl;
import org.jwatch.domain.connection.QuartzConnectUtil;
import org.jwatch.domain.instance.QuartzInstance;
import org.jwatch.domain.quartz.Scheduler;
import org.jwatch.listener.notification.Listener;
import org.jwatch.listener.settings.QuartzConfig;
import org.jwatch.util.Tools;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.TabularData;
import java.util.List;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 13, 2011 3:29:05 PM
 */
public class TestConnect
{
   public static void main(String[] args)
   {
      try
      {
         QuartzConfig config = new QuartzConfig(Tools.generateUUID(), "localhost", 2911, null, null);
         QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
         QuartzInstance quartzInstance = quartzConnectService.initInstance(config);
         if (quartzInstance != null)
         {
            List shList = quartzInstance.getSchedulerList();
            if (shList != null && shList.size() > 0)
            {
               for (int i = 0; i < shList.size(); i++)
               {
                  Scheduler scheduler = (Scheduler) shList.get(i);

                  QuartzConnectUtil.printMBeanProperties(quartzInstance, scheduler.getObjectName());

                  MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
                  ObjectName objectName = scheduler.getObjectName();

                  Listener listener = new Listener();
                  connection.addNotificationListener(objectName, listener, null, null);
                  System.out.println("added listener " + objectName.getCanonicalName());

                  List groupNames = (List) connection.getAttribute(objectName, "JobGroupNames");
                  TabularData cdata = (TabularData) connection.getAttribute(objectName, "CurrentlyExecutingJobs");
                  TabularData jdata = (TabularData) connection.getAttribute(objectName, "AllJobDetails");
                  List tdata = (List) connection.getAttribute(objectName, "AllTriggers");
                  String sid = (String) connection.getAttribute(objectName, "SchedulerInstanceId");
               }
            }
         }
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }
   }

}
