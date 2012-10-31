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
package org.jwatch.listener.notification;

import org.jwatch.util.JMXUtil;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeDataSupport;
import java.util.Date;

/**
 * One notification listener per scheduler/QuartzInstance combo.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 13, 2011 5:28:59 PM
 */
public class Listener implements NotificationListener
{
   private String UUID;
   private int _count;

   public String getUUID()
   {
      return UUID;
   }

   public void setUUID(String UUID)
   {
      this.UUID = UUID;
   }

   public Listener()
   {
      System.out.println("BLAH");
   }

   @Override
   public void handleNotification(Notification notification, Object handback)
   {
      String type = notification.getType();
      Object object = notification.getUserData();
      try
      {
         if ("jobWasExecuted".equalsIgnoreCase(type))
         {
            if (object != null && object instanceof CompositeDataSupport)
            {
               CompositeDataSupport compositeDataSupport = (CompositeDataSupport) object;
               JobEvent event = new JobEvent();
               event.setJobName((String) JMXUtil.convertToType(compositeDataSupport, "jobName"));
               event.setCalendarName((String) JMXUtil.convertToType(compositeDataSupport, "calendarName"));
               event.setFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "fireTime"));
               event.setJobGroup((String) JMXUtil.convertToType(compositeDataSupport, "jobGroup"));
               event.setJobRunTime(((Long) JMXUtil.convertToType(compositeDataSupport, "jobRunTime")).longValue());
               event.setNextFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "nextFireTime"));
               event.setPreviousFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "previousFireTime"));
               event.setRecovering(((Boolean) JMXUtil.convertToType(compositeDataSupport, "recovering")).booleanValue());
               event.setRefireCount(((Integer) JMXUtil.convertToType(compositeDataSupport, "refireCount")).intValue());
               event.setScheduledFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "scheduledFireTime"));
               event.setSchedulerName((String) JMXUtil.convertToType(compositeDataSupport, "schedulerName"));
               event.setTriggerGroup((String) JMXUtil.convertToType(compositeDataSupport, "triggerGroup"));
               event.setTriggerName((String) JMXUtil.convertToType(compositeDataSupport, "triggerName"));
               String[] arr = this.getUUID().split("@@");
               String uuid = arr[0];
               String scheduleID = arr[1];
               event.setSchedulerId(scheduleID);
               event.setQuartzInstanceId(uuid);
               //System.out.println("Event: " + event);

               // publish the event
               EventService.addEvent(event);
            }
         }
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }

      /**
       * {calendarName=javax.management.openmbean.SimpleType(name=java.lang.String),
       * fireTime=javax.management.openmbean.SimpleType(name=java.util.Date),
       * jobDataMap=javax.management.openmbean.TabularType(name=JobDataMap,
       * rowType=javax.management.openmbean.CompositeType(name=JobDataMap,i
       * tems=((itemName=key,itemType=javax.management.openmbean.SimpleType(name=java.lang.String))
       * ,(itemName=value,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),indexNames=(key)),
       * jobGroup=javax.management.openmbean.SimpleType(name=java.lang.String),
       * jobName=javax.management.openmbean.SimpleType(name=java.lang.String),
       * jobRunTime=javax.management.openmbean.SimpleType(name=java.lang.Long),
       * nextFireTime=javax.management.openmbean.SimpleType(name=java.util.Date),
       * previousFireTime=javax.management.openmbean.SimpleType(name=java.util.Date),
       * recovering=javax.management.openmbean.SimpleType(name=java.lang.Boolean),
       * refireCount=javax.management.openmbean.SimpleType(name=java.lang.Integer),
       * scheduledFireTime=javax.management.openmbean.SimpleType(name=java.util.Date),
       * schedulerName=javax.management.openmbean.SimpleType(name=java.lang.String),
       * triggerGroup=javax.management.openmbean.SimpleType(name=java.lang.String),
       * triggerName=javax.management.openmbean.SimpleType(name=java.lang.String)}
       */
   }

   public int getNotificationCount()
   {
      return _count;
   }
}

